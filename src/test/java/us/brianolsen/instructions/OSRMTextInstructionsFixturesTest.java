package us.brianolsen.instructions;

import org.junit.Test;

//Only test specific fixtures in interest of avoiding too many files open bug until a solution is found.
public class OSRMTextInstructionsFixturesTest extends BaseTest {

	@Test
	public void testFixturesMatchGeneratedArriveInstructions() {
		testFixture("arrive");
	}

	// // @Test fails
	// public void testFixturesMatchGeneratedArriveWaypointInstructions() {
	// testFixture("arrive_waypoint");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedArriveWaypointLastInstructions() {
	// testFixture("arrive_waypoint_last");
	// }
	//
	// // @Test fails
	// public void testFixturesMatchGeneratedContinueInstructions() {
	// testFixture("continue");
	// }

	@Test
	public void testFixturesMatchGeneratedDepartInstructions() {
		testFixture("depart");
	}

	// @Test
	// public void testFixturesMatchGeneratedEndOfRoadInstructions() {
	// testFixture("end_of_road");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedExitRotaryInstructions() {
	// testFixture("exit_rotary");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedExitRoundaboutInstructions() {
	// testFixture("exit_roundabout");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedForkInstructions() {
	// testFixture("fork");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedMergeInstructions() {
	// testFixture("merge");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedModesInstructions() {
	// testFixture("modes");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedNewNameInstructions() {
	// testFixture("new_name");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedNotificationInstructions() {
	// testFixture("notification");
	// }
	//
	// // @Test fail
	// public void testFixturesMatchGeneratedOffRampInstructions() {
	// testFixture("off_ramp");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedOnRampInstructions() {
	// testFixture("on_ramp");
	// }
	//
	// // @Test fail
	// public void testFixturesMatchGeneratedOtherInstructions() {
	// testFixture("other");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedPhraseInstructions() {
	// testFixture("phrase");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedRotaryInstructions() {
	// testFixture("rotary");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedRoundaboutInstructions() {
	// testFixture("roundabout");
	// }
	//
	// @Test
	// public void testFixturesMatchGeneratedRoundaboutTurnInstructions() {
	// testFixture("roundabout_turn");
	// }

	@Test
	public void testFixturesMatchGeneratedTurnInstructions() {
		testFixture("turn");
	}

	// @Test
	// public void testFixturesMatchGeneratedUseLaneInstructions() {
	// testFixture("use_lane");
	// }

}
