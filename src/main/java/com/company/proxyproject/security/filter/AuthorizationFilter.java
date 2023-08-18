package com.company.proxyproject.security.filter;

import com.company.proxyproject.common.GlobalContextHolder;
import com.company.proxyproject.constants.AppConstants;
import com.company.proxyproject.security.CustomUserDetails;
import com.company.proxyproject.service.auth.CustomUserDetailService;
import com.company.proxyproject.service.auth.JwtTokenService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;
    private final JwtTokenService jwtTokenService;
    private static final String[] WHITELIST = {

    };

    private boolean isOpenPath(String currentPath) {
        if (currentPath.startsWith("/api/v")) {
            currentPath = currentPath.substring(7);
        }
        if (WHITELIST != null) {
            for (String s : WHITELIST) {
                if (currentPath.contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        if (!isOpenPath(requestUri)) {
            try {
                String token = getJwtFromRequest(request);
                if (jwtTokenService.isValid(token)) {
                    String username = jwtTokenService.subject(token);
                    CustomUserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                    authenticate(request, userDetails);

                    final String traceId = username + "-" + userDetails.getId() + "-" + UUID.randomUUID();
                    GlobalContextHolder.setTraceId(traceId);
                    GlobalContextHolder.setUser(userDetails.getUser());
                    logger.info("User authenticated by username - %s".formatted(username));
                }
            } catch (Exception e) {
                logger.error("internal error", e);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void authenticate(HttpServletRequest request, CustomUserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AppConstants.TOKEN);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) return bearerToken.substring(7);
        return null;
    }
}
