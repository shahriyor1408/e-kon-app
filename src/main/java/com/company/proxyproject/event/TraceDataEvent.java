package com.company.proxyproject.event;

import com.company.proxyproject.entity.Request;
import com.company.proxyproject.entity.Response;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 15:56
 * proxy-project
 */

@Getter
public class TraceDataEvent extends ApplicationEvent {

    private final Response response;

    public TraceDataEvent(Request source, Response response) {
        super(source);
        this.response = response;
    }
}
