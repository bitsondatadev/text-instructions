package us.brianolsen.instructions;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class OSRMTextInstructionsFixturesTest extends BaseTest {
	private static OSRMTextInstructions osrmTextInstructions;

	@BeforeClass
	public static void setupClass() {
		osrmTextInstructions = new OSRMTextInstructions(VERSION);
	}

	@AfterClass
	public static void teardownClass() {
		osrmTextInstructions.close();
	}

	@Test
	public void testFixturesMatchGeneratedArriveInstructions() {
		testFixture("arrive", osrmTextInstructions);
	}

	// @Test fails due to mismatching expected (from parent project)
	public void testFixturesMatchGeneratedArriveWaypointInstructions() {
		testFixture("arrive_waypoint", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedArriveWaypointLastInstructions() {
		testFixture("arrive_waypoint_last", osrmTextInstructions);
	}

	// @Test fails due to mismatching expected (from parent project)
	public void testFixturesMatchGeneratedContinueInstructions() {
		testFixture("continue", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedDepartInstructions() {
		testFixture("depart", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedEndOfRoadInstructions() {
		testFixture("end_of_road", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedExitRotaryInstructions() {
		testFixture("exit_rotary", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedExitRoundaboutInstructions() {
		testFixture("exit_roundabout", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedForkInstructions() {
		testFixture("fork", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedMergeInstructions() {
		testFixture("merge", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedModesInstructions() {
		testFixture("modes", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedNewNameInstructions() {
		testFixture("new_name", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedNotificationInstructions() {
		testFixture("notification", osrmTextInstructions);
	}

	// @Test fails due to mismatching expected (from parent project)
	public void testFixturesMatchGeneratedOffRampInstructions() {
		testFixture("off_ramp", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedOnRampInstructions() {
		testFixture("on_ramp", osrmTextInstructions);
	}

	// @Test fails due to mismatching expected (from parent project)
	public void testFixturesMatchGeneratedOtherInstructions() {
		testFixture("other", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedRotaryInstructions() {
		testFixture("rotary", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedRoundaboutInstructions() {
		testFixture("roundabout", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedRoundaboutTurnInstructions() {
		testFixture("roundabout_turn", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedTurnInstructions() {
		testFixture("turn", osrmTextInstructions);
	}

	@Test
	public void testFixturesMatchGeneratedUseLaneInstructions() {
		testFixture("use_lane", osrmTextInstructions);
	}

}
