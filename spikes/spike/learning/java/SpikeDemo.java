package spike.learning.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SpikeDemo {

	public static void main(String[] args) throws IOException {
		File file = new File("spikes/file");
		
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		for (int i = 0; i < 1000; i++) {
			fileOutputStream.write(1);	
		}
		fileOutputStream.close();
		
		FileInputStream fileInputStream = new FileInputStream(file);
		int i;
		while ((i = fileInputStream.read()) != -1) {
			System.out.print(" " + i);
		}
		System.out.println();
		fileInputStream.close();
	}

}
