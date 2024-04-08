package test.learning.java.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileStreamsTest {
	private File file;
	private FileOutputStream fileOutput;
	private FileInputStream fileInput;
	
	@BeforeEach
	void setUp() {
		file = new File("file");
	}
	
	@AfterEach
	void tearDown() throws IOException {
		if (fileOutput != null) {
			fileOutput.close();
		}
		
		if (fileInput != null) {
			fileInput.close();
		}
		
		if (file != null) {
			file.delete();
		}
	}

	@Test
	void bytesWrittenIntoFileOutputStreamAreReadFromFileInputStream() throws IOException {
		fileOutput = new FileOutputStream(file);
		
		fileOutput.write(1);
		fileOutput.write(2);
		fileOutput.close();
		
		fileInput = new FileInputStream(file);
		assertEquals(1, fileInput.read());
		assertEquals(2, fileInput.read());
		assertEquals("Value after all bytes read", -1, fileInput.read());
		assertEquals("Value after all bytes read", -1, fileInput.read());
	}
	
	@Test
	void fileOutputStreamConstructorCreatesTheRealFileOnDisk() throws IOException {
		assertFalse("File should NOT be creted on disk", file.exists());
		
		fileOutput = new FileOutputStream(file);
		assertTrue("File should be creted on disk", file.exists());
	}
	
	@Test
	void writeAfterReopeningTheFileOverwritesByDefault() throws IOException {		
		fileOutput = new FileOutputStream(file);
		fileOutput.write(1);
		fileOutput.close();
		
		// Reopen and rewrite
		fileOutput = new FileOutputStream(file);
		fileOutput.write(2);
		
		fileInput = new FileInputStream(file);
		assertEquals(2, fileInput.read());
		assertEquals("Value after all bytes read", -1, fileInput.read());
	}
	
	@Test
	void appendingNewBytesAfterReopeningTheFile() throws IOException {		
		fileOutput = new FileOutputStream(file);
		fileOutput.write(1);
		fileOutput.close();
		
		// Reopen and rewrite
		boolean append = true;
		fileOutput = new FileOutputStream(file, append);
		fileOutput.write(2);
		
		fileInput = new FileInputStream(file);
		assertEquals(1, fileInput.read());
		assertEquals("Appended value", 2, fileInput.read());
		assertEquals("Value after all bytes read", -1, fileInput.read());
	}

}
