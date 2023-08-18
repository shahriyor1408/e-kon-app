package com.company.proxyproject.repository;

import com.company.proxyproject.entity.Field;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 11:33
 * proxy-project
 */

public interface FieldRepository extends JpaRepository<Field, Long> {

    @Query(value = "from Field where name = :name or apiFieldId = :apiFieldId or objectId = :objectId")
    Optional<Field> findByNameOrApiOrObjectId(@NonNull String name, Long apiFieldId, Long objectId);

    @Query(value = "from Field where objectId = :id")
    Optional<Field> findByObjectId(Long id);
}
