package com.company.proxyproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 14:55
 * proxy-project
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationUpdateDto {

    @JsonProperty("id")
    @NotNull(message = "station.apiStationId.not.blank")
    private Long id;

    @NotNull(message = "station.apiStationId.not.blank")
    @JsonProperty("apiStationId")
    private Long apiStationId;

    @NotNull(message = "station.objectId.not.blank")
    @JsonProperty("objectId")
    private Long objectId;
}
