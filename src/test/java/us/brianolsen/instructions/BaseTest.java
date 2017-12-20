package us.brianolsen.instructions;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.google.gson.JsonParser;
import com.mapbox.services.api.directions.v5.models.LegStep;

import us.brianolsen.instructions.util.ResourceUtil;

public class BaseTest {
	protected static final double DELTA = 1E-10;
	protected static final String VERSION = OSRMTextInstructions.DEFAULT_VERSION;
	protected static final String LANGUAGE = OSRMTextInstructions.DEFAULT_LANGUAGE;
	protected static final String RESOURCES_DIRECTORY = ResourceUtil.RESOURCES_DIRECTORY;

	protected String loadJsonFixture(String filename) {
		String body = "";

		try (InputStream stream = getClass().getClassLoader().getResourceAsStream(filename)) {
			body = new JsonParser().parse(new InputStreamReader(stream)).getAsJsonObject().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return body;
	}

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
}
