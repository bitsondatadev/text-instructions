package us.brianolsen.instructions;

import java.io.Closeable;
import java.io.File;

import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;
import com.google.gson.Gson;
import com.mapbox.services.api.directions.v5.models.LegStep;

import us.brianolsen.instructions.util.ResourceUtil;
import us.brianolsen.instructions.util.V8Util;

public class OSRMTextInstructions implements Closeable {
	protected static final String DEFAULT_VERSION = "v5";
	protected static final String DEFAULT_LANGUAGE = "en";
	protected static final String MODULE_NAME = "osrm-text-instructions";
	protected static final String MODULE_VERSION = "0.11.1";
	protected static final Gson gson = new Gson();

	private NodeJS nodeJS;
	private V8Object osrmTextInstructions;
	private String version = DEFAULT_VERSION;

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

		File directory = ResourceUtil.getNodeModuleDirectory(MODULE_NAME, MODULE_VERSION);
		V8Function osrmModule = (V8Function) getNodeJS().require(directory);

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
		V8Object v8Step = V8Util.jsonStringToV8Object(getRuntime(), gson.toJson(step));
		String laneConfig = (String) osrmTextInstructions.executeJSFunction("laneConfig", v8Step);

		v8Step.release();
		return laneConfig;
	}

	public String getWayName(String language, LegStep step, String options) {
		V8Object v8Step = V8Util.jsonStringToV8Object(getRuntime(), gson.toJson(step));
		V8Object v8Options = V8Util.jsonStringToV8Object(getRuntime(), options);
		String wayName = (String) osrmTextInstructions.executeJSFunction("getWayName", language, v8Step, v8Options);

		v8Options.release();
		v8Step.release();
		return wayName;
	}

	public String compile(String language, LegStep step, String options) {
		V8Object v8Step = V8Util.jsonStringToV8Object(getRuntime(), gson.toJson(step));
		V8Object v8Options = V8Util.jsonStringToV8Object(getRuntime(), options);
		String instruction = (String) osrmTextInstructions.executeJSFunction("compile", language, v8Step, v8Options);

		v8Options.release();
		v8Step.release();
		return instruction;

	}

	public String grammarize(String language, String name, String grammar) {
		return (String) osrmTextInstructions.executeJSFunction("grammarize", language, name, grammar);
	}

	public String tokenize(String language, String instruction, String tokens, String options) {
		V8Object v8Tokens = V8Util.jsonStringToV8Object(getRuntime(), tokens);
		V8Object v8Options = V8Util.jsonStringToV8Object(getRuntime(), options);
		String tokenizedString = (String) osrmTextInstructions.executeJSFunction("tokenize", language, instruction,
				v8Tokens, v8Options);

		v8Options.release();
		v8Tokens.release();
		return tokenizedString;
	}

	public String getBestMatchingLanguage(String language) {
		return (String) osrmTextInstructions.executeJSFunction("getBestMatchingLanguage", language);
	}

}
