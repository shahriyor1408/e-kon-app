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
 * @since 18/08/23 10:41
 * proxy-project
 */

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "responses")
public class Response extends TimedAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_responses_id")
    @SequenceGenerator(allocationSize = 1, name = "seq_responses_id", sequenceName = "seq_responses_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content")
    private String content;

    @Column(name = "traceId", unique = true)
    private String traceId;

    @Column(name = "request_id")
    private Long requestId;
}
