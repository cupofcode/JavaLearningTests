package test.learning.java.io;


import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

abstract class _InputStreamTest {
	
	// Let subclasses decide what to create.
	abstract InputStream createInputStream(byte[] bytes);
	
	@Test
	void read_ReturnsTheNextByteFromTheStream() throws IOException {
		InputStream input = createInputStream(new byte[] {1,2});
		
		assertEquals(1, input.read());
		assertEquals(2, input.read());
	}
	
	@Test
	void read_Returns_Minus_1_IfNoByteIsAvailabel() throws IOException {
		InputStream input = createInputStream(new byte[] {});
		assertEquals(-1, input.read());
	}
	
	@Test
	void read_ReturnsAByteAsAPositiveInt() throws IOException {
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
	}
	
	@Test
	void skip_MayOverflowWithNoProblem() throws IOException {
		InputStream input = createInputStream(new byte[] {1,2,3});
		input.skip(4);
		
		assertEquals(0, input.available());
		assertEquals(-1, input.read());
	}
	
	@Test
	void read_CopiesStreamDataInToAByteArray() throws IOException {
		InputStream input = createInputStream(new byte[] {1,2,3,4});
		
		byte[] byteArray = new byte[3];		
		input.read(byteArray);
	
		assertArrayEquals(new byte[] {1,2,3}, byteArray);
	}
	
	@Test
	void read_CopiesStreamDataInToAByteArray_WithOffsetAndLength() throws IOException {
		InputStream input = createInputStream(new byte[] {1,2,3,4});
		
		byte[] byteArray = new byte[5];
		int offsetToStart = 1;
		int lengthToRead = 3;
		
		input.read(byteArray, offsetToStart, lengthToRead);

		assertArrayEquals(new byte[] {0,1,2,3,0}, byteArray);
	}

	@Test
	void mark_And_Reset_MakesPrevioslyReadBytesAvailableAgain() throws IOException {
		InputStream input = createInputStream(new byte[] {1,2,3});
		
		assertEquals(1, input.read());
		
		int readLimit = 10;
		input.mark(readLimit); // marks at index 1
		
		assertEquals(2, input.read());
		assertEquals(3, input.read());
	
		assertEquals(0, input.available());
	
		input.reset(); // back to mark position
		
		assertEquals(2, input.available(), "Number of bytes available again");
		assertEquals(2, input.read());
		assertEquals(3, input.read());
	}
	
	@Test
	void markSupported_ReturnsTrueForThisClassToIndicateItSupportsMartAndReset() {
		InputStream input = createInputStream(new byte[] {1,2,3});
		assertTrue(input.markSupported());
	}
	
}
