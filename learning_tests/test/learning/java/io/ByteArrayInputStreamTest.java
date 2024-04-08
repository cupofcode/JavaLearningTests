package test.learning.java.io;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class ByteArrayInputStreamTest extends _InputStreamTest {
	
	@Override
	InputStream setUpInputStream(byte[] bytes) {
		return new ByteArrayInputStream(bytes);
	}
	
	@Test
	void constructorWithOffsetAndLength() throws IOException {
		byte[] bytes  = new byte[] {1,2,3,4};
		int offset= 1;
		int length = 2;
		
		InputStream input = new ByteArrayInputStream(bytes, offset, length);
		
		assertEquals(2, input.read(), "First value at offset index");
		assertEquals(3, input.read());
		assertEquals(-1, input.read(), "Value when no byte is available");
	}
	
}
