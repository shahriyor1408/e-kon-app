package com.company.proxyproject.service;

import com.company.proxyproject.common.MessageSingleton;
import com.company.proxyproject.constants.AppConstants;
import com.company.proxyproject.dto.request.GetHistory;
import com.company.proxyproject.dto.response.GetHistoryResponse;
import com.company.proxyproject.entity.Field;
import com.company.proxyproject.entity.Station;
import com.company.proxyproject.repository.FieldRepository;
import com.company.proxyproject.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final MessageSingleton messageSingleton;

    private final StationRepository stationRepository;

    private final FieldRepository fieldRepository;

    private RestTemplate restTemplate = new RestTemplate();

    private final PropertyService propertyService;

    @Transactional
    public ResponseEntity<?> getHistory(GetHistory request) {
        Optional<Station> stationOpt = stationRepository.findByObjectId(request.getObjectId());
        if (stationOpt.isEmpty()) {
            return messageSingleton.noDataFound();
        }
        Station station = stationOpt.get();
        Map<String, Object> apiRequest = Map.of(
                "direction", request.getDirection(),
                "from", request.getFrom(),
                "to", request.getTo(),
                "type", request.getSensorType(),
                "stationId", station.getApiStationId()
        );
        HttpHeaders httpHeaders = new HttpHeaders();
        HashMap<String, String> map = new HashMap<>();
        httpHeaders.set(AppConstants.TOKEN, propertyService.getToken());
        HttpEntity<Map<String, Object>> http = new HttpEntity<>(apiRequest, httpHeaders);
        String url = AppConstants.URL + AppConstants.GET_HISTORY;
        ResponseEntity<? extends HashMap> response = restTemplate.exchange(url, HttpMethod.PUT, http, map.getClass());
        Object data = response.getBody().get("data");

        return messageSingleton.success(GetHistoryResponse.builder()
                .objectId(station.getObjectId())
                .sensorValues(data)
                .build());
    }

    @Transactional
    public ResponseEntity<?> getCurrentsByFieldId(Long id) {
        Optional<Field> fieldOpt = fieldRepository.findByObjectId(id);
        if (fieldOpt.isEmpty()) {
            return messageSingleton.noDataFound();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        HashMap<String, String> map = new HashMap<>();
        httpHeaders.set(AppConstants.TOKEN, propertyService.getToken());
        HttpEntity<Map<String, Object>> http = new HttpEntity<>(httpHeaders);
        String url = AppConstants.URL + AppConstants.GET_CURRENT_BY_FIELD_ID + "/" + fieldOpt.get().getApiFieldId();
        ResponseEntity<? extends HashMap> response = restTemplate.exchange(url, HttpMethod.GET, http, map.getClass());
        Object data = response.getBody().get("data");
        return messageSingleton.success(data);
    }

}
