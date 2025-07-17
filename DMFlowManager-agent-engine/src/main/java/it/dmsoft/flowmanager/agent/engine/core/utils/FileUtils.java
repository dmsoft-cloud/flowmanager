package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class FileUtils {
	
	private static final Logger logger = Logger.getLogger(FileUtils.class.getName());
	
	public static BasicFileAttributes getFileAttributes(String filePath) {
		Path path = Paths.get(filePath);
	    try {
	      // read file's attribute as a bulk operation
	      return Files.readAttributes(path, BasicFileAttributes.class);
	    } catch (IOException e ) {
	        logger.info("Error while reading file attributes " + e.getMessage());
	    }    
	    
	    return null;
	}

}
