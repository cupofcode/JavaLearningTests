package test.learning.java.io;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

class BufferedOutputStreamTest extends _OutputStreamTest {
	
	@Override
	OutputStream setUpOutputStream() {
		store = new ByteArrayOutputStream();
		return new FilterOutputStream(store);
	}

	@Test
	void flush_CommitsTheBufferedBytesInToTheStore() throws IOException {
		int bufferSize = 2;
		ByteArrayOutputStream store = new ByteArrayOutputStream();
		BufferedOutputStream output = new BufferedOutputStream(store, bufferSize);
		
		output.write(1);
		assertEquals(0, store.toByteArray().length);
		
		output.write(2);
		assertEquals(0, store.toByteArray().length);
		
		output.flush();
		assertEquals(2, store.toByteArray().length, "Output store length after explicit flush");
		
		output.close();
	}

	@Test
	void bufferedStreamAutoflushesItsContentWhenTheBufferIsFull() throws IOException {
		int bufferSize = 2;
		ByteArrayOutputStream store = new ByteArrayOutputStream();
		BufferedOutputStream output = new BufferedOutputStream(store, bufferSize);
		
		output.write(1);
		assertEquals(0, store.toByteArray().length);
		
		output.write(2);
		assertEquals(0, store.toByteArray().length);
		
		output.write(3);
		assertEquals(2, store.toByteArray().length, "Output store length after auto-flush");
		
		output.close();
	}

}
