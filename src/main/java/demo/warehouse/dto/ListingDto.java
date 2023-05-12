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
public class ListingDto {
    private Long id;
    @NotEmpty
    private String operatorName;
    @NotEmpty
    private String goods;
    @NotNull
    private Integer size;
    @NotNull
    private Integer price;
    private Date listedAt;
}
