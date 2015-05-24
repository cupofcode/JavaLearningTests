package test.learning.java.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class _7_FileOutputStreamTest {

	@Test
	public void bytesWrittenIntoFileOutputStreamAreReadFromFileInputStream() throws IOException {
		File file = new File("file");
		FileOutputStream fileOutput = new FileOutputStream(file);
		
		fileOutput.write(1);
		fileOutput.write(2);
		fileOutput.close();
		
		FileInputStream fileInput = new FileInputStream(file);
		assertEquals(1, fileInput.read());
		assertEquals(2, fileInput.read());
		assertEquals("should be -1 after EOF", -1, fileInput.read());
		assertEquals("should be -1 after EOF", -1, fileInput.read());
		
		fileInput.close();
		file.delete();
	}
	
	@Test
	public void fileOutputStreamConstructorCreatesTheRealFileOnDisk() throws IOException {
		File file = new File("file");
		assertFalse("File should NOT be creted on disk", file.exists());
		
		FileOutputStream fileOutput = new FileOutputStream(file);
		assertTrue("File should be creted on disk", file.exists());
		
		fileOutput.close();
		file.delete();
	}

}
