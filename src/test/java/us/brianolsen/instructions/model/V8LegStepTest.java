package us.brianolsen.instructions.model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.eclipsesource.v8.V8Object;
import com.mapbox.services.api.directions.v5.models.IntersectionLanes;
import com.mapbox.services.api.directions.v5.models.LegStep;
import com.mapbox.services.api.directions.v5.models.StepIntersection;
import com.mapbox.services.api.directions.v5.models.StepManeuver;

import us.brianolsen.instructions.BaseTest;
import us.brianolsen.instructions.OSRMTextInstructions;

public class V8LegStepTest extends BaseTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testV8LegStepRelease() {
		LegStep step = new LegStep();
		StepManeuver man = new StepManeuver();
		man.setModifier("left");
		step.setManeuver(man);
		step.setIntersections(Collections.singletonList(new StepIntersection(new IntersectionLanes[] {
				new IntersectionLanes(true), new IntersectionLanes(true), new IntersectionLanes(true) })));
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("v5")) {
			try (V8LegStep legStep = new V8LegStep(step, textInstructions.getRuntime())) {
				V8Object v8Maneuver = ((V8Object) legStep.getV8Step().get("maneuver"));

				assertEquals("left", v8Maneuver.get("modifier"));

				v8Maneuver.release();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.fail();
			}

		}
	}

}
