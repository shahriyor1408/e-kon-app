package com.company.proxyproject.logging.dto;

import com.company.proxyproject.constants.enums.LogType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrdinaryLog {
    private final String content;
    private final LogType logType;
    private final String traceId;
    private final String externalTraceId;
    private final Long time;
    private final String url;
    private final Long userId;
    private final ObjectMapper objectMapper;

    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("log_type", logType.toString());
        objectNode.put("trace_id", traceId);
        objectNode.put("external_trace_id", externalTraceId);
        objectNode.put("url", url);
        objectNode.put("content", content);
        objectNode.put("time", time);
        objectNode.put("userId", userId);
        return objectNode.toString();
    }
}
