package com.company.proxyproject.entity;

import com.company.proxyproject.entity.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 10:21
 * proxy-project
 */
@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fields")
public class Field extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_fields_id")
    @SequenceGenerator(allocationSize = 1, name = "seq_fields_id", sequenceName = "seq_fields_id")
    private Long id;

    @JsonIgnore
    @Column(name = "api_field_id", nullable = false, unique = true)
    private Long apiFieldId;

    @JsonProperty("field_id")
    @Column(name = "field_id", nullable = false, unique = true)
    private Long objectId;

    @Column(name = "name", nullable = false)
    private String name;
}
