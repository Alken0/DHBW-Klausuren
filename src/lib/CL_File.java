package lib;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CL_File {

	private final static String fileExtension = ".txt";
	private final static boolean suppressExceptionOutput = true;

	public static void setContentOfFile(String fileName, String content) {
		try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(fileName + fileExtension))) {
			bw.write(content);
		} catch (Exception e) {
			if (!suppressExceptionOutput) {
				System.out.println("ERROR in Logger.setContentOfFile()");
				e.printStackTrace();
			}
		}
	}

	public static String getContentOfFile(String fileName) {
		try {
			return Files.readString(Paths.get(fileName + fileExtension));
		} catch (Exception e) {
			if (!suppressExceptionOutput) {
				System.out.println("ERROR in Logger.getContentOfFile()");
				e.printStackTrace();
			}
		}
		return "";
	}

	public static void appendNewLineToFile(String fileName, String line) {
		try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(fileName + fileExtension, true))) {
			bw.write(line);
			bw.newLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Bei der Erstellung der Speicherdatei ist etwas " +
					"schief gegangen :/");
			if (!suppressExceptionOutput) {
				System.out.println("ERROR in Logger.appendNewLine()");
				e.printStackTrace();
			}
		}
	}

	public static List<String> getAllLinesFromFile(String fileName) {
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName + fileExtension))) {
			while (br.ready()) {
				lines.add(br.readLine());
			}
		} catch (Exception e) {
			if (!suppressExceptionOutput) {
				System.out.println("ERROR in Logger.readAllLinesFromFile()");
				e.printStackTrace();
			}
		}
		return lines;
	}

	public static String getFileExtension() {
		return fileExtension;
	}
}
