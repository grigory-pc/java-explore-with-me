package ru.practicum.explorewithme.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explorewithme.dto.EndpointHit;

/**
 * Клиент для отправки данных на микросервис статистики
 */
@Service
public class EventClient extends BaseClient {
    private static final String API_PREFIX = "/hit";

    @Autowired
    public EventClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void postStat(EndpointHit endpointHit) {
        post("", endpointHit);
    }
}