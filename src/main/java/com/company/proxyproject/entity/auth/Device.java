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
@Table(name = "devices")
public class Device extends TimedAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_devices_id")
    @SequenceGenerator(allocationSize = 1, name = "seq_devices_id", sequenceName = "seq_devices_id")
    private Long id;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "device", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Session session;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setSession(Session session) {
        if (session == null) {
            if (this.session != null) {
                this.session.setDevice(null);
            }
        } else {
            session.setDevice(this);
        }
        this.session = session;
    }
}
