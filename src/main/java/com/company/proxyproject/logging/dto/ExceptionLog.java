package com.company.proxyproject.logging.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionLog {
    private final String traceId;
    private final String stackTrace;
    private final Long time;
    private final Long userId;
    private final ObjectMapper objectMapper;

    public String toString() {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("trace_id", traceId);
        objectNode.put("stack_trace", stackTrace);
        objectNode.put("time", time);
        objectNode.put("user_id", userId);
        return objectNode.toString();
    }
}
