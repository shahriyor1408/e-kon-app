package com.company.proxyproject.event;

import com.company.proxyproject.entity.Request;
import com.company.proxyproject.entity.Response;
import com.company.proxyproject.repository.RequestRepository;
import com.company.proxyproject.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 18/08/23 16:05
 * proxy-project
 */

@Component
@RequiredArgsConstructor
public class TraceDataEventListener {

    private final RequestRepository requestRepository;
    private final ResponseRepository responseRepository;

    @EventListener
    @Transactional
    public void traceDataEventListener(TraceDataEvent traceDataEvent) {
        Request request = (Request) traceDataEvent.getSource();
        Response response = traceDataEvent.getResponse();
        Request saveRequest = requestRepository.save(request);
        response.setRequestId(saveRequest.getId());
        responseRepository.save(response);
    }

}
