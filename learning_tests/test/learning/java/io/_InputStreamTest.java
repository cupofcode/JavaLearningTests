package test.learning.java.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

abstract class _InputStreamTest {
	
	// TODO make it abstract. Let subclasses decide what to create.
	InputStream createInputStream(byte[] bytes) {
		return null;
	}
	
	@Test
	void read_ReturnsTheNextByteInTheStream() throws IOException {
		InputStream input = createInputStream(new byte[] {1,2});
		
		assertEquals(1, input.read());
		assertEquals(2, input.read());

		assertEquals("Value when no byte is available", -1, input.read());
	}
	
	@Test
	void read_ReturnsBytesAsPositiveIntegers() throws IOException {
		InputStream input = createInputStream(new byte[] {-1});
		assertEquals(255, input.read());
	}
	
	@Test
	void available_ReturnsTheNumberOfBytesThatCanBeRead() throws IOException {
		InputStream input = createInputStream(new byte[] {1,2});
		
		assertEquals(2, input.available());
		
		input.read();
		assertEquals(1, input.available());
		
		input.read();
		assertEquals(0, input.available());
	}
	
	@Test
	void skip_JumpsOverSpecifiedNumberOfBytes() throws IOException {
		InputStream input = createInputStream(new byte[] {1,2,3});
		
		input.skip(2);
		assertEquals(3, input.read());
		
		input.skip(2);
		assertEquals("Value when no byte is available", -1, input.read());
	}
	
	@Test
	void mark_And_Reset_MakesAlreadyReadBytesAvailableAgain() throws IOException {
		InputStream input = createInputStream(new byte[] {1,2,3});
		
		assertTrue(input.markSupported());
		
		assertEquals(1, input.read());
		input.mark(Integer.MAX_VALUE); // marks at 1
		
		assertEquals(2, input.read());
		assertEquals(3, input.read());

		assertEquals(0, input.available());

		input.reset(); // back to mark position
		
		assertEquals("Number of bytes available again", 2, input.available());
		assertEquals(2, input.read());
		assertEquals(3, input.read());
	}
	
	@Test
	void read_CopiesToAByteArray() throws IOException {
		InputStream input = createInputStream(new byte[] {1,2,3,4});
		
		byte[] store = new byte[3];		
		input.read(store);
	
		assertArrayEquals(new byte[] {1,2,3}, store);
	}
	
	@Test
	void read_CopiesToAByteArrayWithOffsetAndLength() throws IOException {
		InputStream input = createInputStream(new byte[] {1,2,3,4});
		
		byte[] store = new byte[5];
		int offsetInStore = 1;
		int lengthToRead = 3;
		
		input.read(store, offsetInStore, lengthToRead);

		assertArrayEquals(new byte[] {0,1,2,3,0}, store);
	}
	
}
