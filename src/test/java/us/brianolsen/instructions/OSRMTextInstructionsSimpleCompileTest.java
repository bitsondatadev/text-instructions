package us.brianolsen.instructions;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Test;

import com.mapbox.services.api.directions.v5.models.LegStep;
import com.mapbox.services.api.directions.v5.models.StepManeuver;

import us.brianolsen.instructions.util.ResourceUtil;

public class OSRMTextInstructionsSimpleCompileTest extends BaseTest {
	protected static final String FIXTURES_DIRECTORY = Paths.get( //
			ResourceUtil.getNodeModuleDirectory(MODULE_NAME, MODULE_VERSION).getAbsolutePath(), //
			"test", "fixtures", VERSION).toString();

	@Test
	public void testSimpleCompileCommands() {
		try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
			String wayName = "Route 66";
			StepManeuver maneuver = new StepManeuver("turn", "left", 1);

			LegStep step = new LegStep();
			step.setManeuver(maneuver);
			step.setName(wayName);

			assertEquals("Turn left onto Route 66", textInstructions.compile("en", step, null));
			assertEquals("Gire a la izquierda en Route 66", textInstructions.compile("es", step, null));
			assertEquals("Svolta a sinistra in Route 66", textInstructions.compile("it", step, null));
			assertEquals("Ga linksaf naar Route 66", textInstructions.compile("nl", step, null));
			assertEquals("左转，上Route 66", textInstructions.compile("zh-Hans", step, null));

			maneuver = new StepManeuver();
			maneuver.setBearingAfter(340);
			maneuver.setType("depart");
			step.setManeuver(maneuver);

			assertEquals("Head north on Route 66", textInstructions.compile("en", step, null));
			assertEquals("Ve a norte en Route 66", textInstructions.compile("es", step, null));
			assertEquals("Continua verso nord in Route 66", textInstructions.compile("it", step, null));
			assertEquals("Neem Route 66 in noordelijke richting", textInstructions.compile("nl", step, null));
			assertEquals("出发向北，上Route 66", textInstructions.compile("zh-Hans", step, null));

			maneuver = new StepManeuver();
			maneuver.setBearingAfter(152);
			maneuver.setType("depart");
			step.setManeuver(maneuver);

			assertEquals("Head southeast on Route 66", textInstructions.compile("en", step, null));
			assertEquals("Ve a sureste en Route 66", textInstructions.compile("es", step, null));
			assertEquals("Continua verso sud-est in Route 66", textInstructions.compile("it", step, null));
			assertEquals("Neem Route 66 in zuidoostelijke richting", textInstructions.compile("nl", step, null));
			assertEquals("出发向东南，上Route 66", textInstructions.compile("zh-Hans", step, null));

			maneuver = new StepManeuver();
			maneuver.setType("fork");
			maneuver.setModifier("sharp right");
			step.setManeuver(maneuver);

			assertEquals("Take a sharp right at the fork onto Route 66", textInstructions.compile("en", step, null));
			assertEquals("Gire a la derecha en el cruce en Route 66", textInstructions.compile("es", step, null));
			assertEquals("Svolta a destra al bivio in Route 66", textInstructions.compile("it", step, null));
			assertEquals("Rechtsaf op de splitsing naar Route 66", textInstructions.compile("nl", step, null));
			assertEquals("在岔道保持向右，上Route 66", textInstructions.compile("zh-Hans", step, null));

		} catch (RuntimeException e) {
			System.err.println(e);
		}
	}

}
