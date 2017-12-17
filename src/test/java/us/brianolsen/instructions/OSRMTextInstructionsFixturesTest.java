package us.brianolsen.instructions;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;

import us.brianolsen.instructions.util.V8Util;

public class OSRMTextInstructionsFixturesTest extends BaseTest {
	protected static final String FIXTURES_DIRECTORY = OSRMTextInstructions.OSRM_TEXT_INSTRUCTIONS_MODULE_DIRECTORY
			+ "test/fixtures/" + VERSION + "/";

	@Test
	public void testFixturesMatchGeneratedArriveInstructions() {
		testFixture("arrive");
	}

	@Test // fails
	public void testFixturesMatchGeneratedArriveWaypointInstructions() {
		testFixture("arrive_waypoint");
	}

	@Test
	public void testFixturesMatchGeneratedArriveWaypointLastInstructions() {
		testFixture("arrive_waypoint_last");
	}

	// @Test fails
	public void testFixturesMatchGeneratedContinueInstructions() {
		testFixture("continue");
	}

	@Test
	public void testFixturesMatchGeneratedDepartInstructions() {
		testFixture("depart");
	}

	@Test
	public void testFixturesMatchGeneratedEndOfRoadInstructions() {
		testFixture("end_of_road");
	}

	@Test
	public void testFixturesMatchGeneratedExitRotaryInstructions() {
		testFixture("exit_rotary");
	}

	@Test
	public void testFixturesMatchGeneratedExitRoundaboutInstructions() {
		testFixture("exit_roundabout");
	}

	@Test
	public void testFixturesMatchGeneratedForkInstructions() {
		testFixture("fork");
	}

	@Test
	public void testFixturesMatchGeneratedMergeInstructions() {
		testFixture("merge");
	}

	@Test
	public void testFixturesMatchGeneratedModesInstructions() {
		testFixture("modes");
	}

	@Test
	public void testFixturesMatchGeneratedNewNameInstructions() {
		testFixture("new_name");
	}

	@Test
	public void testFixturesMatchGeneratedNotificationInstructions() {
		testFixture("notification");
	}

	@Test
	public void testFixturesMatchGeneratedOffRampInstructions() {
		testFixture("off_ramp");
	}

	@Test
	public void testFixturesMatchGeneratedOnRampInstructions() {
		testFixture("on_ramp");
	}

	// @Test fail
	public void testFixturesMatchGeneratedOtherInstructions() {
		testFixture("other");
	}

	@Test
	public void testFixturesMatchGeneratedPhraseInstructions() {
		testFixture("phrase");
	}

	@Test
	public void testFixturesMatchGeneratedRotaryInstructions() {
		testFixture("rotary");
	}

	@Test
	public void testFixturesMatchGeneratedRoundaboutInstructions() {
		testFixture("roundabout");
	}

	@Test
	public void testFixturesMatchGeneratedRoundaboutTurnInstructions() {
		testFixture("roundabout_turn");
	}

	@Test
	public void testFixturesMatchGeneratedTurnInstructions() {
		testFixture("turn");
	}

	@Test
	public void testFixturesMatchGeneratedUseLaneInstructions() {
		testFixture("use_lane");
	}

	private void testFixture(String fixture) {
		File folder = new File(FIXTURES_DIRECTORY + fixture);
		for (File fixtureFile : folder.listFiles()) {

			String body = loadJsonFixture(fixtureFile.getPath().replaceFirst(V8Util.RESOURCES_DIRECTORY, ""));
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
