package us.brianolsen.instructions;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonParser;

public class BaseTest {
	public static final double DELTA = 1E-10;

	protected String loadJsonFixture(String filename) throws IOException {
		InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
		return new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject().toString();
	}
}
