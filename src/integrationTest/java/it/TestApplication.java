package it;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class TestApplication extends EndpointTest {

	@Test
	void testDeployment() {
		testEndpoint("/index.html",
				Pattern.compile(
						".*<h1>Welcome to the MicroProfile Config demonstrator application</h1>.*",
						Pattern.DOTALL));
	}

}
