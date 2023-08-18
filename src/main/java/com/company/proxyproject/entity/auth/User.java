package com.company.proxyproject.entity.auth;

import com.company.proxyproject.constants.enums.Gender;
import com.company.proxyproject.constants.enums.Status;
import com.company.proxyproject.entity.audit.Auditable;
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
@Table(name = "users")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_users_id")
    @SequenceGenerator(allocationSize = 1, name = "seq_users_id", sequenceName = "seq_users_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private Session session;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Device device;

    public void setSession(Session session) {
        if (session == null) {
            if (this.session != null) {
                this.session.setUser(null);
            }
        } else {
            session.setUser(this);
        }
        this.session = session;
    }

    public void setDevice(Device device) {
        if (device == null) {
            if (this.device != null) {
                this.device.setUser(null);
            }
        } else {
            device.setUser(this);
        }
        this.device = device;
    }
}
