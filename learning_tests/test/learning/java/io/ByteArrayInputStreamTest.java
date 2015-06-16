package test.learning.java.io;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class ByteArrayInputStreamTest {
	
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
	public void mark_And_Reset_MakesAlreadyReadBytesAvailableAgain() throws IOException {
		InputStream input = new ByteArrayInputStream(new byte[] {1,2,3});
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
	public void read_PassesToAByteArrayWithOffsetAndLength() throws IOException {
		InputStream input = new ByteArrayInputStream(new byte[] {1,2,3,4});
		
		byte[] store = new byte[5];
		int offsetInStore = 1;
		int lengthToRead = 2;
		
		input.read(store, offsetInStore, lengthToRead);

		assertArrayEquals(new byte[] {0,1,2,0,0}, store);
	}
	
	@Test
	public void constructureWithPredefinedByteSource() throws IOException {
		byte[] source  = new byte[] {1,2,3,4};
		int offsetInSource= 1;
		int lengthToRead = 2;
		
		InputStream input = new ByteArrayInputStream(source, offsetInSource, lengthToRead);
		
		assertEquals("First value after offset", 2, input.read());
		assertEquals(3, input.read());
		assertEquals("Value after exceeding the length", -1, input.read());
	}
	
}
