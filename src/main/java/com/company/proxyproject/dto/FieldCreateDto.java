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
 * @since 18/08/23 11:38
 * proxy-project
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldCreateDto {

    @JsonProperty("apiFieldId")
    @NotNull(message = "field.apiFieldId.not.null")
    private Long apiFieldId;

    @JsonProperty("objectId")
    @NotNull(message = "field.objectId.not.null")
    private Long objectId;

    @JsonProperty("name")
    @NotBlank(message = "field.name.not.blank")
    @NotNull(message = "field.name.not.null")
    private String name;
}
