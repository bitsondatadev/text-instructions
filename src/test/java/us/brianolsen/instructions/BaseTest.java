package us.brianolsen.instructions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.JsonParser;
import com.mapbox.services.api.directions.v5.models.LegStep;

public class BaseTest {
	protected static final double DELTA = 1E-10;
	protected static final String VERSION = OSRMTextInstructions.DEFAULT_VERSION;
	protected static final String LANGUAGE = OSRMTextInstructions.DEFAULT_LANGUAGE;
	protected static final String MODULE_NAME = OSRMTextInstructions.MODULE_NAME;
	protected static final String MODULE_VERSION = OSRMTextInstructions.MODULE_VERSION;

	public class FixtureModel {
		private LegStep step;
		private Map<String, String> instructions;

		public LegStep getStep() {
			return step;
		}

		public Map<String, String> getInstructions() {
			return instructions;
		}
	}

	public static String loadJsonFixture(String filename) {
		String body = "";
		String fileAsString;

		try {
			fileAsString = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
			body = new JsonParser().parse(fileAsString).getAsJsonObject().toString();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return body;
	}
}
