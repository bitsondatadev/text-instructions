package us.brianolsen.instructions.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;

public class ResourceUtil {
	private static final String J2V8_TEMP_DIR = "j2v8-tempdir";
	private static final String NODE_MODULES_DIRECTORY = "/node_modules/";

	public static File getNodeModuleDirectory(String moduleName, String moduleVersion) {
		File tempDir = createTempDirectory(J2V8_TEMP_DIR);
		String moduleDirName = moduleName + "-" + moduleVersion;
		File tempFile = Paths.get(tempDir.getAbsolutePath(), moduleDirName).toFile();

		if (!tempFile.exists()) {
			tempFile = ResourceUtil.unzipJarResourceToTempDirectory(NODE_MODULES_DIRECTORY + moduleDirName + ".zip");
		}

		return tempFile;
	}

	public static File unzipJarResourceToTempDirectory(String jarFilePath) {
		if (jarFilePath == null) {
			return null;
		}

		try {
			InputStream fileStream = getResourceAsStream(jarFilePath);

			if (fileStream == null) {
				return null;
			}

			File tempDir = createTempDirectory(J2V8_TEMP_DIR);

			String fileName = FilenameUtils.removeExtension(Paths.get(jarFilePath).getFileName().toString());
			File tempFile = Paths.get(tempDir.getAbsolutePath(), fileName).toFile();
			if (!tempFile.exists()) {
				UnzipUtility.unzip(fileStream, tempDir.getAbsolutePath());
			}

			fileStream.close();

			return tempFile;

		} catch (IOException e) {
			return null;
		}
	}

	public static File createTempDirectory(String directoryPath) {
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		File tempDir = new File(baseDir, directoryPath);
		if (tempDir.exists()) {
			if (!tempDir.isDirectory()) {
				throw new IllegalArgumentException("directory path references existing file");
			}
			return tempDir;
		}
		if (tempDir.mkdir()) {
			return tempDir;
		}

		return baseDir;// FIXME should throw error instead?
	}

	public static InputStream getResourceAsStream(String fileFromResourceRoot) {
		return ResourceUtil.class.getResourceAsStream(fileFromResourceRoot);
	}

}
