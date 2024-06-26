package test.learning.java.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

/**
 * Test methods of this class are almost identical to 
 * {@code ByteArrayOutputStreamTest}.
 */
class FilterOutputStreamTest extends _OutputStreamTest {

	OutputStream setUpOutputStream() {
		store = new ByteArrayOutputStream();
		return new FilterOutputStream(store);
	}
	
	@Test
	void flush_FlushesTheStore() throws IOException {
		StringBuilder callStack = new StringBuilder();
		
		OutputStream store = new OutputStream() {
			public void flush() {callStack.append("-flush");}
			public void write(int b) throws IOException {} // dummy
		};
		
		OutputStream output = new FilterOutputStream(store);
		
		output.flush();
		
		assertEquals("-flush", callStack.toString());

		output.close();
	}
	
	@Test
	void close_FlushesAndClosesTheStore() throws IOException {
		StringBuilder callStack = new StringBuilder();
		
		OutputStream store = new OutputStream() {
			public void flush() {callStack.append("-flush");}
			public void close() {callStack.append("-close");}
			public void write(int b) throws IOException {} // dummy
		};
		
		OutputStream output = new FilterOutputStream(store);
		
		output.close();
		
		assertEquals("-flush-close", callStack.toString());
	}

}
