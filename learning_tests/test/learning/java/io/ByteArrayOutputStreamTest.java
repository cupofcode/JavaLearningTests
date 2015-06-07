package test.learning.java.io;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public class ByteArrayOutputStreamTest {

	@Test
	public void write() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write(1);
		output.write(2);
		
		assertArrayEquals(new byte[] {1,2},  output.toByteArray());
	}
	
	@Test
	public void size() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		assertEquals(0, output.size());
		
		output.write(1);
		assertEquals(1, output.size());
		
		output.write(2);
		assertEquals(2, output.size());
	}
	
	@Test
	public void write_WithOffsetAndLengthFromPredefinedByteSource() {
		byte[] source  = new byte[] {1,2,3,4};
		int offsetInSource= 1;
		int lengthToRead = 2;
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write(source, offsetInSource, lengthToRead);
		
		assertArrayEquals(new byte[] {2,3},  output.toByteArray());
	}
	
	@Test
	public void writeTo_WritesContentToAnotherOutputStream() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write(1);
		output.write(2);
		
		ByteArrayOutputStream anotherOutput = new ByteArrayOutputStream();
		output.writeTo(anotherOutput);
		
		assertArrayEquals(new byte[] {1,2},  anotherOutput.toByteArray());
	}
	
	@Test
	public void reset_DiscardsAllPreviouslyWritttenBytes() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write(1);
		output.write(2);
		
		output.reset();
		
		output.write(3);
		output.write(4);
		assertArrayEquals(new byte[] {3,4},  output.toByteArray());
	}
	
	@Test
	public void toString_() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write('A');
		output.write('B');
		
		assertEquals("AB", output.toString());
	}

}
