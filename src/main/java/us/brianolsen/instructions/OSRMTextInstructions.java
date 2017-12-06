package us.brianolsen.instructions;

import java.io.Closeable;
import java.io.File;

import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;
import com.mapbox.services.api.directions.v5.models.LegStep;

public class OSRMTextInstructions implements Closeable {
	private static final String MODULE_NAME = "osrm-text-instructions";
	private static final String RESOURCES_DIRECTORY = OSRMTextInstructions.class.getClassLoader().getResource("")
			.getPath() + "/";
	private static final String NODE_MODULES_DIRECTORY = RESOURCES_DIRECTORY + "node_modules/";
	private static final String OSRM_TEXT_INSTRUCTIONS_MODULE_DIRECTORY = NODE_MODULES_DIRECTORY + MODULE_NAME + "/";

	private NodeJS nodeJS;
	private V8Object osrmTextInstructions;
	private String version = "v5";

	public V8 getRuntime() {
		return nodeJS.getRuntime();
	}

	public NodeJS getNodeJS() {
		return nodeJS;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public OSRMTextInstructions(String version) {
		this.setVersion(version);
		init();
	}

	private void init() {
		nodeJS = NodeJS.createNodeJS();
		V8Function osrmModule = (V8Function) getNodeJS().require(new File(OSRM_TEXT_INSTRUCTIONS_MODULE_DIRECTORY));

		V8Array parameters = new V8Array(osrmModule.getRuntime());
		parameters.push(getVersion());

		osrmTextInstructions = (V8Object) osrmModule.call(osrmModule.getRuntime(), parameters);

		parameters.release();
		osrmModule.release();
	}

	@Override
	public void close() {
		try {
			osrmTextInstructions.release();
			nodeJS.release();
		} finally {
			osrmTextInstructions = null;
			nodeJS = null;
		}
	}

	public String capitalizeFirstLetter(String language, String string) {
		return (String) osrmTextInstructions.executeJSFunction("capitalizeFirstLetter", language, string);
	}

	public String ordinalize(String language, Integer number) {
		return (String) osrmTextInstructions.executeJSFunction("ordinalize", language, number);
	}

	public String directionFromDegree(String language, Double degree) {
		return (String) osrmTextInstructions.executeJSFunction("directionFromDegree", language, degree);
	}

	public String laneConfig(LegStep step) {
		throw new UnsupportedOperationException("still needs to be implemented");
	}

	public String getWayName(String language, LegStep step, String options) {
		throw new UnsupportedOperationException("still needs to be implemented");

	}

	public String compile(String language, V8Object step, V8Object options) {
		// FIXME take LegStep step from mapbox
		return (String) osrmTextInstructions.executeJSFunction("compile", language, step, options);
	}

	public String compile(String language, LegStep step, V8Object options) {
		throw new UnsupportedOperationException("still needs to be implemented");
	}

	public String grammarize(String language, String name, String grammar) {
		throw new UnsupportedOperationException("still needs to be implemented");

	}

	public String tokenize(String language, String instruction, String tokens, String options) {
		throw new UnsupportedOperationException("still needs to be implemented");
	}

	public String getBestMatchingLanguage(String language) {
		return (String) osrmTextInstructions.executeJSFunction("getBestMatchingLanguage", language);
	}

	// private static File createTemporaryScriptFile(final String script, final
	// String name) throws IOException {
	// File tempFile = File.createTempFile(name, ".js.tmp", new
	// File(RESOURCES_DIRECTORY));
	// PrintWriter writer = new PrintWriter(tempFile, "UTF-8");
	// try {
	// writer.print(script);
	// } finally {
	// writer.close();
	// }
	// return tempFile;
	// }
}
