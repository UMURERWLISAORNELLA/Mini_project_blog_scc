package com.example.blogmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Generic response wrapper for paginated endpoints. This class holds the
 * paginated list of content as well as metadata about the total number of
 * elements and pages. Using a wrapper simplifies exposing pagination
 * information consistently across endpoints.
 *
 * @param <T> type of content being paginated
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {

    @Schema(description = "Content for the current page")
    private List<T> content;

    @Schema(description = "Current zero-based page index")
    private int page;

    @Schema(description = "Size of the page requested")
    private int size;

    @Schema(description = "Total number of elements across all pages")
    private long totalElements;

    @Schema(description = "Total number of pages available")
    private int totalPages;

    @Schema(description = "Indicator if this page is the last one")
    private boolean last;
}