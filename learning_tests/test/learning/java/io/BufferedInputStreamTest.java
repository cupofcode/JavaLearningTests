package test.learning.java.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * Test methods of this class are almost identical to 
 * {@code FilterInputStreamTest}.
 */
public class BufferedInputStreamTest {

	@Test
	public void read_ReturnsTheNextByteInTheSourceStream() throws IOException {
		InputStream source = new ByteArrayInputStream(new byte[] {1,2});
		InputStream input = new BufferedInputStream(source);
		
		assertEquals(1, input.read());
		assertEquals(2, input.read());
		
		assertEquals("Return value after reaching the end", -1, input.read());
		assertEquals("Return value after reaching the end", -1, input.read());
	}
	
	@Test
	public void available_ReturnsTheNumberOfBytesThatCanBeReadFromTheSourceStream() throws IOException {
		InputStream source = new ByteArrayInputStream(new byte[] {1,2});
		InputStream input = new BufferedInputStream(source);
		
		assertEquals(2, input.available());
		
		input.read();
		assertEquals(1, input.available());
		
		input.read();
		assertEquals(0, input.available());
		
		input.read();
		assertEquals("Available bytes after reaching the end", 0, input.available());
	}
	
	@Test
	public void skip_JumpsOverSpecifiedNumberOfBytesInTheSourceStream() throws IOException {
		InputStream source = new ByteArrayInputStream(new byte[] {1,2,3});
		InputStream input = new BufferedInputStream(source);
		
		input.skip(2);
		assertEquals(3, input.read());
		
		input.skip(2);
		assertEquals("Return value after reaching the end", -1, input.read());
	}
	
	@Test
	public void mark_And_Reset_MakesAlreadyReadBytesAvailableAgainInTheSourceStream() throws IOException {

		// Source without mark-reset capability
		InputStream source = new ByteArrayInputStream(new byte[] {1,2,3}) {
			@Override public void mark(int readAheadLimit) {}
			@Override public synchronized void reset() {super.reset();}
		};
		
		InputStream input = new BufferedInputStream(source);
		
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
	public void read_PassesToAByteArrayFromTheSourceStream() throws IOException {
		InputStream source = new ByteArrayInputStream(new byte[] {1,2,3,4});
		InputStream input = new BufferedInputStream(source);
		
		byte[] store = new byte[3];		
		input.read(store);
	
		assertArrayEquals(new byte[] {1,2,3}, store);
	}
	
	@Test
	public void close_ClosesTheSourceStream() throws IOException {
		StringBuilder callStack = new StringBuilder();
		
		InputStream source = new InputStream() {
			public void close() {callStack.append("-close");}
			public int read() throws IOException {return 0;} // dummy
		};
		
		InputStream input = new BufferedInputStream(source);
		
		input.close();
		
		assertEquals("-close", callStack.toString());
	}
}
