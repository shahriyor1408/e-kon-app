package com.company.proxyproject.service;

import com.company.proxyproject.common.MessageSingleton;
import com.company.proxyproject.constants.enums.LogType;
import com.company.proxyproject.dto.StationCreateDto;
import com.company.proxyproject.dto.StationUpdateDto;
import com.company.proxyproject.entity.Field;
import com.company.proxyproject.entity.Station;
import com.company.proxyproject.logging.LogService;
import com.company.proxyproject.repository.StationRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 14:49
 * proxy-project
 */
@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository repository;

    private final LogService logService;

    private final MessageSingleton messageSingleton;

    private final FieldService fieldService;

    @Transactional
    public ResponseEntity<?> create(@NonNull StationCreateDto dto) {
        Optional<Station> stationOpt = repository.findByStationIdOrObjectId(dto.getApiStationId(), dto.getObjectId());
        if (stationOpt.isPresent()) {
            logService.logInternal("operation failed due to station found by id: %s".formatted(dto.getApiStationId()), LogType.STATION, "/station/create");
            return messageSingleton.dataExists(dto.getApiStationId()+" "+dto.getObjectId());
        }

        Field field = fieldService.getByObjectId(dto.getFieldId());
        if (Objects.isNull(field)) {
            logService.logInternal("operation failed due to no field found by id: %s".formatted(dto.getFieldId()), LogType.FIELD, "/field/delete");
            return messageSingleton.noDataFound("field not found by id: "+dto.getFieldId());
        }

        Station station = Station.builder()
                .name(dto.getName())
                .apiStationId(dto.getApiStationId())
                .objectId(dto.getObjectId())
                .field(field)
                .build();
        return messageSingleton.success(repository.save(station));
    }

    @Transactional
    public ResponseEntity<?> get(@NonNull Long id) {
        Optional<Station> stationOpt = repository.findByObjectId(id);
        if (stationOpt.isEmpty()) {
            logService.logInternal("operation failed no due to station found by id: %s".formatted(id), LogType.STATION, "/station/get");
            return messageSingleton.noDataFound();
        }
        return messageSingleton.success(stationOpt.get());
    }

    @Transactional
    public ResponseEntity<?> getAll() {
        return messageSingleton.success(repository.findAll());
    }

    @Transactional
    public ResponseEntity<?> update(@NonNull StationUpdateDto dto) {
        Optional<Station> stationOpt = repository.findById(dto.getId());
        if (stationOpt.isEmpty()) {
            logService.logInternal("operation failed no due to station found by id: %s".formatted(dto.getId()), LogType.STATION, "/station/update");
            return messageSingleton.noDataFound();
        }
        Station station = stationOpt.get();
        station.setApiStationId(dto.getApiStationId());
        station.setObjectId(dto.getObjectId());
        return messageSingleton.success(repository.save(station));
    }

    @Transactional
    public ResponseEntity<?> delete(@NonNull Long id) {
        Optional<Station> stationOpt = repository.findById(id);
        if (stationOpt.isEmpty()) {
            logService.logInternal("operation failed no due to station found by id: %s".formatted(id), LogType.STATION, "/station/delete");
            return messageSingleton.noDataFound();
        }
        repository.deleteById(id);
        return messageSingleton.success();
    }

    @Transactional
    public ResponseEntity<?> createByArray(List<StationCreateDto> dtos) {
        List<Object> response = new ArrayList<>();
        for (StationCreateDto dto : dtos) {
            ResponseEntity<?> responseEntity = create(dto);
            response.add(responseEntity.getBody());
        }
        return messageSingleton.success(response);
    }

}
