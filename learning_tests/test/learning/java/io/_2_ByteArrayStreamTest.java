package test.learning.java.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * This test case demonstrates how to use ByteArrayInputStream and 
 * ByteArrayOutputStream as stub and spy objects.
 */
public class _2_ByteArrayStreamTest {

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
