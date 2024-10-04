package com.example.demo.client.dto.in;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String id;
    private String status;
    private String cartId;
    private Double totalPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy, hh:mm:ss a")
    private Date updated;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy, hh:mm:ss a")
    private Date created;
    private List<ArticleDto> articles;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticleDto {
        private String id;
        private int quantity;
        private boolean validated;
        private boolean valid;
    }
}
