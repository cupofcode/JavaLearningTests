package test.learning.java.io;

import static org.junit.Assert.assertEquals;
import helper.learning.java.io.InputStreamStub;

import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This test case drives an InputStream extension that can be used as a stub.
 * ByteArrayInputStream or ByteArrayOutputStream could be better 
 * to be used as a stub, but this example gives clues about 
 * writing test doubles from scratch.
 */
public class InputStreamStubTest {
	private InputStream stub;
	
	@Before
	public void setUp() {
		stub = new InputStreamStub(new byte[] {1, 2});		
	}
	
	@After
	public void tearDown() throws IOException {
		stub.close();
	}
	
	@Test
	public void eachReadReturnsTheNextByteInTheStream() throws IOException {
		assertEquals(1, stub.read());
		assertEquals(2, stub.read());
	}
	
	@Test
	public void readingAfterTheLastByteReturnsMinusOne() throws IOException {
		stub.read();
		stub.read();
		assertEquals(-1, stub.read());
		assertEquals(-1, stub.read());
	}

}
