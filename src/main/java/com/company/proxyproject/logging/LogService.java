package com.company.proxyproject.logging;

import com.company.proxyproject.common.GlobalContextHolder;
import com.company.proxyproject.constants.enums.LogType;
import com.company.proxyproject.entity.Request;
import com.company.proxyproject.entity.Response;
import com.company.proxyproject.event.TraceDataEvent;
import com.company.proxyproject.logging.dto.ExceptionLog;
import com.company.proxyproject.logging.dto.InterceptedLog;
import com.company.proxyproject.logging.dto.OrdinaryLog;
import com.company.proxyproject.security.CustomUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static com.company.proxyproject.common.SecurityProvider.getCurrentUser;
import static com.company.proxyproject.constants.AppConstants.TOKEN;
import static com.company.proxyproject.constants.enums.LogType.REQUEST;
import static com.company.proxyproject.constants.enums.LogType.RESPONSE;

@Component
public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);
    private final ObjectMapper objectMapper;

    private final ApplicationEventPublisher publisher;

    public LogService(ObjectMapper objectMapper, ApplicationEventPublisher publisher) {
        this.objectMapper = objectMapper;
        this.publisher = publisher;
    }

    public void logIntercepted(ContentCachingRequestWrapper request,
                               ContentCachingResponseWrapper response,
                               final Long startTime,
                               final Long endTime,
                               final String traceId) throws JsonProcessingException {
        CustomUserDetails user = getCurrentUser();
        final Long userId = Optional.ofNullable(user).map(CustomUserDetails::getId).orElse(0L);
        final String token = request.getHeader(TOKEN);
        final String ipAddress = request.getHeader("X-Real-IP");
        final String requestContent = getRequestContent(request);
        final String responseContent = getResponseContent(response);
        //request logging
        ObjectNode requestNode = objectMapper.createObjectNode();
        requestNode.put("trace_id", traceId);
        requestNode.put("token", token);
        requestNode.put("ip_address", ipAddress);
        requestNode.set("request_content", objectMapper.readValue(requestContent, ObjectNode.class));
        InterceptedLog requestLog = new InterceptedLog(userId, requestNode.toString(), REQUEST, traceId, startTime, objectMapper);

        //response logging
        ObjectNode responseNode = objectMapper.createObjectNode();
        responseNode.set("response_content", objectMapper.readValue(responseContent, ObjectNode.class));
        InterceptedLog responseLog = new InterceptedLog(userId, responseNode.toString(), RESPONSE, traceId, endTime, objectMapper);

        logger.debug("INTERCEPTED_REQUEST_LOGGING: {}", requestLog);
        logger.debug("INTERCEPTED_RESPONSE_LOGGING: {}", responseLog);

        Request requestData = Request.builder()
                .userId(requestLog.getUserId())
                .token(token)
                .content(requestContent)
                .ipAddress(ipAddress)
                .traceId(requestLog.getTraceId())
                .build();

        Response responseData = Response.builder()
                .userId(responseLog.getUserId())
                .content(requestContent)
                .traceId(responseLog.getTraceId())
                .requestId(-1L)
                .build();
        publisher.publishEvent(new TraceDataEvent(requestData, responseData));
    }


    public void logExternal(final String content, final LogType logType, final String externalTraceId, final String url) {
        final String traceId = GlobalContextHolder.getTraceId();
        final Long userId = GlobalContextHolder.getUser().getId();
        final Long loggedTime = System.currentTimeMillis();
        OrdinaryLog externalLog = new OrdinaryLog(content, logType, traceId, externalTraceId, loggedTime, url, userId, objectMapper);
        logger.debug("EXTERNAL_LOGGING: {}", externalLog);
    }

    public void logInternal(final String content, final LogType logType, final String url) {
        final String traceId = GlobalContextHolder.getTraceId();
        final Long userId = GlobalContextHolder.getUser().getId();
        final Long loggedTime = System.currentTimeMillis();
        OrdinaryLog ordinaryLog = new OrdinaryLog(content, logType, traceId, null, loggedTime, url, userId, objectMapper);
        logger.debug("INTERNAL_LOGGING: {}", ordinaryLog);
    }

    public void logException(final String traceId, final String stacktrace, final String brief) {
        final Long loggedTime = System.currentTimeMillis();
        final Long userId = GlobalContextHolder.getUser().getId();
        ExceptionLog exceptionLog = new ExceptionLog(traceId, stacktrace, loggedTime, userId, objectMapper);
        ExceptionLog briefExceptionLog = new ExceptionLog(traceId, brief, loggedTime, userId, objectMapper);
        logger.error("EXCEPTION_LOGGING: {}", exceptionLog);
        logger.debug("EXCEPTION_LOGGING: {}", briefExceptionLog);
    }

    private String getResponseContent(ContentCachingResponseWrapper response) {
        ObjectNode responseNode = objectMapper.createObjectNode();
        responseNode.put("status", response.getStatus());
        if (response.getContentAsByteArray().length > 0) {
            try {
                ObjectReader reader = objectMapper.reader();
                responseNode.set("body", reader.readTree(new ByteArrayInputStream(response.getContentAsByteArray())));
            } catch (IOException e) {
                String body = new String(response.getContentAsByteArray())
                        .trim()
                        .replaceAll(" +", " ")
                        .replaceAll("\n", "");
                responseNode.put("body", body);
            }
        }
        return responseNode.toString();
    }

    private String getRequestContent(ContentCachingRequestWrapper request) {
        ObjectNode requestNode = objectMapper.createObjectNode();
        requestNode.put("method", request.getMethod());
        requestNode.put("url", request.getRequestURI());
        if (request.getContentAsByteArray().length > 0) {
            ObjectReader reader = objectMapper.reader();
            try {
                requestNode.set("body", reader.readTree(new ByteArrayInputStream(request.getContentAsByteArray())));
            } catch (IOException e) {
                String body = new String(request.getContentAsByteArray());
                requestNode.put("body", body.trim().replaceAll(" +", "").replaceAll("\n", ""));
            }
        }

        if (request.getParameterMap().size() > 0) {
            ObjectNode params = objectMapper.createObjectNode();
            request.getParameterMap().forEach((key, value) -> params.put(key, getParameter(value)));
            requestNode.set("parameters", params);
        }
        return requestNode.toString();
    }

    private String getParameter(String[] value) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : value) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
}
