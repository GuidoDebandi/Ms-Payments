package com.example.demo.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @Schema(required = true, description = "Status de error del servicio")
    private String status;

    @Schema(required = true, description = "Código de error del servicio")
    private String code;

    @Schema(required = true, description = "Descripción de error del servicio")
    private String description;
}
