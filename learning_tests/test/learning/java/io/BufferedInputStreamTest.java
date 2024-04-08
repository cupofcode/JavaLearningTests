package test.learning.java.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class BufferedInputStreamTest extends _InputStreamTest {

	@Override
	InputStream setUpInputStream(byte[] bytes) {
		InputStream source = new ByteArrayInputStream(bytes);
		return new BufferedInputStream(source) {};
	}
	
	@Test
	void close_ClosesTheSourceStream() throws IOException {
		StringBuilder callStack = new StringBuilder();
		
		InputStream source = new InputStream() {
			public void close() {callStack.append("-close");}
			public int read() throws IOException {return 0;} // dummy
		};
		
		InputStream input = new BufferedInputStream(source) {};
		
		// Exercise
		input.close();
		
		assertEquals("-close", callStack.toString());
	}
	
	@Test
	void thisClassMakesMarkResetCapabilityAvailableAgain() throws IOException {

		// Source without mark-reset capability
		InputStream source = new ByteArrayInputStream(new byte[] {1,2,3}) {
			@Override public void mark(int readAheadLimit) {}
			@Override public synchronized void reset() {super.reset();}
		};
		
		InputStream input = new BufferedInputStream(source);
		
		assertTrue(input.markSupported());
		
		assertEquals(1, input.read());
		input.mark(Integer.MAX_VALUE); // marks at 1
		
		assertEquals(2, input.read());
		assertEquals(3, input.read());
		
		assertEquals(0, input.available());

		input.reset(); // back to mark position
		
		assertEquals(2, input.available(), "Number of bytes available again");
		assertEquals(2, input.read());
		assertEquals(3, input.read());
	}
	
}
