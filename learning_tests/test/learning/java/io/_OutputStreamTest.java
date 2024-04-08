package test.learning.java.io;

import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

abstract class _OutputStreamTest {
	
	// This stores the bytes written to the OutputStream to be tested.
	// Let subclasses decide what store to be.
	ByteArrayOutputStream store;

	// Let subclasses decide which OutputStream to be set up.
	abstract OutputStream setUpOutputStream();
	
	@Test
	public void write_Byte() throws IOException {
		OutputStream output = setUpOutputStream();

		output.write(1);
		output.write(2);
		
		assertArrayEquals(new byte[] {1,2},  store.toByteArray());
	}
	
	@Test
	public void write_Bytes() throws IOException {
		OutputStream output = setUpOutputStream();
		
		// Exercise
		output.write(new byte[] {1,2,3,4});
	
		assertArrayEquals(new byte[] {1,2,3,4},  store.toByteArray());
	}
	
	@Test
	public void write_BytesWithOffsetAndLength() throws IOException {
		byte[] source  = new byte[] {1,2,3,4};
		int offsetInSource= 1;
		int lengthToRead = 2;
		
		OutputStream output = setUpOutputStream();
		
		// Exercise
		output.write(source, offsetInSource, lengthToRead);

		assertArrayEquals(new byte[] {2,3},  store.toByteArray());
	}
	
}
