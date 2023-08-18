package com.company.proxyproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 11:51
 * proxy-project
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldUpdateDto {

    @JsonProperty("id")
    @NotNull(message = "field.id.not.null")
    private Long id;

    @JsonProperty("apiFieldId")
    @NotNull(message = "field.apiFieldId.not.null")
    private Long apiFieldId;

    @JsonProperty("objectId")
    @NotNull(message = "field.objectId.not.null")
    private Long objectId;

    @JsonProperty("name")
    @NotNull(message = "field.name.not.null")
    @NotBlank(message = "field.name.not.blank")
    private String name;
}
