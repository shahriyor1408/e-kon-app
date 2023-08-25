package com.company.proxyproject.dto.response;

import com.company.proxyproject.constants.enums.SensorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetCurrentsResponse {
    private List<Body> data;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Body implements Serializable {
        private Long id;

        private List<Sensor> sensors;

        private String name;

        private Long objectId;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Sensor implements Serializable {
        private String date;

        private SensorType type;

        private Double value;

        private String direction;

    }

}
