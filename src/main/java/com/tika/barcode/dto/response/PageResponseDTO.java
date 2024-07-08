package com.tika.barcode.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Processes an {@link PageResponseDTO } response.
 * @author Raghu
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO {
	private List<?> data;
	private boolean isLast;
	private boolean isFirst;
	private Integer totalPages;
	private Long totalRecords;
	private Integer recordCount;
	private Long recordOffset;
	private Integer pageNumber;
	private Integer requestedCount;
}
