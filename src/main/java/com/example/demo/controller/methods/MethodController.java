package com.example.demo.controller.methods;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.model.PaymentMethod;
import com.example.demo.service.PaymentMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Tag(name = "MethodController", description = "Controller para gestionar el alta y obtencion de medios de pagos")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(MethodController.URI)
public class MethodController {
    public static final String URI = "v1/";

    public static final String GET_PAYMENT_METHODS = "/payment/methods/";
    public static final String POST_PAYMENT_METHOD = "/payment/methods";

    public static final String DEACTIVATE_PAYMENT_METHOD = "/deactivate/{paymentMethodId}";

    @Autowired PaymentMethodService service;

    @Operation(summary = "Obtiene los métodos de pago disponibles", description = "Se recuperan los medios de pagos activos mediante la bandera enabled", tags = {"MethodController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaymentMethod.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(path = POST_PAYMENT_METHOD)
    public ResponseEntity<Iterable<PaymentMethod>> getPaymentMethods(){

        return new ResponseEntity<>(service.getMethods(), HttpStatus.OK);
    }

    @Operation(summary = "Crea un nuevo método de pago", description = "Se crea un nuevo medio de pago para que  luego pueda ser consultado", tags = {"MethodController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaymentMethod.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping(path = POST_PAYMENT_METHOD)
    public ResponseEntity<PaymentMethod> postPaymentMethod(@RequestBody PaymentMethod newMethod){
        return new ResponseEntity<>(service.postMethod(newMethod), HttpStatus.OK);
    }


    @Operation(summary = "Inhabilita un medio de pago activo", description = "Se modifica la bandera de enabled del metodo de pago especificado para que no pueda ser recuperado entre los activos", tags = {"MethodController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaymentMethod.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))})

    @PatchMapping(path = DEACTIVATE_PAYMENT_METHOD)
    public ResponseEntity<PaymentMethod> disablePaymentMethod( @PathVariable String paymentMethodId ){
        return new ResponseEntity<>(service.deactivatePaymentMethod(paymentMethodId), HttpStatus.OK);

    }
}
