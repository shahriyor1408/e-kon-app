package com.company.proxyproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentDataResponseBody implements Serializable {
    private Long id;

    private List<SensorData> sensors;

    private String name;

    private Long objectId;

}