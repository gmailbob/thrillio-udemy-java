package com.bob.thrillio.util;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOUtil {
	public static void read(List<String> data, String filename) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
			String line;
			while ((line = br.readLine()) != null) {
				data.add(line);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String read(InputStream inputStream) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + '\n');
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void write(String webpage, long id) {
		// use methods from nio, which is different from the demo
		try {
			// the non-existing file will be created automatically
			// but directory should be there
			Files.writeString(Paths.get(System.getProperty("user.home"), "Desktop", id + ".html"), webpage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
