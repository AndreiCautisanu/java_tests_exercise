package api_mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServer {
    private static final int PORT = 8088;

    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(PORT));
        wireMockServer.start();

        WireMock.configureFor("localhost", 8088);

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/test"))
                .withRequestBody(WireMock.equalToJson("{\"id\": 1234567890, \"a\": 2, \"b\": 3}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1234567890, \"a\": 2, \"b\": 3}")
                ));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/test/1234567890"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"a\": 2, \"b\": 3}")
                ));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/test"))
                .willReturn(WireMock.aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("Internal Server Error")
                ));

        try {
            while (true) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("WireMock server interrupted. Stopping...");
        } finally {
            wireMockServer.stop();
        }
    }
}
