package com.meraphorce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class to encapuslate the results for a Bulk process
 * @author Juan Chavez
 * @since May/18/2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BulkResultDTO {
	private List<?> success;
	private List<?> failed;
}
