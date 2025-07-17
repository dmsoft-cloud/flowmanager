package it.dmsoft.flowmanager.agent.engine.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class OCSZipper {

	private static Logger logger;

	public static void zip(Logger loggerExt, File fileToZip, String destPath) throws IOException {
		logger = loggerExt;
		FileOutputStream fos = new FileOutputStream(destPath);
		ZipOutputStream zos = new ZipOutputStream(fos);
		addDirToZipArchive(zos, fileToZip, null, true);
		zos.flush();
		fos.flush();
		zos.close();
		fos.close();
	}

	private static void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parentDirectoryName, boolean firstTime) throws IOException {
		if (fileToZip == null || !fileToZip.exists()) {
			return;
		}

		String zipEntryName = fileToZip.getName();
		if (parentDirectoryName != null && !parentDirectoryName.isEmpty()) {
			zipEntryName = parentDirectoryName + File.separator + fileToZip.getName();
		}

		if (fileToZip.isDirectory()) {
			if (logger.isDebugEnabled()) {
				logger.debug(fileToZip.getAbsolutePath() + "(directory)");
			}
			for (File file : fileToZip.listFiles()) {
				addDirToZipArchive(zos, file, firstTime ? null : zipEntryName, false);
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("   " + zipEntryName + "(file)");
			}
			byte[] buffer = new byte[1024];
			FileInputStream fis = new FileInputStream(fileToZip);
			zos.putNextEntry(new ZipEntry(zipEntryName));
			int length;
			while ((length = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, length);
			}
			zos.closeEntry();
			fis.close();
		}
	}

	public static void unzip(Logger loggerExt, File fileToUnzip, String destPath) throws IOException {
		logger = loggerExt;
		File destPathFile = new File(destPath);
		if (!destPathFile.exists()) {
			destPathFile.mkdirs();
		}
		File destinationDir = new File(destPath);
		ZipInputStream zis = new ZipInputStream(new FileInputStream(fileToUnzip));
		saveDirFromZipArchive(zis, destinationDir);
		zis.closeEntry();
		zis.close();
	}

	private static void saveDirFromZipArchive(ZipInputStream zis, File destinationDir) throws IOException {
		ZipEntry entry = zis.getNextEntry();
		if (entry == null) {
			return;
		}
		if (entry.isDirectory()) {
			new File(destinationDir + File.separator + entry.getName()).mkdirs();
		} else {
			byte[] buffer = new byte[1024];

			File file = newFile(destinationDir, entry);
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
		}
		saveDirFromZipArchive(zis, destinationDir);
	}

	private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());
		String destFilePath = destFile.getCanonicalPath();
		String destDirPath = destinationDir.getCanonicalPath();

		// Previene Zip Slip
		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			if (logger.isDebugEnabled()) {
				logger.debug(new KeyValueLog("destFilePath", destFilePath), new KeyValueLog("destDirPath", destDirPath));
			}
			logger.warn(String.format("Entry (%s) is outside of the target dir (%s)!", zipEntry.getName(), destinationDir.getAbsolutePath()));
		}
		return destFile;
	}
	
	public static boolean isArchive(File f) throws IOException {
	    int fileSignature = 0;
	    RandomAccessFile raf = null;
	    try {
	    	raf = new RandomAccessFile(f, "r");
	        fileSignature = raf.readInt();
	    } finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					//do nothing
				}
			}
		}
	    return fileSignature == 0x504B0304 || fileSignature == 0x504B0506 || fileSignature == 0x504B0708 || fileSignature == 0x377ABCAF;
	}
	
	public static void main(String[] args) throws IOException {
		File f = new File("C:\\Users\\davidecr\\Desktop\\DCTintervento.txt");
		System.out.println(isArchive(f));
	}

}
