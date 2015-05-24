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
		BufferedOutputStream bufferedOoutput = new BufferedOutputStream(outputStore, bufferSize);
		
		bufferedOoutput.write((byte) 1);
		assertEquals(0, outputStore.toByteArray().length);
		
		bufferedOoutput.write((byte) 2);
		assertEquals(0, outputStore.toByteArray().length);
		
		bufferedOoutput.write((byte) 3);
		assertEquals("should auto-flush the content", 2, outputStore.toByteArray().length);
		
		bufferedOoutput.close();
	}
	
	@Test
	public void flushCommitsTheBufferedBytesInToTheSink() throws IOException {
		int bufferSize = 2;
		ByteArrayOutputStream outputStore = new ByteArrayOutputStream();
		BufferedOutputStream bufferedOoutput = new BufferedOutputStream(outputStore, bufferSize);
		
		bufferedOoutput.write((byte) 1);
		assertEquals(0, outputStore.toByteArray().length);
		
		bufferedOoutput.write((byte) 2);
		assertEquals(0, outputStore.toByteArray().length);
		
		bufferedOoutput.flush();
		assertEquals("should commit the buffer", 2, outputStore.toByteArray().length);
		
		bufferedOoutput.close();
	}

}
