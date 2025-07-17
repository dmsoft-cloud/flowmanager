package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class UtilityZip {

	/**
	 * Legge uno byte array di input compresso con ZIP e restituisce un byte
	 * array decompresso
	 * 
	 * @param input
	 *            byte array di input compresso con ZIP
	 * @return byte array decompresso
	 * @throws IOException
	 */
	public static byte[] unZip(byte[] input) throws IOException {
		ZipInputStream zipin = new ZipInputStream(new ByteArrayInputStream(input));
		while ((zipin.getNextEntry()) != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int b = zipin.read();
			while (b >= 0) {
				baos.write(b);
				b = zipin.read();
			}
			zipin.close();
			return (baos.toByteArray());
		}

		zipin.close();
		return new byte[0];
	}

	/**
	 * 
	 * @param input
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] zip(byte[] input, String filename) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(bos);

		try {
			zos.putNextEntry(new ZipEntry(filename));
			zos.write(input);
			zos.closeEntry();
		} finally {
			zos.close();
		}
		return bos.toByteArray();
	}
}
