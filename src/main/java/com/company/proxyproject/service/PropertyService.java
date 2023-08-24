package com.company.proxyproject.service;

import com.company.proxyproject.constants.AppConstants;
import com.company.proxyproject.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;

    @Transactional(readOnly = true)
    public String getValue(String name) {
        return propertyRepository.findByName(name).orElse(null);
    }

    public String getToken() {
        return getValue(AppConstants.TOKEN);
    }

}
