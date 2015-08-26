package test.learning.java.io;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class ObjectStreamsTest {
	private ByteArrayOutputStream outputStore;
	private ObjectOutputStream objectOutput;
	
	private ObjectInputStream objectInput;

	@Before
	public void setUp() throws IOException {
		outputStore = new ByteArrayOutputStream();
		objectOutput = new ObjectOutputStream(outputStore);
	}
	
	@After
	public void tearDown() throws IOException {
		objectOutput.close();
		objectInput.close();
	}

	@Test
	public void writeAndReadObjectWithVariousFields() throws Exception {
		DataClass object = new DataClass();
		objectOutput.writeObject(object);
		
		pushWrittenBytesToObjectInput();
		
		DataClass objectRead = (DataClass) objectInput.readObject();
		
		assertEquals(true, objectRead.boolean_);
		assertEquals((byte) 12, objectRead.byte_);
		assertEquals((short) 13, objectRead.short_);
		assertEquals('a', objectRead.char_);
		assertEquals(14, objectRead.int_);
		assertEquals(1.2f, objectRead.float_, 0.0);
		assertEquals(1.3, objectRead.double_, 0.0);
		assertArrayEquals(new int[] {1,2}, objectRead.intArray);
		assertEquals("DataClass string", objectRead.string);
	}
	
	@Test
	public void superClassesAndReferencedClassesAreSupported() throws Exception {
		MyClass object = new MyClass();
		objectOutput.writeObject(object);
		
		pushWrittenBytesToObjectInput();
		
		MyClass objectRead = (MyClass) objectInput.readObject();
		assertEquals("MyClass", objectRead.string);
		assertEquals("SuperClass", objectRead.stringOfSuper);
		assertEquals("ReferencedClass", objectRead.referencedClass.stringOfReferenced);
	}
	
	@Test
	public void sameObjectsInObjectStreamAreHeldByTheSameReference() throws Exception {		
		MyClass object = new MyClass();
		objectOutput.writeObject(object);
		objectOutput.writeObject(object);
		
		pushWrittenBytesToObjectInput();
		
		MyClass objectRead1 = (MyClass) objectInput.readObject();
		MyClass objectRead2 = (MyClass) objectInput.readObject();
		assertSame(objectRead1, objectRead2);
	}

	private void pushWrittenBytesToObjectInput() throws IOException {
		byte[] writtenBytes = outputStore.toByteArray();
		ByteArrayInputStream inputSource = new ByteArrayInputStream(writtenBytes);
		objectInput = new ObjectInputStream(inputSource);
	}
	
	private static class DataClass implements Serializable {
		public boolean boolean_ = true;
		public byte byte_ = (byte) 12;
		public short short_ = (short) 13;
		public char char_ = 'a';
		public int int_ = 14;
		public float float_ = 1.2f;
		public double double_ = 1.3;
		public int[] intArray = {1,2};
		public String string = "DataClass string";		
	}
	
	private static class MyClass extends SuperClass implements Serializable {
		public String string = "MyClass";
		public ReferencedClass referencedClass = new ReferencedClass();
	}

	private static class SuperClass implements Serializable {
		public String stringOfSuper = "SuperClass";
	}
	
	private static class ReferencedClass implements Serializable {
		public String stringOfReferenced = "ReferencedClass";
	}

}
