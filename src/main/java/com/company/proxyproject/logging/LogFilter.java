package com.company.proxyproject.logging;

import com.company.proxyproject.common.GlobalContextHolder;
import com.company.proxyproject.entity.auth.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class LogFilter extends OncePerRequestFilter {
    private static final Set<String> notLoggableUrls = new HashSet<>() {
        {
            add("swagger");
            add("favicon");
            add("ws");
        }
    };

    private final LogService logService;

    public LogFilter(LogService logService) {
        this.logService = logService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {
            doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
        }
    }

    private void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) throws IOException, ServletException {
        Long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            Long endTime = System.currentTimeMillis();
            afterRequest(request, response, startTime, endTime);
            response.copyBodyToResponse();
        }
    }

    static private ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    static private ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }

    private void afterRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, Long startTime, Long endTime) throws IOException {
        if (allowToSave(request.getRequestURI())) {
            Optional<User> user = Optional.ofNullable(GlobalContextHolder.getUser());
            final String username = user.map(User::getUsername).orElse("unknown");
            final Long userId = user.map(User::getId).orElse(0L);
            final String uid = UUID.randomUUID().toString();
            final String traceId = username + "-" + userId + "-" + uid;
            logService.logIntercepted(request, response, startTime, endTime, traceId);
        }
    }
    private static boolean allowToSave(String uri) {
        for (String url : notLoggableUrls) {
            if (uri.contains(url))
                return false;
        }
        return true;
    }
}
