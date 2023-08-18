package com.company.proxyproject.security;

import com.company.proxyproject.common.MessageSingleton;
import com.company.proxyproject.constants.enums.Status;
import com.company.proxyproject.exception.CustomAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import static com.company.proxyproject.constants.enums.Status.isPending;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired
    private MessageSingleton messageSingleton;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        CustomUserDetails customUserDetail = (CustomUserDetails) userDetails;
        Status status = customUserDetail.getStatus();
        if (isPending(status)) {
            throw new CustomAuthenticationException(messageSingleton.accountPending());
        }
    }
}
