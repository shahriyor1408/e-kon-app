package com.company.proxyproject.entity;

import com.company.proxyproject.entity.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 10:25
 * proxy-project
 */
@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stations")
public class Station extends Auditable {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_stations_id")
    @SequenceGenerator(allocationSize = 1, name = "seq_station_id", sequenceName = "seq_stations_id")
    private Long id;

    @JsonIgnore
    @Column(name = "api_station_id", nullable = false)
    private Long apiStationId;

    @Column(name = "object_id", nullable = false)
    private Long objectId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;
}
