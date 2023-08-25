package com.company.proxyproject.dto.response;

import com.company.proxyproject.constants.enums.SensorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class SensorData implements Serializable {
    private String date;

    private SensorType type;

    private Double value;

    private String direction;

}