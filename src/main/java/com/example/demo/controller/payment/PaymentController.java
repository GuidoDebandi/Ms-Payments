package com.example.demo.controller.payment;

import com.example.demo.controller.methods.MethodController;
import com.example.demo.exception.ErrorResponse;
import com.example.demo.model.PaymentMethod;
import com.example.demo.model.PaymentOrder;
import com.example.demo.service.PaymentOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PaymentController", description = "Controller para gestionar el alta y procesamiento de ordenes de pagos")
@RestController
@RequestMapping(PaymentController.URI)
public class PaymentController {
    public static final String URI = "v1/payments";

    public static final String PROCESS_PAYMENT = "/process";
    public static final String NEW_PAYMENT = "/";


    @Autowired
    PaymentOrderService service;


    @Operation(summary = "Procesa una orden de pago", description = "Intenta liquidar una orden de pago mediante el método asociado e informa el resultado a todo el sistema", tags = {"PaymentController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaymentOrder.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))})

    @PostMapping(path = PROCESS_PAYMENT)
    public ResponseEntity<String> processPaymentOrder(){

        service.processOrders();
        return ResponseEntity.ok("Ordenes procesadas correctamente");
    }
}
