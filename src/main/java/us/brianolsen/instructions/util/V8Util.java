package us.brianolsen.instructions.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.V8Value;

public class V8Util {

	public static void add(V8Object object, String key, Integer value) {
		if (value != null) {
			object.add(key, value);
		}
	}

	public static void add(V8Object object, String key, Double value) {
		if (value != null) {
			object.add(key, value);
		}
	}

	public static void add(V8Object object, String key, Boolean value) {
		if (value != null) {
			object.add(key, value);
		}
	}

	public static void add(V8Object object, String key, String value) {
		if (value != null) {
			object.add(key, value);
		}
	}

	public static void add(V8Object object, String key, V8Array value) {
		if (value != null && value.length() > 0) {
			object.add(key, value);
		}
		release(value);
	}

	public static void add(V8Object object, String key, V8Object value) {
		if (value != null && value.getKeys().length > 0) {
			object.add(key, value);
		}
		release(value);
	}

	public static void add(V8Object object, String key, V8Value value) {
		if (value != null) {
			object.add(key, value);
		}
		release(value);
	}

	public static Object get(V8Object object, String key) {
		if (object.contains(key)) {
			return object.get(key);
		}
		return null;
	}

	public static V8Array getV8Array(V8Object object, String key) {
		if (object.contains(key)) {
			return (V8Array) object.get(key);
		}
		return null;
	}

	public static void release(V8Value value) {
		if (value != null && !value.isReleased()) {
			value.release();
		}
	}

	public static V8Object jsonStringToV8Object(V8 runtime, String json) {
		V8Object V8JSON = runtime.getObject("JSON");
		V8Array parameters = new V8Array(runtime).push(json == null || json.isEmpty() ? "{}" : json);

		V8Object result = V8JSON.executeObjectFunction("parse", parameters);

		parameters.release();
		V8JSON.release();

		return result;
	}

	public static File createTemporaryScriptFile(final String script, final String name) throws IOException {
		File tempFile = File.createTempFile(name, ".js.tmp", new File(ResourceUtil.RESOURCES_DIRECTORY));
		PrintWriter writer = new PrintWriter(tempFile, "UTF-8");
		try {
			writer.print(script);
		} finally {
			writer.close();
		}
		return tempFile;
	}
}
