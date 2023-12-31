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
 * @since 18/08/23 14:50
 * proxy-project
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationCreateDto {

    @NotNull(message = "station.name.not.notNull")
    @NotBlank(message = "station.name.not.blank")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "station.apiStationId.not.blank")
    @JsonProperty("apiStationId")
    private Long apiStationId;

    @NotNull(message = "station.objectId.not.blank")
    @JsonProperty("objectId")
    private Long objectId;

    @JsonProperty("fieldId")
    @NotNull(message = "field.fieldId.not.null")
    private Long fieldId;
}
