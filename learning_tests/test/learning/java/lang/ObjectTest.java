package test.learning.java.lang;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ObjectTest {
	
	@Test
	void getClass_ReturnsTheSameInstanceForAllObjectsOfTheSameRuntimeType() {
		assertEquals(Object.class, new Object().getClass());
		assertEquals(Object.class, new Object().getClass());

		assertSame(Object.class, Object.class);
	}
	
	@Test
	void getClass_ReturnsTheExactRuntimeType() {
		Number number = Integer.valueOf(3);
		assertEquals(Integer.class, number.getClass());
	}
	
	@Test
	void hashCode_ReturnsTheSameValueForTheSameObject() {
		Object object = new Object();
		assertEquals(object.hashCode(), object.hashCode());
	}
	
	@Test
	void hashCode_returnsTheSameValueForEqualObjects() {
		assertEquals(Integer.valueOf(1).hashCode(), Integer.valueOf(1).hashCode());
	}

	@Test
	void toString_ImplementationOfObject() {
		Object object = new Object();
		assertEquals("java.lang.Object@" + Integer.toHexString(object.hashCode()) , object.toString());
 	}

}
