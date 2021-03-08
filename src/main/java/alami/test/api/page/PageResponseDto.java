package alami.test.api.page;

import java.util.Collection;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 *
 * @author Muhammad Rais Rahim
 */
@Data
public class PageResponseDto<T> {

    private Collection<T> content;
    private int pagNumber, pageSize, numberOfElements, totalPages;
    private long totalElements;

    public static <T> PageResponseDto<T> of(Page<T> p) {

        PageResponseDto<T> d = new PageResponseDto<>();
        d.content = p.getContent();
        d.pagNumber = p.getNumber();
        d.pageSize = p.getSize();
        d.numberOfElements = p.getNumberOfElements();
        d.totalPages = p.getTotalPages();
        d.totalElements = p.getTotalElements();
        return d;
    }
}
