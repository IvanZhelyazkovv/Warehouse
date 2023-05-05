package demo.warehouse.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private Long id;
    @NotEmpty
    private String supplierName;
    @NotEmpty
    private String goods;
    @NotNull
    private Integer size;
    @NotNull
    private Integer price;
    private Date deliveredAt;
    private Date acceptedAt;
    private String acceptedBy;
}
