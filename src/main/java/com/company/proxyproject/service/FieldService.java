package com.company.proxyproject.service;

import com.company.proxyproject.common.MessageSingleton;
import com.company.proxyproject.constants.enums.LogType;
import com.company.proxyproject.dto.FieldCreateDto;
import com.company.proxyproject.dto.FieldUpdateDto;
import com.company.proxyproject.entity.Field;
import com.company.proxyproject.logging.LogService;
import com.company.proxyproject.repository.FieldRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 11:33
 * proxy-project
 */
@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository repository;
    private final LogService logService;
    private final MessageSingleton messageSingleton;


    @Transactional
    public ResponseEntity<?> create(@NonNull FieldCreateDto dto) {
        Optional<Field> fieldOpt = repository.findByNameOrApiOrObjectId(dto.getName(), dto.getApiFieldId(), dto.getObjectId());
        if (fieldOpt.isPresent()) {
            logService.logInternal("operation failed due to field found by parameters: %s %s %s"
                    .formatted(dto.getName(), dto.getApiFieldId(), dto.getObjectId()), LogType.FIELD, "/field/create");
            return messageSingleton.dataExists();
        }
        Field field = Field.builder()
                .apiFieldId(dto.getApiFieldId())
                .objectId(dto.getObjectId())
                .name(dto.getName())
                .build();
        return messageSingleton.success(repository.save(field));
    }

    @Transactional
    public ResponseEntity<?> get(@NonNull Long id) {
        Optional<Field> fieldOpt = repository.findById(id);
        if (fieldOpt.isEmpty()) {
            logService.logInternal("operation failed due to no field found by object id: %s".formatted(id), LogType.FIELD, "/field/get");
            return messageSingleton.noDataFound();
        }
        return messageSingleton.success(fieldOpt.get());
    }

    @Transactional
    public ResponseEntity<?> update(@NonNull FieldUpdateDto dto) {
        Optional<Field> fieldOpt = repository.findById(dto.getId());
        if (fieldOpt.isEmpty()) {
            logService.logInternal("operation failed due to no field found by id: %s".formatted(dto.getId()), LogType.FIELD, "/field/update");
            return messageSingleton.noDataFound();
        }
        Field field = fieldOpt.get();
        field.setApiFieldId(dto.getApiFieldId());
        field.setObjectId(dto.getObjectId());
        field.setName(dto.getName());
        return messageSingleton.success(repository.save(field));
    }

    @Transactional
    public ResponseEntity<?> delete(@NonNull Long id) {
        Optional<Field> fieldOpt = repository.findById(id);
        if (fieldOpt.isEmpty()) {
            logService.logInternal("operation failed due to no field found by id: %s".formatted(id), LogType.FIELD, "/field/delete");
            return messageSingleton.noDataFound();
        }
        repository.deleteById(id);
        return messageSingleton.success();
    }

    @Transactional
    public ResponseEntity<?> getAll() {
        return messageSingleton.success(repository.findAll());
    }
}
