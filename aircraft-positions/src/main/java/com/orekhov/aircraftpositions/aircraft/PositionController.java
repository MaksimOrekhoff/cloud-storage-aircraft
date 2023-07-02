package com.orekhov.aircraftpositions.aircraft;

import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
public class PositionController {
    private final PositionRetriever retriever;
    private final RSocketRequester requester;

    public PositionController(PositionRetriever retriever, RSocketRequester.Builder builder) {
        this.retriever = retriever;
        this.requester = builder.tcp("localhost", 7000);
    }

    // Конечная точка HTTP, созданный ранее инициатор HTTP-запроса
    @GetMapping("/aircraft")
    public Flux<Aircraft> getCurrentAircraftPositions() {
        return retriever.retrieveAircraftPositions();
    }

    @GetMapping("/aircraftadmin")
    public Flux<Aircraft> getCurrentAircraftPositionsAdminPrivs() {
        return retriever.retrieveAircraftPositions();
    }

    // Конечная точка HTTP, клиентская конечная точка RSocket
    @ResponseBody
    @GetMapping(value = "/acstream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Aircraft> getCurrentACPositionsStream() {
        return requester.route("acstream")
                .data("Requesting aircraft positions")
                .retrieveFlux(Aircraft.class);
    }
}


