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
public class InterceptedLog {
    private final Long userId;
    private final String content;
    private final LogType logType;
    private final String traceId;
    private final Long time;
    private final ObjectMapper mapper;

    public String toString() {
        try {
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("trace_id", traceId);
            objectNode.put("log_type", logType.toString());
            objectNode.put("user_id", userId);
            objectNode.set("content", mapper.readValue(content, ObjectNode.class));
            objectNode.put("time", time);
            return objectNode.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
