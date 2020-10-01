package test_lib;

import lib.FileHandler;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileHandlerTests {

	private final String fileName = "test-file";
	private final String path = fileName + FileHandler.getFileExtension();

	@After
	public void clearContentOfFile() throws IOException {
		var writer = new PrintWriter(path);
		writer.print("");
		writer.close();
	}

	@Test
	public void setContentOfFile() throws IOException {
		String content = "test-content";

		FileHandler.setContentOfFile(fileName, content + "other content");
		FileHandler.setContentOfFile(fileName, content);

		assertTrue(new File(path).isFile());

		var actualContent = Files.readString(Paths.get(path));
		assertEquals(actualContent, content);
	}

	@Test
	public void setAndGetContentOfFile() {
		String content = "test-content";

		FileHandler.setContentOfFile(fileName, content);
		var returnValue = FileHandler.getContentOfFile(fileName);

		assertEquals(content, returnValue);
	}

	@Test
	public void appendNewLineToFile() {
		String line = "test-line";

		FileHandler.appendNewLineToFile(fileName, line);
		FileHandler.appendNewLineToFile(fileName, line);

		var expectedOutput = line + System.lineSeparator() + line + System.lineSeparator();

		assertEquals(expectedOutput, FileHandler.getContentOfFile(fileName));
	}

	@Test
	public void appendAndGetLinesGetFile() {
		String line = "test-line";

		FileHandler.appendNewLineToFile(fileName, line);
		FileHandler.appendNewLineToFile(fileName, line);

		var expectedOutput = Arrays.asList(line, line);

		assertEquals(expectedOutput, FileHandler.getAllLinesFromFile(fileName));
	}

}
