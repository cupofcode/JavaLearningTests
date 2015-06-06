package test.learning.java.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * Test methods of this class are almost identical to 
 * {@code ByteArrayInputStreamTest}.
 */
public class FilterInputStreamTest {
	
	@Test
	public void read_ReturnsTheNextByteInTheSource() throws IOException {
		InputStream source = new ByteArrayInputStream(new byte[] {1,2});
		InputStream input = new FilterInputStream(source) {};
		
		assertEquals(1, input.read());
		assertEquals(2, input.read());
		
		assertEquals("Return value after reaching the end", -1, input.read());
		assertEquals("Return value after reaching the end", -1, input.read());
	}
	
	@Test
	public void available_ReturnsTheNumberOfBytesThatCanBeReadFromTheSource() throws IOException {
		InputStream source = new ByteArrayInputStream(new byte[] {1,2});
		InputStream input = new FilterInputStream(source) {};
		
		assertEquals(2, input.available());
		
		input.read();
		assertEquals(1, input.available());
		
		input.read();
		assertEquals(0, input.available());
		
		input.read();
		assertEquals("Available bytes after reaching the end", 0, input.available());
	}
	
	@Test
	public void skip_JumpsOverSpecifiedNumberOfBytesInTheSource() throws IOException {
		InputStream source = new ByteArrayInputStream(new byte[] {1,2,3});
		InputStream input = new FilterInputStream(source) {};
		
		input.skip(2);
		assertEquals(3, input.read());
		
		input.skip(2);
		assertEquals("Return value after reaching the end", -1, input.read());
	}
	
	@Test
	public void mark_And_Reset_MakesAlreadyReadBytesAvailableAgainInTheSource() throws IOException {
		InputStream source = new ByteArrayInputStream(new byte[] {1,2,3});
		InputStream input = new FilterInputStream(source) {};
		
		assertTrue(input.markSupported());
		
		input.read();
		
		input.mark(Integer.MAX_VALUE); // marks at 1
		
		input.read();
		input.read();
		
		assertEquals("No more bytes to read", 0, input.available());

		input.reset(); // back to mark position
		
		assertEquals("Number of bytes available again", 2, input.available());
		assertEquals(2, input.read());
		assertEquals(3, input.read());
	}
	
	@Test
	public void read_PassesToAByteArrayFromTheSource() throws IOException {
		InputStream source = new ByteArrayInputStream(new byte[] {1,2,3,4});
		InputStream input = new FilterInputStream(source) {};
		
		byte[] store = new byte[3];		
		input.read(store);
	
		assertArrayEquals(new byte[] {1,2,3}, store);
	}

	@Test
	public void read_PassesToAByteArrayWithOffsetAndLengthFromTheSource() throws IOException {
		InputStream source = new ByteArrayInputStream(new byte[] {1,2,3,4});
		InputStream input = new FilterInputStream(source) {};
		
		byte[] store = new byte[5];
		int offsetInStore = 1;
		int lengthToRead = 2;
		
		input.read(store, offsetInStore, lengthToRead);

		assertArrayEquals(new byte[] {0,1,2,0,0}, store);
	}

}
