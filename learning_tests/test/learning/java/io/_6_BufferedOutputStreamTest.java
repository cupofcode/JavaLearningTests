package test.learning.java.io;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public class _6_BufferedOutputStreamTest {

	@Test
	public void bufferedStreamAutoflushesItsContentWhenTheBufferIsFull() throws IOException {
		int bufferSize = 2;
		ByteArrayOutputStream outputStore = new ByteArrayOutputStream();
		BufferedOutputStream outputStream = new BufferedOutputStream(outputStore, bufferSize);
		
		outputStream.write((byte) 1);
		assertEquals(0, outputStore.toByteArray().length);
		
		outputStream.write((byte) 2);
		assertEquals(0, outputStore.toByteArray().length);
		
		outputStream.write((byte) 3);
		assertEquals("exceeding the buffer length should write the buffered bytes to the underlying output stream", 2, outputStore.toByteArray().length);
		
		outputStream.close();
	}
	
	@Test
	public void flushCommitsTheBufferedBytesInToTheSink() throws IOException {
		int bufferSize = 2;
		ByteArrayOutputStream outputStore = new ByteArrayOutputStream();
		BufferedOutputStream outputStream = new BufferedOutputStream(outputStore, bufferSize);
		
		outputStream.write((byte) 1);
		assertEquals(0, outputStore.toByteArray().length);
		
		outputStream.write((byte) 2);
		assertEquals(0, outputStore.toByteArray().length);
		
		outputStream.flush();
		assertEquals("flush should write the buffered bytes to the underlying output stream", 2, outputStore.toByteArray().length);
		
		outputStream.close();
	}

}
