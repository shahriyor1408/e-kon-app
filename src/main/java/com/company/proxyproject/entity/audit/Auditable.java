package com.company.proxyproject.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Clock;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy",
                "createdAt", "updatedAt"},
        allowGetters = true
)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
    @JsonIgnore
    @CreatedBy
    @Column(name = "created_by")
    protected Long createdBy;

    @JsonIgnore
    @LastModifiedBy
    @Column(name = "updated_by")
    protected Long updatedBy;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        createdAt = LocalDateTime.now(Clock.systemDefaultZone());
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = LocalDateTime.now(Clock.systemDefaultZone());
    }
}
