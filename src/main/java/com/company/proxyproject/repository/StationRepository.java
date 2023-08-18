package com.company.proxyproject.repository;

import com.company.proxyproject.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 14:48
 * proxy-project
 */

public interface StationRepository extends JpaRepository<Station, Long> {

    @Query("from Station where apiStationId = :apiStationId or objectId = :objectId")
    Optional<Station> findByStationIdOrObjectId(Long apiStationId, Long objectId);
}
