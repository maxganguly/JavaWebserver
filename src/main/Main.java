package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.HashMap;

import server.Server;

public class Main {

	static final int PORT = 80;
	public static void main(String[] args) {
		try {
			
			ServerSocket serverConnect = new ServerSocket(PORT);
			System.out.println("Server gestarted auf Port : " + PORT + " \n");
			
			while (true) {
				Server serv = new Server(serverConnect.accept());
				
				System.out.println("Neue Verbindung (" + new Date() + ")");
				
				Thread thread = new Thread(serv);
				thread.start();
			}
			
		} catch (IOException e) {
			System.err.println("Error : " + e.getMessage());
		}
	}
}
