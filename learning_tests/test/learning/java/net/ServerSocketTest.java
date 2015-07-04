package test.learning.java.net;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerSocketTest {
	private static final int LOCAL_PORT = 1001;
	
	private ServerSocket serverSocket;
	
	@Before
	public void setUp() throws IOException {
		serverSocket = new ServerSocket(LOCAL_PORT);		
	}
	
	@After
	public void tearDown() throws IOException {
		serverSocket.close();
	}

	@Test
	public void isClosed() throws IOException {
		assertFalse("Socket should be open by default", serverSocket.isClosed());
		
		serverSocket.close();
		assertTrue(serverSocket.isClosed());
	}
	
	@Test
	public void isBound() {
		assertTrue(serverSocket.isBound());
	}
	
	@Test
	public void getLocalPort() {
		assertEquals(LOCAL_PORT, serverSocket.getLocalPort());
	}
	
	@Test
	public void soTimeout() throws IOException {
		assertEquals("Zero indicating infinity by default", 0, serverSocket.getSoTimeout());

		serverSocket.setSoTimeout(1);
		assertEquals(1, serverSocket.getSoTimeout());
	}
	
	@Test (expected = SocketTimeoutException.class)
	public void socketTimeout() throws IOException {
		serverSocket.setSoTimeout(1);
		serverSocket.accept();
	}
	
	@Test
	public void receiveBufferSize() throws SocketException {
		assertEquals("Buffer size by default", 65536, serverSocket.getReceiveBufferSize());
		
		serverSocket.setReceiveBufferSize(1000000);
		assertEquals(1000000, serverSocket.getReceiveBufferSize());
	}
	
	@Test
	public void socketCommunicationAfterAccept() throws IOException {		
		Socket clientSideSocket = new Socket("localhost", LOCAL_PORT);
		Socket serverSideSocket = serverSocket.accept();

		OutputStream output = clientSideSocket.getOutputStream();
		InputStream input = serverSideSocket.getInputStream();
		
		output.write(3);
		output.write(5);
		
		assertEquals(3, input.read());
		assertEquals(5, input.read());
		
		clientSideSocket.close();
		serverSideSocket.close();
	}
	
}
