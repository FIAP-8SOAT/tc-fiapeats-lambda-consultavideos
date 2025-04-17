package br.com.fiap.consultavideos;

import br.com.fiap.consultavideos.models.VideoDTO;
import br.com.fiap.consultavideos.repository.VideoRepository;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppTest {

    /*private App app;
    private VideoRepository mockRepo;
    private Context mockContext;

    @BeforeEach
    void setUp() {
        mockRepo = mock(VideoRepository.class);
        mockContext = mock(Context.class);
        app = new App(mockRepo);
    }

    @Test
    void testHandleRequestWithValidToken() throws Exception {
        // Gerar um JWT fake com sub
        String sub = "user123";
        String token = JWT.create()
                .withSubject(sub)
                .sign(Algorithm.HMAC256("secret")); // assinatura irrelevante nesse caso

        // Montar evento com Authorization header
        APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        event.setHeaders(headers);

        // Simular retorno do repositório
        List<VideoDTO> videos = List.of(new VideoDTO("video1", "ok", "http://url.com"));
        when(mockRepo.buscarVideosPorSub(sub)).thenReturn(videos);

        // Executar
        APIGatewayProxyResponseEvent response = app.handleRequest(event, mockContext);

        // Verificações
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().contains("video1"));
        assertTrue(response.getBody().contains("url.com"));
    }

    @Test
    void testHandleRequestWithoutToken() {
        APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
        event.setHeaders(new HashMap<>());

        APIGatewayProxyResponseEvent response = app.handleRequest(event, mockContext);

        assertEquals(500, response.getStatusCode());
        assertEquals("{}", response.getBody());
    }

    @Test
    void testHandleRequestWithInvalidJWT() {
        String token = "Bearer invalid.token.here";

        APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        event.setHeaders(headers);

        APIGatewayProxyResponseEvent response = app.handleRequest(event, mockContext);

        assertEquals(500, response.getStatusCode());
    }

    @Test
    void testHandleRequestWithRepoError() throws Exception {
        String sub = "user123";
        String token = JWT.create().withSubject(sub).sign(Algorithm.HMAC256("secret"));

        APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
        event.setHeaders(Map.of("Authorization", "Bearer " + token));

        when(mockRepo.buscarVideosPorSub(sub)).thenThrow(new RuntimeException("DB fail"));

        APIGatewayProxyResponseEvent response = app.handleRequest(event, mockContext);

        assertEquals(500, response.getStatusCode());
        assertTrue(response.getBody().contains("DB fail"));
    }*/
}