package com.company.proxyproject.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto implements GrantedAuthority {
    private String name;
    private String path;

    @Override
    public String getAuthority() {
        return name;
    }
}
