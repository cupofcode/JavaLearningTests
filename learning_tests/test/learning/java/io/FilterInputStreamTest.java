package test.learning.java.io;

import static org.junit.Assert.*;
import helper.learning.java.io.OddNumberFilterInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * This test case demonstrates FilterInputStream by filtering the odd numbers.
 */
public class FilterInputStreamTest {

	@Test
	public void oddNumbersAreFiltered() throws IOException {
		InputStream inputSource = new ByteArrayInputStream(new byte[] {1,2,3,4,5,7,8});
		FilterInputStream filterInput = new OddNumberFilterInputStream(inputSource);
		
		ByteArrayOutputStream outputStore = new ByteArrayOutputStream();
		
		int i = 0;
		while ((i = filterInput.read()) != -1) {
			outputStore.write(i);
		}
		
		assertArrayEquals(new byte[] {2,4,8}, outputStore.toByteArray());
		
		filterInput.close();
		outputStore.close();
	}

}
