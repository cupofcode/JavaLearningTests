package test.learning.java.util.function;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Test;

public class FunctionTest {

	@Test
	public void function_RegularImplementation() {
		Function<Integer, String> integerToString = new Function<Integer, String>() {

			@Override
			public String apply(Integer integer) {
				return String.valueOf(integer);
			}
		};
		
		assertEquals("123", integerToString.apply(123));
	}
	
	@Test
	public void function_ShortHandNotation() {
		Function<Integer, String> integerToString = integer -> String.valueOf(integer);
		assertEquals("123", integerToString.apply(123));
	}
	
	// Used in the next test.
	static class C {
		static String integerToString(Integer integer) {
			return String.valueOf(integer); 
		}
	}
	
	@Test
	public void function_AssignedToAClassMethod() {
		Function<Integer, String> integerToString = C::integerToString;
		assertEquals("123", integerToString.apply(123));
	}
	
	@Test
	public void functionComposition() {
		Function<Integer, Integer> square = x -> x * x;
		Function<Integer, Integer> half = x -> x / 2;
		
		assertEquals(new Integer(8), half.apply(square.apply(4)));
		assertEquals(new Integer(4), square.apply(half.apply(4)));
	}
	
	@Test
	public void compose_FunctionChain() {
		Function<Integer, Integer> square = x -> x * x;
		Function<Integer, Integer> half = x -> x / 2;
		
		Function<Integer, Integer> squareHalf = half.compose(square);
		Function<Integer, Integer> halfSquare = square.compose(half);
		
		assertEquals(new Integer(8), squareHalf.apply(4));
		assertEquals(new Integer(4), halfSquare.apply(4));
	}

}
