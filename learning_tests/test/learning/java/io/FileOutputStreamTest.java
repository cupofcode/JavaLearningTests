package test.learning.java.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileOutputStreamTest {
	private File file;
	private FileOutputStream fileOutput;
	private FileInputStream fileInput;
	
	@Before
	public void setUp() {
		file = new File("file");
	}
	
	@After
	public void tearDown() throws IOException {
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
	public void bytesWrittenIntoFileOutputStreamAreReadFromFileInputStream() throws IOException {
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
	public void fileOutputStreamConstructorCreatesTheRealFileOnDisk() throws IOException {
		assertFalse("File should NOT be creted on disk", file.exists());
		
		fileOutput = new FileOutputStream(file);
		assertTrue("File should be creted on disk", file.exists());
	}
	
	@Test
	public void writeAfterReopeningTheFileOverwritesByDefault() throws IOException {		
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
	public void appendingNewBytesAfterReopeningTheFile() throws IOException {		
		fileOutput = new FileOutputStream(file);
		fileOutput.write(1);
		fileOutput.close();
		
		// Reopen and rewrite
		fileOutput = new FileOutputStream(file, true);
		fileOutput.write(2);
		
		fileInput = new FileInputStream(file);
		assertEquals(1, fileInput.read());
		assertEquals("Appended value", 2, fileInput.read());
		assertEquals("Value after all bytes read", -1, fileInput.read());
	}

}
