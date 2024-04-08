package test.learning.java.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class FilterInputStreamTest extends _InputStreamTest {
	
	@Override
	InputStream setUpInputStream(byte[] bytes) {
		InputStream source = new ByteArrayInputStream(bytes);
		return new FilterInputStream(source) {};
	}
	
	@Test
	void close_ClosesTheSourceStream() throws IOException {
		StringBuilder callStack = new StringBuilder();
		
		InputStream source = new InputStream() {
			public void close() {callStack.append("-close");}
			public int read() throws IOException {return 0;} // dummy
		};
		
		InputStream input = new FilterInputStream(source) {};
		
		// Exercise
		input.close();
		
		assertEquals("-close", callStack.toString());
	}

}
