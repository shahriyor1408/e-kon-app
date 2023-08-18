package com.company.proxyproject.entity;

import com.company.proxyproject.entity.audit.TimedAuditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 10:40
 * proxy-project
 */

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")
public class Request extends TimedAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_requests_id")
    @SequenceGenerator(allocationSize = 1, name = "seq_requests_id", sequenceName = "seq_requests_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "token")
    private String token;

    @Column(name = "content")
    private String content;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "trace_id", unique = true)
    private String traceId;
}
