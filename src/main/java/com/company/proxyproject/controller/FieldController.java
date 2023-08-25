package com.company.proxyproject.controller;

import com.company.proxyproject.dto.FieldCreateDto;
import com.company.proxyproject.dto.FieldUpdateDto;
import com.company.proxyproject.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.company.proxyproject.constants.AppConstants.API_VERSION;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 10:29
 * proxy-project
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + "/field")
public class FieldController {
    private final FieldService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody FieldCreateDto fieldCreateDto) {
        return service.create(fieldCreateDto);
    }

    @GetMapping("/get/{fieldId}")
    public ResponseEntity<?> get(@PathVariable Long fieldId) {
        return service.get(fieldId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return service.getAll();
    }

    @PutMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@Valid @RequestBody FieldUpdateDto dto) {
        return service.update(dto);
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<?> delete(@RequestParam(name = "id") Long id) {
//        return service.delete(id);
//    }
}
