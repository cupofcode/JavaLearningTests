package helper.learning.java.io;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamStub extends InputStream {
	private byte[] bytes;
	private int index = 0;

	public InputStreamStub(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public int read() throws IOException {
		if (index == bytes.length) return -1;
		return bytes[index++];
	}

}
