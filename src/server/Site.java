package server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

public class Site {

	static final File WEB_ROOT = new File(".");
	static final String DEFAULT_FILE = "index.html";
	static final String FILE_NOT_FOUND = "404.html";
	static final String STANDARD_SUFFIX = ".html";
	public static String getcontent(String path, String method , BufferedReader in , PrintWriter out ,BufferedOutputStream dataOut) {
		if (path.equals("/")) {
			path += DEFAULT_FILE;
		}
		
		File file = new File(WEB_ROOT, path);
		int fileLength = (int) file.length();
		if(fileLength == 0) {
			file = new File(WEB_ROOT, path+STANDARD_SUFFIX);
			fileLength = (int) file.length();
			byte[] fileData;
			try {
				if(fileLength == 0) {
					fileNotFound(out, dataOut, path);
					return "";
				}
				fileData = readFileData(file, fileLength);
			
				header(out);
			
				dataOut.write(fileData, 0, fileLength);
				dataOut.flush();
			} catch (IOException e) {
				
				fileNotFound(out, dataOut, path);
				e.printStackTrace();
			}
			return "";
		}else if (method.equals("GET")) {
			byte[] fileData;
			try {
				fileData = readFileData(file, fileLength);
			
				header(out);
			
				dataOut.write(fileData, 0, fileLength);
				dataOut.flush();
			} catch (IOException e) {
				
				fileNotFound(out, dataOut, path);
				e.printStackTrace();
			}
			return "";
			
		}else if(method.equals("POST")) {
			try{
			System.out.println("POST");
			String temp = "";
	        while (in.ready()) {
	            temp += (char) in.read();
	            //System.out.println(temp);
	        }
	        String param = temp.substring(temp.lastIndexOf("\n"));
	        Post(out, dataOut, path, param);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return "Error 404 <br > Nothing found";
	}
	public static void send404(PrintWriter out ,OutputStream dataOut) {
		System.out.println("404 File not found");
		File file = new File(WEB_ROOT, FILE_NOT_FOUND);
		int fileLength = (int) file.length();
		
		byte[] fileData;
		try {
			fileData = readFileData(file, fileLength);
			header(out);
			dataOut.write(fileData, 0, fileLength);
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static byte[] readFileData(File file, int fileLength) throws IOException {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		
		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		}catch(FileNotFoundException e) {
			
		}finally {
			if (fileIn != null) 
				fileIn.close();
		}
		
		return fileData;
	}
	 
	
	private static void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested){
		send404(out, dataOut);/*
		File file = new File(WEB_ROOT, FILE_NOT_FOUND);
		System.out.println(WEB_ROOT.getAbsolutePath()+fileRequested);
		int fileLength = (int) file.length();
		byte[] fileData = readFileData(file, fileLength);
		
		header(out);
		
		dataOut.write(fileData, 0, fileLength);
		dataOut.flush();*/
		
	}
	private static void header(PrintWriter out) {
		out.println("HTTP/1.1 200 OK");
		out.println("Server: Java HTTP Server: 1.0");
		out.println("Date: " + new Date());
		out.println("Content-type: text/html");
		out.println();
		out.flush();

	}
	private static void Post(PrintWriter out, OutputStream dataOut, String fileRequested, String parameter) {
		if(fileRequested.equals("/register.html")){
			register(out, dataOut, parameter);
		}else if(fileRequested.equals("/login.html")) {
			login(out, dataOut, parameter);
		}
		System.out.println(fileRequested);
		System.out.println(parameter);
		
	}
	private static void register(PrintWriter out, OutputStream dataOut, String parameter) {
		header(out);
		String username = parameter.substring(10, parameter.indexOf('&'));
		String password = parameter.substring(parameter.indexOf('&')+10, parameter.length());
		System.out.println("Username :"+username);
		System.out.println("Password :"+password);
		if(User.userexists(username)) {
			try {
				System.out.println("Username alredy taken");
				dataOut.write("Username alredy taken".getBytes());
				dataOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}else if(User.newUser(username, password)) {
			try {
				System.out.println("Registration succesful");
				dataOut.write("Registration succesful".getBytes());
				dataOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}else {
			try {
				System.out.println("Something went wrong");
				dataOut.write("Something went wrong".getBytes());
				dataOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}
	private static void login(PrintWriter out, OutputStream dataOut, String parameter) {
		header(out);
		String username = parameter.substring(10, parameter.indexOf('&'));
		String password = parameter.substring(parameter.indexOf('&')+10, parameter.length());
		System.out.println("Username :"+username);
		System.out.println("Password :"+password);
		if(!User.userexists(username)) {
			try {
				System.out.println("No such Username");
				dataOut.write("No such username".getBytes());
				dataOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}else if(User.login(username, password)) {
			try {
				System.out.println("Login succesful");
				dataOut.write("Login successful".getBytes());
				dataOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}else {
			try {
				System.out.println("Username or password wrong");
				dataOut.write("Username or password wrong".getBytes());
				dataOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}

	
}
