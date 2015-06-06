package test.learning.java.io;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * This test case demonstrates how to use ByteArrayInputStream and 
 * ByteArrayOutputStream as stub and spy objects.
 */
public class ByteArrayStreamTest {
	
	@Test
	public void read_ReturnsTheNextByteInTheStream() throws IOException {
		InputStream input = new ByteArrayInputStream(new byte[] {1,2});
		
		assertEquals(1, input.read());
		assertEquals(2, input.read());
		
		assertEquals("Return value after reaching the end", -1, input.read());
		assertEquals("Return value after reaching the end", -1, input.read());
	}
	
	@Test
	public void available_ReturnsTheNumberOfBytesThatCanBeRead() throws IOException {
		InputStream input = new ByteArrayInputStream(new byte[] {1,2});
		assertEquals(2, input.available());
		
		input.read();
		assertEquals(1, input.available());
		
		input.read();
		assertEquals(0, input.available());
		
		input.read();
		assertEquals("Available bytes after reaching the end", 0, input.available());
	}
	
	@Test
	public void skip_JumpsOverSpecifiedNumberOfBytes() throws IOException {
		InputStream input = new ByteArrayInputStream(new byte[] {1,2,3});
		
		input.skip(2);
		assertEquals(3, input.read());
		
		input.skip(2);
		assertEquals("Return value after reaching the end", -1, input.read());
	}

	@Test
	public void bytesPulledFromInputArrayStreamArePushedIntoArrayOuputStream() throws IOException {
		InputStream input = new ByteArrayInputStream(new byte[] {1,2}); // stub
		ByteArrayOutputStream output = new ByteArrayOutputStream(); // spy
		
		output.write(input.read());
		output.write(input.read());
		output.write(input.read());
		output.write(input.read());
				
		byte[] outputBytes = output.toByteArray();
		
		assertEquals(1, outputBytes[0]);
		assertEquals(2, outputBytes[1]);
		assertEquals(-1, outputBytes[2]);
		assertEquals(-1, outputBytes[3]);
		
		input.close();
		output.close();
	}

}
