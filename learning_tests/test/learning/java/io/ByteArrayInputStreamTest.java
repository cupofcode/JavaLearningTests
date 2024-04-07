package test.learning.java.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class ByteArrayInputStreamTest extends _InputStreamTest {
	
	@Override
	InputStream createInputStream(byte[] bytes) {
		return new ByteArrayInputStream(bytes);
	}
	
	@Test
	void constructorWithOffsetAndLength() throws IOException {
		byte[] bytes  = new byte[] {1,2,3,4};
		int offset= 1;
		int length = 2;
		
		InputStream input = new ByteArrayInputStream(bytes, offset, length);
		
		assertEquals("First value at offset index", 2, input.read());
		assertEquals(3, input.read());
		assertEquals("Value when no byte is available", -1, input.read());
	}
	
}
