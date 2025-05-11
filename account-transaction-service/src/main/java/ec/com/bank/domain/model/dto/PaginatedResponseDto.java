package ec.com.bank.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PaginatedResponseDto<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PaginatedResponseDto(List<T> content, int page, int size, long totalElements) {
        this.content       = content;
        this.page          = page;
        this.size          = size;
        this.totalElements = totalElements;
        this.totalPages    = (int) Math.ceil((double) totalElements / size);
    }

}