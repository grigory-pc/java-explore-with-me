package ru.practicum.explorewithme.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explorewithme.dto.EndpointHit;

import java.util.Map;

/**
 * Клиент для отправки данных на микросервис статистики
 */
@Service
public class StatsClient extends BaseClient {
    @Autowired
    public StatsClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void postStat(EndpointHit endpointHit) {
        post("/hit", endpointHit);
    }

    public int getStatsByUri(String uri) {
        Map<String, Object> parameters = Map.of(
                "uri", uri
        );
        ResponseEntity<Object> responseEntity = get("/stats/hits?uri={uri}", parameters);

        return (int) responseEntity.getBody();
    }
}





