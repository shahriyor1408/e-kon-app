package com.company.proxyproject.controller;

import com.company.proxyproject.dto.StationCreateDto;
import com.company.proxyproject.dto.StationUpdateDto;
import com.company.proxyproject.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.company.proxyproject.constants.AppConstants.API_VERSION;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 10:30
 * proxy-project
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + "/station")
public class StationController {

    private final StationService service;

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@Valid @RequestBody StationCreateDto dto) {
        return service.create(dto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return service.getAll();
    }

    @PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@Valid @RequestBody StationUpdateDto dto) {
        return service.update(dto);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
