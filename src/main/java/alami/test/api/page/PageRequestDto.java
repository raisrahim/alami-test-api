package alami.test.api.page;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;

/**
 *
 * @author Muhammad Rais Rahim
 */
@Data
public class PageRequestDto {

    @NotNull
    @PositiveOrZero
    private int pageNumber;

    @NotNull
    @Positive
    @Max(100)
    private int pageSize;

}
