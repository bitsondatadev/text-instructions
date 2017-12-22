package us.brianolsen.instructions.util;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

public class V8Util {

	public static V8Object jsonStringToV8Object(V8 runtime, String json) {
		V8Object V8JSON = runtime.getObject("JSON");
		V8Array parameters = new V8Array(runtime).push(json == null || json.isEmpty() ? "{}" : json);

		V8Object result = V8JSON.executeObjectFunction("parse", parameters);

		parameters.release();
		V8JSON.release();

		return result;
	}

	// public static File createTemporaryScriptFile(final String script, final
	// String name) throws IOException {
	// File tempFile = File.createTempFile(name, ".js.tmp");
	// PrintWriter writer = new PrintWriter(tempFile, "UTF-8");
	// try {
	// writer.print(script);
	// } finally {
	// writer.close();
	// }
	// return tempFile;
	// }
}
