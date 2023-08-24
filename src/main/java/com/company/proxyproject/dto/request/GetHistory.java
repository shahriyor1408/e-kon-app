package com.company.proxyproject.dto.request;

import com.company.proxyproject.constants.enums.SensorDirection;
import com.company.proxyproject.constants.enums.SensorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetHistory {
    private Long objectId;

    private LocalDateTime from;

    private LocalDateTime to;

    private SensorType sensorType;

    private SensorDirection direction;

}
