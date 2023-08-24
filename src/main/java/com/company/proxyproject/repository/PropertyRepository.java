package com.company.proxyproject.repository;

import com.company.proxyproject.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface
PropertyRepository extends JpaRepository<Property, Long> {
    @Query("select p.value from Property p where p.name=:name")
    Optional<String> findByName(String name);

}
