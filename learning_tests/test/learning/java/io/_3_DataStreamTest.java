package test.learning.java.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.Test;

public class _3_DataStreamTest {

	@Test
	public void pushIntoDataOutputStreamAndPullFromDataInputStream() throws IOException {
		ByteArrayOutputStream outputStore = new ByteArrayOutputStream();
		DataOutputStream dataOutput = new DataOutputStream(outputStore);
		
		dataOutput.writeByte((byte) 127);
		dataOutput.writeChar('Q');
		dataOutput.writeShort((short) 111);
		dataOutput.writeInt(222);
		dataOutput.writeLong(333L);
		dataOutput.writeFloat(1.2F);
		dataOutput.writeDouble(1.3);
		dataOutput.writeUTF("Hello world.");
		
		byte[] writtenBytes = outputStore.toByteArray();
		
		ByteArrayInputStream inputSource = new ByteArrayInputStream(writtenBytes);
		DataInputStream dataInput = new DataInputStream(inputSource);
		
		assertEquals((byte) 127, dataInput.readByte());
		assertEquals('Q', dataInput.readChar());
		assertEquals((short) 111, dataInput.readShort());
		assertEquals(222, dataInput.readInt());
		assertEquals(333L, dataInput.readLong());
		assertEquals(1.2F, dataInput.readFloat(), 0.0);
		assertEquals(1.3, dataInput.readDouble(), 0.0);
		assertEquals("Hello world.", dataInput.readUTF());

		dataOutput.close();
		dataInput.close();
	}

}
