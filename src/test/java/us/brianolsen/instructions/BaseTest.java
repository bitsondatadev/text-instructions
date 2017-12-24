package us.brianolsen.instructions;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mapbox.services.api.directions.v5.models.LegStep;

import us.brianolsen.instructions.util.ResourceUtil;

public class BaseTest {
	protected static final double DELTA = 1E-10;
	protected static final String VERSION = OSRMTextInstructions.DEFAULT_VERSION;
	protected static final String LANGUAGE = OSRMTextInstructions.DEFAULT_LANGUAGE;
	protected static final String MODULE_NAME = OSRMTextInstructions.MODULE_NAME;
	protected static final String MODULE_VERSION = OSRMTextInstructions.MODULE_VERSION;
	protected static final String FIXTURES_DIRECTORY = Paths.get( //
			ResourceUtil.getNodeModuleDirectory(MODULE_NAME, MODULE_VERSION).getAbsolutePath(), //
			"test", "fixtures", VERSION).toString();

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

	protected void testFixture(String fixture) {
		String fixtureDirectory = Paths.get(FIXTURES_DIRECTORY, fixture).toString();
		File fixtureDirectoryFile = new File(fixtureDirectory);
		for (File fixtureFile : fixtureDirectoryFile.listFiles()) {

			String body = loadJsonFixture(fixtureFile.getAbsolutePath());
			FixtureModel model = new Gson().fromJson(body, FixtureModel.class);
			System.out.println(fixture + ":" + fixtureFile.getName());
			for (Object entry : model.getInstructions().entrySet()) {
				try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
					Map.Entry<String, String> pair = (Map.Entry<String, String>) entry;
					String language = pair.getKey();
					String compiled = pair.getValue();
					assertEquals(compiled, textInstructions.compile(language, model.getStep(), null));
				} catch (RuntimeException e) {
					System.err.println(e);
				}
			}
		}
	}
}
