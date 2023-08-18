package com.company.proxyproject.entity.auth;

import com.company.proxyproject.constants.enums.Status;
import com.company.proxyproject.entity.audit.TimedAuditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessions")
public class Session extends TimedAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_sessions_id")
    @SequenceGenerator(allocationSize = 1, name = "seq_sessions_id", sequenceName = "seq_sessions_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
