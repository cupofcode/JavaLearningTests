package test.learning.java.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

/**
 * Test methods of this class are almost identical to 
 * {@code ByteArrayOutputStreamTest}.
 */
public class FilterOutputStreamTest {

	@Test
	public void write() throws IOException {
		ByteArrayOutputStream store = new ByteArrayOutputStream();
		OutputStream output = new FilterOutputStream(store);
		
		output.write(1);
		output.write(2);
		
		assertArrayEquals(new byte[] {1,2},  store.toByteArray());
	}
	
	@Test
	public void write_FromPredefinedByteSource() throws IOException {
		byte[] source  = new byte[] {1,2,3,4};
		
		ByteArrayOutputStream store = new ByteArrayOutputStream();
		OutputStream output = new FilterOutputStream(store);
		
		output.write(source);
	
		assertArrayEquals(new byte[] {1,2,3,4},  store.toByteArray());
	}

	@Test
	public void write_FromPredefinedByteSourceWithOffsetAndLength() throws IOException {
		byte[] source  = new byte[] {1,2,3,4};
		int offsetInSource= 1;
		int lengthToRead = 2;
		
		ByteArrayOutputStream store = new ByteArrayOutputStream();
		OutputStream output = new FilterOutputStream(store);
		
		output.write(source, offsetInSource, lengthToRead);

		assertArrayEquals(new byte[] {2,3},  store.toByteArray());
	}
	
	@Test
	public void close_FlushesAndClosesTheStore() throws IOException {
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
