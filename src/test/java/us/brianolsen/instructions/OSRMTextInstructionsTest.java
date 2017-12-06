package us.brianolsen.instructions;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.google.gson.Gson;
import com.mapbox.services.api.directions.v5.models.IntersectionLanes;
import com.mapbox.services.api.directions.v5.models.LegStep;
import com.mapbox.services.api.directions.v5.models.StepIntersection;

public class OSRMTextInstructionsTest extends BaseTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testSanity() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("v5")) {

		}
	}

	@Test
	public void testBadLanguage() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("v5")) {
			assertEquals("en", textInstructions.getBestMatchingLanguage("yyyasdfasd"));
		}
	}

	@Test
	public void testBadVersion() {
		thrown.expect(RuntimeException.class);
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("yyy")) {

		}
	}

	@Test
	public void testCapitalizeFirstLetter() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("v5")) {
			assertEquals("Mapbox", textInstructions.capitalizeFirstLetter("en", "mapbox"));
		}
	}

	@Test
	public void testOrdinalize() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("v5")) {
			assertEquals("1st", textInstructions.ordinalize("en", 1));
			assertEquals("", textInstructions.ordinalize("en", 999));
		}
	}

	@Test
	public void testValidDirectionFromDegree() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("v5")) {
			assertEquals("", textInstructions.directionFromDegree("en", null));
			assertEquals("north", textInstructions.directionFromDegree("en", 0.));
			assertEquals("north", textInstructions.directionFromDegree("en", 1.));
			assertEquals("north", textInstructions.directionFromDegree("en", 20.));
			assertEquals("northeast", textInstructions.directionFromDegree("en", 21.));
			assertEquals("northeast", textInstructions.directionFromDegree("en", 69.));
			assertEquals("east", textInstructions.directionFromDegree("en", 70.));
			assertEquals("east", textInstructions.directionFromDegree("en", 110.));
			assertEquals("southeast", textInstructions.directionFromDegree("en", 111.));
			assertEquals("southeast", textInstructions.directionFromDegree("en", 159.));
			assertEquals("south", textInstructions.directionFromDegree("en", 160.));
			assertEquals("south", textInstructions.directionFromDegree("en", 200.));
			assertEquals("southwest", textInstructions.directionFromDegree("en", 201.));
			assertEquals("southwest", textInstructions.directionFromDegree("en", 249.));
			assertEquals("west", textInstructions.directionFromDegree("en", 250.));
			assertEquals("west", textInstructions.directionFromDegree("en", 290.));
			assertEquals("northwest", textInstructions.directionFromDegree("en", 291.));
			assertEquals("northwest", textInstructions.directionFromDegree("en", 339.));
			assertEquals("north", textInstructions.directionFromDegree("en", 340.));
			assertEquals("north", textInstructions.directionFromDegree("en", 360.));
		}
	}

	@Test
	public void testInvalidDirectionFromDegree() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("v5")) {
			thrown.expect(RuntimeException.class);
			assertEquals("", textInstructions.directionFromDegree("en", 361.));
		}
	}

	// @Test FIXME serialize LegStep -> V8Object
	public void testLaneDiagram() {
		thrown.expect(RuntimeException.class);
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("v5")) {
			Map<String, LegStep> map = new HashMap<>();

			map.put("o", new LegStep(Collections.singletonList(new StepIntersection(new IntersectionLanes[] {
					new IntersectionLanes(true), new IntersectionLanes(true), new IntersectionLanes(true) }))));
			map.put("ox", new LegStep(Collections.singletonList(new StepIntersection(new IntersectionLanes[] {
					new IntersectionLanes(true), new IntersectionLanes(true), new IntersectionLanes(false) }))));
			map.put("ox",
					new LegStep(Collections.singletonList(new StepIntersection(
							new IntersectionLanes[] { new IntersectionLanes(true), new IntersectionLanes(true),
									new IntersectionLanes(false), new IntersectionLanes(false) }))));
			map.put("oxo", new LegStep(Collections.singletonList(new StepIntersection(new IntersectionLanes[] {
					new IntersectionLanes(true), new IntersectionLanes(false), new IntersectionLanes(true) }))));
			map.put("xox",
					new LegStep(Collections.singletonList(new StepIntersection(
							new IntersectionLanes[] { new IntersectionLanes(false), new IntersectionLanes(true),
									new IntersectionLanes(true), new IntersectionLanes(false) }))));
			map.put("xoxox",
					new LegStep(Collections.singletonList(new StepIntersection(new IntersectionLanes[] {
							new IntersectionLanes(false), new IntersectionLanes(true), new IntersectionLanes(false),
							new IntersectionLanes(true), new IntersectionLanes(false) }))));
			map.put("x", new LegStep(Collections.singletonList(new StepIntersection(new IntersectionLanes[] {
					new IntersectionLanes(false), new IntersectionLanes(false), new IntersectionLanes(false) }))));

			for (Object entry : map.entrySet()) {
				Map.Entry pair = (Map.Entry) entry;
				assertEquals(pair.getKey(), textInstructions.laneConfig((LegStep) pair.getValue()));
			}
		}
	}

	// @Test FIXME see above
	public void testInvalidLaneDiagram() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("v5")) {
			thrown.expect(RuntimeException.class);
			assertEquals("", textInstructions.laneConfig(
					new LegStep(Collections.singletonList(new StepIntersection(new IntersectionLanes[] {})))));
		}
	}

	// @Test FIXME
	public void testFixturesMatchGeneratedInstructions() throws IOException {
		for (String fixture : TextInstructionsFixtures.FIXTURES) {
			String body = loadJsonFixture(fixture);
			FixtureModel model = new Gson().fromJson(body, FixtureModel.class);
			for (Object entry : model.getInstructions().entrySet()) {
				try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("v5")) {
					Map.Entry pair = (Map.Entry) entry;
					String language = (String) pair.getKey();
					String compiled = (String) pair.getValue();
					assertEquals(compiled, textInstructions.compile(language, model.getStep(), null));
				}
			}
		}
	}

	@Test
	public void testGeneratedInstructionUsingJ2V8() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("v5");) {
			String language = textInstructions.getBestMatchingLanguage("en");
			V8 runtime = textInstructions.getRuntime();

			// LegStep object
			V8Object step = new V8Object(runtime);
			V8Object maneuver = new V8Object(runtime);
			V8Object options = new V8Object(runtime);
			maneuver.add("type", "arrive");
			maneuver.add("modifier", "right");
			step.add("maneuver", maneuver);
			step.add("name", "Way Name");
			step.add("destinations", "Destination 1,Destination 2");

			String instruction = textInstructions.compile(language, step, options);

			assertEquals("You have arrived at your destination, on the right", instruction);

			options.release();
			maneuver.release();
			step.release();
		}
	}

	private class FixtureModel {
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
