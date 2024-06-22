package com.adi685.blogging_web_app.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

	private Integer id;

	@NotBlank(message = "title should not be blank !!")
	@Size(min = 3, message = "title should at least contain 3 characters !!")
	@JsonProperty("title")
	private String categoryTitle;

	@JsonProperty("desc")
	private String categoryDescription;

}
