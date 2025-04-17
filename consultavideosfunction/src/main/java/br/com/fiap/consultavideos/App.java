package br.com.fiap.consultavideos;

import br.com.fiap.consultavideos.models.VideoDTO;
import br.com.fiap.consultavideos.models.VideoResponse;
import br.com.fiap.consultavideos.repository.VideoRepository;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final VideoRepository repo = new VideoRepository();

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        try {
            String authHeader = input.getHeaders().get("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                ObjectMapper mapper = new ObjectMapper();

                String token = authHeader.substring("Bearer ".length());

                // 2. Decodifica o JWT
                DecodedJWT jwt = JWT.decode(token);

                // 3. Extrai a claim 'sub'
                String sub = jwt.getSubject();

                List<VideoDTO> listaVideos = repo.buscarVideosPorSub(sub);

                VideoResponse videoResponse = new VideoResponse(listaVideos);

                String jsonOutput = mapper.writeValueAsString(videoResponse);

                return response
                        .withStatusCode(200)
                        .withBody(jsonOutput);
            }

            return response
                    .withBody("{}")
                    .withStatusCode(500);

        } catch (Exception e) {
            return response
                    .withBody(e.getMessage())
                    .withStatusCode(500);
        }
    }
}
