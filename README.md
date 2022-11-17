# Ms-Payments
# WIP

Microservicio de pagos
El ms se desarrollará en GO Lang, aunque por el momento solo se ha desarrollado un entorno de swagger
  para visualizar el contrato del mismo. 
  
  ## Casos de Uso

### CU: Registro de pago pendiente
- precondicion:
    - Se crea una orden asociada a productos validos en el ecomerce.
- camino normal:
    - Se registra una orden de pago para que sea procesada. Esta orden es la entidad manejada internamente en el microservicio relacionada 1 a 1 con una orden de compra. Para mayor informacion ver entidad PaymentOrder en el modelo de datos.

### CU: Procesar Pago (async)
- precondicion:
    - Existe un orden pendiente de pago
    - Existe al menos un medio de pago registrado y operativo
- camino normal:
    - Se recupera la entidad de pago relacionada a la orden y se procesa el medio asociado a la misma para liquidar el pago en cuestion. Luego de la orden haya sido pagada, se cambia el estado del pago a exitoso y se informa al servicio de ordenes la modificacion del estado de la misma. Luego, se le informa a los demas servicios del estado de la orden para disparar cualquier actividad que deba realizarse.
- caminos alternativos:
    - Si no ha podido realizarse un pago satisfactoriamente se modifica el estado del pago como fallido y se informa al servicio de ordenes el estado. Luego, se le informa a los demas servicios del estado de la orden para disparar cualquier actividad que deba realizarse.

### CU: Obtener formas de Pago disponibles
- camino normal:
    - Se recuperan las formas de pago disponibles para que el usuario pueda seleccionar la que necesite y avanzar con el pago.

- caminos alternativos:
    - De no existir ningun medio de pago disponible el front-end deberá mostrar un mensaje de error para el pago dejando la orden en pendiente.

### CU: Registrar nueva forma de Pago
- camino normal:
    - Se registra una nueva forma de pago para que pueda ser elegida por el usuario al momento de pagar la orden.

### CU: Inhabilitar forma de Pago
- precondicion:
    - Existe al menos un medio de pago disponible.
- camino normal:
    - Se modifica un medio de pago para que no pueda ser recuperado en la lista de medios disponibles, esto puede producirse por algun error temporal o indefinido en los medios de pagos.
- caminos alternativos:
    - No se encuentra el medio de pago que se quiere inhabilitar o ya se encuantra inhabilitado, en este caso debe devolverse un codigo de error.

## Modelo de Datos
![[Modelo de datos - ms-payments.png]]
## Interfaz REST
- github con proyecto: 


![[Api-rest.png]]
![[processPayment.png]]
![[post new-payment.png]]
![[obtener methodos de pagos.png]]
![[deactivate Method.png]]
![[Post new method.png]]
## Interfaz Asincronica (RabbitMQ)
  

**Procesamiento de Orden de Pago**

Recibe para procesar un pago

body

```json
{
    "orderId": String,
    "totalAmount": Float,
    "paymentMethodId": String,
    "referenceId": String
}

```


Responde con el resultado del pago por fan-out

body

```json

{
    "orderId": String,
    "statusName": String,
    "statusCode": Integer,
    "referenceId": String
}

```

## Cambios Arquitectura Actual
- Llamar al alta de orden de Pago en el checkout de una orden.
