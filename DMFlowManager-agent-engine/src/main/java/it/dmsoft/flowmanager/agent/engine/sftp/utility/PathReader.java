package it.dmsoft.flowmanager.agent.engine.sftp.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tools.ant.DirectoryScanner;

public class PathReader {
	
	private static final String[] JOLLY_CHARS = {"*", "?"};
	
	public static void main(String[] args) throws FileNotFoundException {
		String path = "C:\\Users\\davidecr\\Desktop\\DCTintervento.ra*";
		System.out.println(getFilePaths(path));
	}
	
	public static List<String> getFilePaths(String path) throws FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		File baseDir = new File(path);
		if (!baseDir.exists()) {
			boolean containsJolly = true;
			sb.append(baseDir.getName());
			while(containsJolly) {
				File tmpBaseDir = baseDir.getParentFile();
				if (tmpBaseDir != null) {
					String absPath = tmpBaseDir.getAbsolutePath();
					containsJolly = containsJolly(absPath);
					if (containsJolly) {
						sb.insert(0, String.format("%s%s", tmpBaseDir.getName(), File.separator));
					}
					baseDir = tmpBaseDir;
				}
			}
			DirectoryScanner scanner = new DirectoryScanner();
			scanner.setIncludes(new String[]{sb.toString()});
			scanner.setBasedir(baseDir.getAbsoluteFile());
			scanner.setCaseSensitive(false);
			scanner.scan();
			String[] files = scanner.getIncludedFiles();
			
			List<String> filePaths = new ArrayList<String>();
			for(String file : files) {
				filePaths.add(baseDir.getAbsolutePath() + File.separator + file);
			}
			if (filePaths.isEmpty()) {
				throw new FileNotFoundException(path);
			}
			return filePaths;
		} 
		return Collections.singletonList(path);
	}

	private static boolean containsJolly(String absPath) {
		for (String jolly : JOLLY_CHARS) {
			if (absPath.contains(jolly)) {
				return true;
			}
		}
		return false;
	}
}
