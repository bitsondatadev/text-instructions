package us.brianolsen.instructions.model;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.mapbox.services.api.directions.v5.models.IntersectionLanes;
import com.mapbox.services.api.directions.v5.models.LegStep;
import com.mapbox.services.api.directions.v5.models.StepIntersection;
import com.mapbox.services.api.directions.v5.models.StepManeuver;

import us.brianolsen.instructions.util.V8Util;

public class V8LegStep implements Closeable {
	private final V8 runtime;
	private final V8Object v8Step;

	public V8LegStep(LegStep legStep, V8 runtime) {
		this.runtime = runtime;
		this.v8Step = convertFromLegStep(legStep);
	}

	public V8 getRuntime() {
		return runtime;
	}

	public V8Object getV8Step() {
		return v8Step;
	}

	@Override
	public void close() throws IOException {
		releaseV8LegStep();
	}

	private V8Object convertFromLegStep(LegStep legStep) {
		V8Object step = new V8Object(getRuntime());
		V8Object tempManeuver = convertFromStepManeuver(legStep.getManeuver());
		V8Array tempIntersections = convertFromStepIntersectionList(legStep.getIntersections());

		V8Util.add(step, "destinations", legStep.getDestinations());
		V8Util.add(step, "distance", legStep.getDistance());
		V8Util.add(step, "duration", legStep.getDuration());
		V8Util.add(step, "geometry", legStep.getGeometry());
		V8Util.add(step, "mode", legStep.getMode());
		V8Util.add(step, "pronunciation", legStep.getPronunciation());
		V8Util.add(step, "ref", legStep.getRef());
		V8Util.add(step, "rotary_name", legStep.getRotaryName());
		V8Util.add(step, "rotary_pronunciation", legStep.getRotaryPronunciation());
		V8Util.add(step, "intersections", tempIntersections);
		V8Util.add(step, "weight", legStep.getWeight());
		V8Util.add(step, "maneuver", tempManeuver);
		V8Util.add(step, "name", legStep.getName());

		V8Util.release(tempManeuver);
		V8Util.release(tempIntersections);
		return step;
	}

	private V8Object convertFromStepManeuver(StepManeuver stepManeuver) {
		if (stepManeuver == null) {
			return null;
		}
		V8Object maneuver = new V8Object(getRuntime());
		V8Util.add(maneuver, "bearing_after", stepManeuver.getBearingAfter());
		V8Util.add(maneuver, "bearing_before", stepManeuver.getBearingBefore());
		V8Util.add(maneuver, "instruction", stepManeuver.getInstruction());
		V8Util.add(maneuver, "modifier", stepManeuver.getModifier());
		V8Util.add(maneuver, "type", stepManeuver.getType());
		V8Util.add(maneuver, "exit", stepManeuver.getExit());
		return maneuver;
	}

	private V8Array convertFromStepIntersectionList(List<StepIntersection> stepIntersections) {
		if (stepIntersections == null || stepIntersections.isEmpty()) {
			return null;
		}

		V8Array v8Array = new V8Array(getRuntime());
		if (stepIntersections != null) {
			for (StepIntersection stepIntersection : stepIntersections) {
				V8Object tempStepIntersection = convertFromStepIntersection(stepIntersection);
				v8Array.push(tempStepIntersection);
				V8Util.release(tempStepIntersection);
			}
		}
		return v8Array;
	}

	private V8Object convertFromStepIntersection(StepIntersection stepIntersection) {
		if (stepIntersection == null) {
			return null;
		}
		V8Object intersection = new V8Object(getRuntime());
		V8Util.add(intersection, "in", stepIntersection.getIn());
		V8Util.add(intersection, "out", stepIntersection.getOut());

		V8Array tempBearings = new V8Array(getRuntime());
		V8Array tempEntry = new V8Array(getRuntime());
		V8Array tempLanes = new V8Array(getRuntime());
		V8Array tempLocation = new V8Array(getRuntime());

		if (stepIntersection.getBearings() != null) {
			for (Integer bearing : stepIntersection.getBearings()) {
				tempBearings.push(bearing);
			}
		}
		V8Util.add(intersection, "bearings", tempBearings);

		if (stepIntersection.getEntry() != null) {
			for (Boolean lane : stepIntersection.getEntry()) {
				tempEntry.push(lane);
			}
		}
		V8Util.add(intersection, "entry", tempEntry);

		if (stepIntersection.getLanes() != null) {
			for (IntersectionLanes lane : stepIntersection.getLanes()) {
				V8Object tempLane = convertFromIntersectionLanes(lane);
				tempLanes.push(tempLane);
				V8Util.release(tempLane);
			}
		}
		V8Util.add(intersection, "lanes", tempLanes);

		if (stepIntersection.getLocation() != null) {
			for (Double item : stepIntersection.getLocation()) {
				tempLocation.push(item);
			}
		}
		V8Util.add(intersection, "location", tempLocation);

		V8Util.release(tempBearings);
		V8Util.release(tempEntry);
		V8Util.release(tempLanes);
		V8Util.release(tempLocation);

		return intersection;
	}

	private V8Object convertFromIntersectionLanes(IntersectionLanes intersectionLanes) {
		if (intersectionLanes == null) {
			return null;
		}
		V8Object lanes = new V8Object(getRuntime());
		V8Util.add(lanes, "valid", intersectionLanes.getValid());

		V8Array indications = new V8Array(getRuntime());
		if (intersectionLanes.getIndications() != null) {
			for (String indication : intersectionLanes.getIndications()) {
				indications.push(indication);
			}
		}
		V8Util.add(lanes, "indications", indications);

		return lanes;
	}

	private void releaseV8LegStep() {
		V8Object maneuver = (V8Object) V8Util.get(v8Step, "maneuver");
		V8Array intersections = (V8Array) V8Util.getV8Array(v8Step, "intersections");

		if (intersections != null) {

			for (int i = 0; i < intersections.length(); i++) {
				V8Object stepIntersection = (V8Object) intersections.get(i);
				V8Array bearings = (V8Array) V8Util.getV8Array(stepIntersection, "bearings");
				V8Array entry = (V8Array) V8Util.getV8Array(stepIntersection, "entry");
				V8Array lanes = (V8Array) V8Util.getV8Array(stepIntersection, "lanes");
				V8Array location = (V8Array) V8Util.getV8Array(stepIntersection, "location");

				if (lanes != null) {
					for (int j = 0; j < lanes.length(); j++) {
						V8Object intersectionLanes = (V8Object) lanes.get(j);
						V8Array indications = (V8Array) V8Util.getV8Array(stepIntersection, "indications");

						V8Util.release(indications);
						V8Util.release(intersectionLanes);
					}
				}

				V8Util.release(bearings);
				V8Util.release(entry);
				V8Util.release(lanes);
				V8Util.release(location);
				V8Util.release(stepIntersection);
			}
		}

		V8Util.release(intersections);
		V8Util.release(maneuver);
		V8Util.release(v8Step);
	}
}
