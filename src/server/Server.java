package server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.HashMap;


public class Server implements Runnable{ 
	


	static final boolean debug = false;
	
	private Socket connect;
	public User user;
	public static HashMap<InetAddress,User> currentUsers = new HashMap<InetAddress, User>();
	public Server(Socket c) {
		connect = c;
		try {
			c.setReceiveBufferSize(Integer.MAX_VALUE);
			c.setSendBufferSize(Integer.MAX_VALUE);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if(currentUsers.containsKey(c.getInetAddress())) {
			this.user = currentUsers.get(c.getInetAddress());
		}else {
			
		}
	}
	@Override
	public void run() {
		BufferedReader in = null; PrintWriter out = null; BufferedOutputStream dataOut = null;
		String fileRequested = null;

		try {
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			out = new PrintWriter(connect.getOutputStream());
			dataOut = new BufferedOutputStream(connect.getOutputStream());
			
			String input = in.readLine();
			System.out.println(input);
			StringTokenizer parse = new StringTokenizer(input);
			String method = parse.nextToken().toUpperCase();
			fileRequested = parse.nextToken().toLowerCase();
			Site.getcontent(fileRequested, method, in, out, dataOut);
			
		} catch (IOException e) {
			System.err.println("Error : " + e);
		} finally {
			try {
				in.close();
				out.close();
				dataOut.close();
				connect.close();
			} catch (Exception e) {
				System.err.println("Error " + e.getMessage());
			} 
			
			if (debug) {
				System.out.println("Verbindung geschlossen\n");
			}
		}
		
		
	}
	
	
	
}

