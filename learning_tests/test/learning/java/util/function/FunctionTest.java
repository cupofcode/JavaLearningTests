package test.learning.java.util.function;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Test;

public class FunctionTest {

	@Test
	public void createWithApplyImplementation() {
		Function<Integer, String> integerToString = new Function<Integer, String>() {

			@Override
			public String apply(Integer integer) {
				return String.valueOf(integer);
			}
		};
		
		assertEquals("123", integerToString.apply(123));
	}
	
	@Test
	public void shortHandNotation() {
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
	public void assigningToAClassMethod() {
		Function<Integer, String> integerToString = C::integerToString;
		assertEquals("123", integerToString.apply(123));
	}
	
	@Test
	public void functionComposition() {
		Function<Integer, Integer> square = x -> x * x;
		Function<Integer, Integer> half = x -> x / 2;
		
		Function<Integer, Integer> squareHalf = x -> half.apply(square.apply(x));
		Function<Integer, Integer> halfSquare = x -> square.apply(half.apply(x));
		
		assertEquals(Integer.valueOf(8), squareHalf.apply(4));
		assertEquals(Integer.valueOf(4), halfSquare.apply(4));
	}
	
	@Test
	public void compose_FunctionComposition() {
		Function<Integer, Integer> square = x -> x * x;
		Function<Integer, Integer> half = x -> x / 2;
		
		Function<Integer, Integer> squareHalf = half.compose(square);
		Function<Integer, Integer> halfSquare = square.compose(half);
		
		assertEquals(Integer.valueOf(8), squareHalf.apply(4));
		assertEquals(Integer.valueOf(4), halfSquare.apply(4));
	}
	
	@Test
	public void before_FunctionComposition() {
		Function<Integer, Integer> square = x -> x * x;
		Function<Integer, Integer> half = x -> x / 2;
		
		Function<Integer, Integer> squareHalf = square.andThen(half);
		Function<Integer, Integer> halfSquare = half.andThen(square);
		
		assertEquals(Integer.valueOf(8), squareHalf.apply(4));
		assertEquals(Integer.valueOf(4), halfSquare.apply(4));
	}

}
