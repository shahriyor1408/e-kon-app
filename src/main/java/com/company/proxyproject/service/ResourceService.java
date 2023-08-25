package com.company.proxyproject.service;

import com.company.proxyproject.common.MessageSingleton;
import com.company.proxyproject.constants.AppConstants;
import com.company.proxyproject.dto.request.GetHistory;
import com.company.proxyproject.dto.response.GetCurrentsResponse;
import com.company.proxyproject.dto.response.GetHistoryResponse;
import com.company.proxyproject.entity.Field;
import com.company.proxyproject.entity.Station;
import com.company.proxyproject.exception.CustomException;
import com.company.proxyproject.repository.FieldRepository;
import com.company.proxyproject.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final MessageSingleton messageSingleton;

    private final StationRepository stationRepository;

    private final FieldRepository fieldRepository;

    private final RestTemplate restTemplate;
    private final PropertyService propertyService;

    @Transactional
    public ResponseEntity<?> getHistory(GetHistory request) {
        Optional<Station> stationOpt = stationRepository.findByObjectId(request.getObjectId());
        if (stationOpt.isEmpty()) {
            return messageSingleton.noDataFound();
        }
        Station station = stationOpt.get();
        HttpEntity<Map<String, Object>> http = getMapHttpEntity(request, station);
        try {
            String url = AppConstants.URL + AppConstants.GET_HISTORY;
            ResponseEntity<GetCurrentsResponse> response = restTemplate.exchange(url, HttpMethod.PUT, http, GetCurrentsResponse.class);
            Object data = Objects.requireNonNull(response.getBody()).getData();
            return messageSingleton.success(GetHistoryResponse.builder()
                    .objectId(station.getObjectId())
                    .sensorValues(data)
                    .build());
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @NotNull
    private HttpEntity<Map<String, Object>> getMapHttpEntity(GetHistory request, Station station) {
        Map<String, Object> apiRequest = Map.of(
                "direction", request.getDirection().getValue(),
                "from", request.getFrom(),
                "to", request.getTo(),
                "type", request.getSensorType(),
                "stationId", station.getApiStationId()
        );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AppConstants.TOKEN, propertyService.getToken());
        return new HttpEntity<>(apiRequest, httpHeaders);
    }

    @Transactional
    public ResponseEntity<?> getCurrentsByFieldId(Long id) {
        Optional<Field> fieldOpt = fieldRepository.findByObjectId(id);
        if (fieldOpt.isEmpty()) {
            return messageSingleton.noDataFound();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AppConstants.TOKEN, propertyService.getToken());
        HttpEntity<Map<String, Object>> http = new HttpEntity<>(httpHeaders);
        try {
            String url = AppConstants.URL + AppConstants.GET_CURRENT_BY_FIELD_ID + fieldOpt.get().getApiFieldId();
            ResponseEntity<GetCurrentsResponse> response = restTemplate.exchange(url, HttpMethod.GET, http, GetCurrentsResponse.class);
            List<GetCurrentsResponse.Body> data = Objects.requireNonNull(response.getBody()).getData();
            data.removeIf(body -> {
                Optional<Station> stationOpt = stationRepository.findByApiId(body.getId());
                stationOpt.ifPresent(station -> {
                    body.setId(station.getObjectId());
                    body.setName(station.getName());
                });
                return stationOpt.isEmpty();
            });
            return messageSingleton.success(data);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
