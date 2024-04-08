package test.learning.java.io;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class BufferedOutputStreamTest extends _OutputStreamTest {
	
	@Override
	OutputStream createOutputStream() {
		store = new ByteArrayOutputStream();
		return new FilterOutputStream(store);
	}

	@Test
	public void flush_CommitsTheBufferedBytesInToTheStore() throws IOException {
		int bufferSize = 2;
		ByteArrayOutputStream store = new ByteArrayOutputStream();
		BufferedOutputStream output = new BufferedOutputStream(store, bufferSize);
		
		output.write(1);
		assertEquals(0, store.toByteArray().length);
		
		output.write(2);
		assertEquals(0, store.toByteArray().length);
		
		output.flush();
		assertEquals("Output store length after explicit flush", 2, store.toByteArray().length);
		
		output.close();
	}

	@Test
	public void bufferedStreamAutoflushesItsContentWhenTheBufferIsFull() throws IOException {
		int bufferSize = 2;
		ByteArrayOutputStream store = new ByteArrayOutputStream();
		BufferedOutputStream output = new BufferedOutputStream(store, bufferSize);
		
		output.write(1);
		assertEquals(0, store.toByteArray().length);
		
		output.write(2);
		assertEquals(0, store.toByteArray().length);
		
		output.write(3);
		assertEquals("Output store length after auto-flush", 2, store.toByteArray().length);
		
		output.close();
	}

}
