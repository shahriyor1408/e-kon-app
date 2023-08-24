package com.company.proxyproject.controller;

import com.company.proxyproject.dto.request.GetHistory;
import com.company.proxyproject.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.company.proxyproject.constants.AppConstants.API_VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + "/resource")
public class ResourceController {
    private final ResourceService resourceService;


    @PostMapping("/get-history")
    public ResponseEntity<?> getHistory(@Valid @RequestBody GetHistory request) {
        return resourceService.getHistory(request);
    }

    @GetMapping("/get-currents-by-field-id/{id}")
    public ResponseEntity<?> getCurrentsByFieldId(@PathVariable Long id) {
        return resourceService.getCurrentsByFieldId(id);
    }

}
