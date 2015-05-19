package helper.learning.java.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OddNumberFilterInputStream extends FilterInputStream {

	public OddNumberFilterInputStream(InputStream inputStream) {
		super (inputStream);
	}

	@Override
	public int read() throws IOException {
		int i = super.read();
		while (i != -1 && isOdd(i)) {
			i = super.read();
		}
		return i;
	}

	private boolean isOdd(int i) {
		return (i % 2) == 1;
	}

}
