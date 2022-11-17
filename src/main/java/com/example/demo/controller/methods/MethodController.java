package com.example.demo.controller.methods;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.model.PaymentMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.UUID;

@Tag(name = "MethodController", description = "Controller para gestionar el alta y obtencion de medios de pagos")
@RestController
@RequestMapping(MethodController.URI)
public class MethodController {
    public static final String URI = "/ms-payments/v1/paymentMethods";

    public static final String GET_PAYMENT_METHODS = "/";
    public static final String POST_PAYMENT_METHOD = "/";
    public static final String DEACTIVATE_PAYMENT_METHOD = "/deactivate/{uuidPaymentMethod}";



    @Operation(summary = "Obtiene los métodos de pago disponibles", description = "Se recuperan los medios de pagos activos mediante la bandera enabled", tags = {"MethodController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaymentMethod.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))})

    @GetMapping(path = GET_PAYMENT_METHODS)
    public ResponseEntity<List<PaymentMethod>> getPaymentMethods(){
        return null;
    }


    @Operation(summary = "Registra un nuevo método de pago", description = "Se registra un nuevo método de pago desde el bodyRequest", tags = {"MethodController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaymentMethod.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))})

    @PostMapping(path = POST_PAYMENT_METHOD)
    public ResponseEntity<PaymentMethod> postPaymentMethod(@RequestBody PaymentMethod paymentMethod){
        return null;
    }


    @Operation(summary = "Inhabilita un medio de pago activo", description = "Se modifica la bandera de enabled del metodo de pago especificado para que no pueda ser recuperado entre los activos", tags = {"MethodController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaymentMethod.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))})

    @PatchMapping(path = DEACTIVATE_PAYMENT_METHOD)
    public ResponseEntity<PaymentMethod> disablePaymentMethod( @PathVariable UUID paymentMethodId ){
        return null;
    }
}
