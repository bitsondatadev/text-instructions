package us.brianolsen.instructions;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.mapbox.services.api.directions.v5.models.IntersectionLanes;
import com.mapbox.services.api.directions.v5.models.LegStep;
import com.mapbox.services.api.directions.v5.models.StepIntersection;

public class OSRMTextInstructionsTest extends BaseTest {

	@Test
	public void testSanity() {
		OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION);
		textInstructions.close();
	}

	@Test
	public void testBadLanguage() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
			assertEquals(LANGUAGE, textInstructions.getBestMatchingLanguage("yyyasdfasd"));
		}
	}

	@Test
	public void testBadVersion() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions("yyy")) {
			Assert.fail();
		} catch (RuntimeException e) {
			// exception should be thrown
		}
	}

	@Test
	public void testCapitalizeFirstLetter() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
			assertEquals("Mapbox", textInstructions.capitalizeFirstLetter(LANGUAGE, "mapbox"));
		}
	}

	@Test
	public void testOrdinalize() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
			assertEquals("1st", textInstructions.ordinalize(LANGUAGE, 1));
			assertEquals("", textInstructions.ordinalize(LANGUAGE, 999));
		}
	}

	@Test
	public void testGetWayName() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
			LegStep step = new LegStep();
			String wayName = "North Michigan Ave.";
			step.setName(wayName);
			assertEquals(wayName, textInstructions.getWayName(LANGUAGE, step, "{}"));
		}
	}

	@Test
	public void testGrammarize() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
			assertEquals("Бармалееву улицу", textInstructions.grammarize("ru", "Бармалеева улица", "accusative"));
			assertEquals("Бармалеевой улице", textInstructions.grammarize("ru", "Бармалеева улица", "dative"));
			assertEquals("Бармалеевой улицы", textInstructions.grammarize("ru", "Бармалеева улица", "genitive"));
			assertEquals("Бармалеевой улице", textInstructions.grammarize("ru", "Бармалеева улица", "prepositional"));
			assertEquals("Большую Монетную улицу",
					textInstructions.grammarize("ru", "Большая Монетная улица", "accusative"));
			assertEquals("Большой Монетной улице",
					textInstructions.grammarize("ru", "Большая Монетная улица", "dative"));
			assertEquals("Большой Монетной улицы",
					textInstructions.grammarize("ru", "Большая Монетная улица", "genitive"));
			assertEquals("Большой Монетной улице",
					textInstructions.grammarize("ru", "Большая Монетная улица", "prepositional"));
			assertEquals("Малую Зеленину улицу",
					textInstructions.grammarize("ru", "Малая Зеленина улица", "accusative"));
			assertEquals("Малой Зелениной улице", textInstructions.grammarize("ru", "Малая Зеленина улица", "dative"));
			assertEquals("Малой Зелениной улицы",
					textInstructions.grammarize("ru", "Малая Зеленина улица", "genitive"));
			assertEquals("Малой Зелениной улице",
					textInstructions.grammarize("ru", "Малая Зеленина улица", "prepositional"));
			assertEquals("22-23-ю линии В.О.", textInstructions.grammarize("ru", "22-23-я линии В.О.", "accusative"));
			assertEquals("22-23-й линиям В.О.", textInstructions.grammarize("ru", "22-23-я линии В.О.", "dative"));
			assertEquals("22-23-й линий В.О.", textInstructions.grammarize("ru", "22-23-я линии В.О.", "genitive"));
			assertEquals("22-23-й линиях В.О.",
					textInstructions.grammarize("ru", "22-23-я линии В.О.", "prepositional"));
			assertEquals("Австрийскую площадь", textInstructions.grammarize("ru", "Австрийская площадь", "accusative"));
			assertEquals("Австрийской площади", textInstructions.grammarize("ru", "Австрийская площадь", "dative"));
			assertEquals("Австрийской площади", textInstructions.grammarize("ru", "Австрийская площадь", "genitive"));
			assertEquals("Австрийской площади",
					textInstructions.grammarize("ru", "Австрийская площадь", "prepositional"));
			assertEquals("Лялину площадь", textInstructions.grammarize("ru", "Лялина площадь", "accusative"));
			assertEquals("Лялиной площади", textInstructions.grammarize("ru", "Лялина площадь", "dative"));
			assertEquals("Лялиной площади", textInstructions.grammarize("ru", "Лялина площадь", "genitive"));
			assertEquals("Лялиной площади", textInstructions.grammarize("ru", "Лялина площадь", "prepositional"));
			assertEquals("1-й Тверской-Ямской переулок",
					textInstructions.grammarize("ru", "1-й Тверской-Ямской переулок", "accusative"));
			assertEquals("1-му Тверскому-Ямскому переулку",
					textInstructions.grammarize("ru", "1-й Тверской-Ямской переулок", "dative"));
			assertEquals("1-го Тверского-Ямского переулка",
					textInstructions.grammarize("ru", "1-й Тверской-Ямской переулок", "genitive"));
			assertEquals("1-м Тверском-Ямском переулке",
					textInstructions.grammarize("ru", "1-й Тверской-Ямской переулок", "prepositional"));
			assertEquals("Большой Сампсониевский проспект",
					textInstructions.grammarize("ru", "Большой Сампсониевский проспект", "accusative"));
			assertEquals("Большому Сампсониевскому проспекту",
					textInstructions.grammarize("ru", "Большой Сампсониевский проспект", "dative"));
			assertEquals("Большого Сампсониевского проспекта",
					textInstructions.grammarize("ru", "Большой Сампсониевский проспект", "genitive"));
			assertEquals("Большом Сампсониевском проспекте",
					textInstructions.grammarize("ru", "Большой Сампсониевский проспект", "prepositional"));
			assertEquals("Нижний Лебяжий мост", textInstructions.grammarize("ru", "Нижний Лебяжий мост", "accusative"));
			assertEquals("Нижнему Лебяжьему мосту", textInstructions.grammarize("ru", "Нижний Лебяжий мост", "dative"));
			assertEquals("Нижнего Лебяжьего моста",
					textInstructions.grammarize("ru", "Нижний Лебяжий мост", "genitive"));
			assertEquals("Нижнем Лебяжьем мосту",
					textInstructions.grammarize("ru", "Нижний Лебяжий мост", "prepositional"));
			assertEquals("Старо-Калинкин мост", textInstructions.grammarize("ru", "Старо-Калинкин мост", "accusative"));
			assertEquals("Старо-Калинкину мосту", textInstructions.grammarize("ru", "Старо-Калинкин мост", "dative"));
			assertEquals("Старо-Калинкина моста", textInstructions.grammarize("ru", "Старо-Калинкин мост", "genitive"));
			assertEquals("Старо-Калинкином мосту",
					textInstructions.grammarize("ru", "Старо-Калинкин мост", "prepositional"));
			assertEquals("Тучков мост", textInstructions.grammarize("ru", "Тучков мост", "accusative"));
			assertEquals("Тучкову мосту", textInstructions.grammarize("ru", "Тучков мост", "dative"));
			assertEquals("Тучкова моста", textInstructions.grammarize("ru", "Тучков мост", "genitive"));
			assertEquals("Тучковом мосту", textInstructions.grammarize("ru", "Тучков мост", "prepositional"));
			assertEquals("Пыхов-Церковный проезд",
					textInstructions.grammarize("ru", "Пыхов-Церковный проезд", "accusative"));
			assertEquals("Пыхову-Церковному проезду",
					textInstructions.grammarize("ru", "Пыхов-Церковный проезд", "dative"));
			assertEquals("Пыхова-Церковного проезда",
					textInstructions.grammarize("ru", "Пыхов-Церковный проезд", "genitive"));
			assertEquals("Пыховом-Церковном проезде",
					textInstructions.grammarize("ru", "Пыхов-Церковный проезд", "prepositional"));
			assertEquals("Третье Транспортное кольцо",
					textInstructions.grammarize("ru", "Третье Транспортное кольцо", "accusative"));
			assertEquals("Третьему Транспортному кольцу",
					textInstructions.grammarize("ru", "Третье Транспортное кольцо", "dative"));
			assertEquals("Третьего Транспортного кольца",
					textInstructions.grammarize("ru", "Третье Транспортное кольцо", "genitive"));
			assertEquals("Третьем Транспортном кольце",
					textInstructions.grammarize("ru", "Третье Транспортное кольцо", "prepositional"));
		}
	}

	@Test
	public void testTokenize() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {

			String tokenString = "Can {first} {second}";
			String options = "{}";

			JsonObject hasBoth = new JsonObject();
			hasBoth.addProperty("first", "osrm");
			hasBoth.addProperty("second", "do routing");

			assertEquals("Can osrm do routing",
					textInstructions.tokenize(LANGUAGE, tokenString, hasBoth.toString(), options));

			JsonObject hasFirst = new JsonObject();
			hasFirst.addProperty("first", "osrm");
			hasFirst.addProperty("second", "");

			assertEquals("Can osrm ", textInstructions.tokenize(LANGUAGE, tokenString, hasFirst.toString(), options));

			JsonObject hasSecond = new JsonObject();
			hasSecond.addProperty("first", "");
			hasSecond.addProperty("second", "swim");

			assertEquals("Can swim", textInstructions.tokenize(LANGUAGE, tokenString, hasSecond.toString(), options));

			JsonObject missingSecond = new JsonObject();
			missingSecond.addProperty("first", "osrm");

			assertEquals("Can osrm {second}",
					textInstructions.tokenize(LANGUAGE, tokenString, missingSecond.toString(), options));
		}
	}

	@Test
	public void testValidDirectionFromDegree() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
			assertEquals("", textInstructions.directionFromDegree(LANGUAGE, null));
			assertEquals("north", textInstructions.directionFromDegree(LANGUAGE, 0.));
			assertEquals("north", textInstructions.directionFromDegree(LANGUAGE, 1.));
			assertEquals("north", textInstructions.directionFromDegree(LANGUAGE, 20.));
			assertEquals("northeast", textInstructions.directionFromDegree(LANGUAGE, 21.));
			assertEquals("northeast", textInstructions.directionFromDegree(LANGUAGE, 69.));
			assertEquals("east", textInstructions.directionFromDegree(LANGUAGE, 70.));
			assertEquals("east", textInstructions.directionFromDegree(LANGUAGE, 110.));
			assertEquals("southeast", textInstructions.directionFromDegree(LANGUAGE, 111.));
			assertEquals("southeast", textInstructions.directionFromDegree(LANGUAGE, 159.));
			assertEquals("south", textInstructions.directionFromDegree(LANGUAGE, 160.));
			assertEquals("south", textInstructions.directionFromDegree(LANGUAGE, 200.));
			assertEquals("southwest", textInstructions.directionFromDegree(LANGUAGE, 201.));
			assertEquals("southwest", textInstructions.directionFromDegree(LANGUAGE, 249.));
			assertEquals("west", textInstructions.directionFromDegree(LANGUAGE, 250.));
			assertEquals("west", textInstructions.directionFromDegree(LANGUAGE, 290.));
			assertEquals("northwest", textInstructions.directionFromDegree(LANGUAGE, 291.));
			assertEquals("northwest", textInstructions.directionFromDegree(LANGUAGE, 339.));
			assertEquals("north", textInstructions.directionFromDegree(LANGUAGE, 340.));
			assertEquals("north", textInstructions.directionFromDegree(LANGUAGE, 360.));
		}
	}

	@Test
	public void testInvalidDirectionFromDegree() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
			assertEquals("", textInstructions.directionFromDegree(LANGUAGE, 361.));
		} catch (RuntimeException e) {
			// exception should be thrown
		}
	}

	@Test
	public void testLaneDiagram() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
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
				Map.Entry<String, LegStep> pair = (Map.Entry<String, LegStep>) entry;
				assertEquals(pair.getKey(), textInstructions.laneConfig((LegStep) pair.getValue()));
			}
		}
	}

	@Test
	public void testInvalidLaneDiagram() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
			assertEquals("", textInstructions.laneConfig(
					new LegStep(Collections.singletonList(new StepIntersection(new IntersectionLanes[] {})))));
		}
	}

}
