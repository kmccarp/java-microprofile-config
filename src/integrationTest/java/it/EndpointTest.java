package it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class EndpointTest {

	public void testEndpoint(String endpoint, Pattern... expectedOutputs) {
        String port = System.getProperty("liberty.test.port");
        String war = System.getProperty("war.context");
        String url = "http://localhost:" + port + "/" + war + endpoint;
        System.out.println("Testing " + url);
        Response response = sendRequest(url, "GET");
		try {
			int responseCode = response.getStatus();
			assertEquals(200,
					responseCode,
					"Incorrect response code: " + responseCode);

			String responseString = response.readEntity(String.class);
			for (Pattern expectedOutput : expectedOutputs) {
				assertTrue(
						expectedOutput.matcher(responseString).matches(),
						"Incorrect response: pattern=" + expectedOutput
								+ " response=" + responseString);
			}
		} finally {
			response.close();
		}
    }

    public Response sendRequest(String url, String requestType) {
        Client client = ClientBuilder.newClient();
        System.out.println("Testing " + url);
        WebTarget target = client.target(url);
        Invocation.Builder invoBuild = target.request();
        Response response = invoBuild.build(requestType).invoke();
        return response;
    }
}
