package demo.warehouse.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private Integer size;
}
