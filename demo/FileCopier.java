import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This program copies a file on disk. Prints out the file size and execution duration.
 */
public class FileCopier {

	/**
	 * This command takes two arguments.
	 * The first one is the name of the existing file to be copied.
	 * The second one is the name of the new file created.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String fromFileName = args[0];
		String toFileName = args[1];
		new FileCopier().execute(fromFileName, toFileName);
	}

	private long byteCount;

	public void execute(String fromFileName, String toFileName) throws IOException {
		System.out.println("Copying " + fromFileName + " to " + toFileName + " ...");
		long startTime = System.nanoTime();
		
		copyFiles(fromFileName, toFileName);
		
		long finishTime = System.nanoTime();
		long deltaTime = finishTime - startTime;
		logFinish(fromFileName, toFileName, deltaTime);
	}

	private void copyFiles(String fromFileName, String toFileName) throws FileNotFoundException, IOException {
		InputStream in = null;
		OutputStream out = null;
		
		try {
			in = new BufferedInputStream(new FileInputStream(fromFileName));
			out = new BufferedOutputStream(new FileOutputStream(toFileName));
			copyBytes(in, out);
		} finally {
			close(in);
			close(out);
		}
	}

	private void copyBytes(InputStream in, OutputStream out) throws IOException {
		int value = 0;
		while((value = in.read()) != -1) {
			out.write(value);
			byteCount++;
		}
	}

	private void close(Closeable closeable) throws IOException {
		if (closeable != null) {
			closeable.close();
		}
	}

	private void logFinish(String fromFileName, String toFileName, long deltaTime) {
		double deltaTimeInSeconds = deltaTime * 1E-9;	
		double speed = byteCount / deltaTimeInSeconds;
		
		System.out.println(fromFileName + " copied to " + toFileName + " in " + deltaTimeInSeconds + " seconds.");
		System.out.println("Size: " + byteCount + " bytes.");
		System.out.println("Speed: " + speed + " bytes per second.");
	}

}
