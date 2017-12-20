package us.brianolsen.instructions.util;

import java.io.File;
import java.net.URISyntaxException;

public class ResourceUtil {

	public static final String RESOURCES_DIRECTORY = getResource("/").getAbsolutePath();

	public static File getResource(String fileFromResourceRoot) {
		File directory = null;
		try {
			directory = new File(ResourceUtil.class.getResource(fileFromResourceRoot).toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return directory;
	}
}
