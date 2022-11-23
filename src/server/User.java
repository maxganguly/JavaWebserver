package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class User {

	static final String FILEDB = "./src/data/db";

	public static int hashint(String text) {
		return text.hashCode();
	}
	public static byte[] hash(String text) throws NoSuchAlgorithmException {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(salt);
		byte[] hashedPassword = md.digest(text.getBytes(StandardCharsets.UTF_8));
		System.out.println(text+" to "+new String(hashedPassword));
		return hashedPassword;
	    
	}
	public static boolean userexists(String username) {
		File f = new File(FILEDB);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f)); 
			String st; 
			while ((st = br.readLine()) != null) {
			  if(st.substring(0, username.length()).equalsIgnoreCase(username)) {
				  br.close();
				  return true;
			  }
			} 
			br.close();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return true;
		
		 
	}
	 public static boolean newUser(String username,String password) {
		 if(!userexists(username)) {
			 try {
				BufferedWriter output = new BufferedWriter(new FileWriter(FILEDB, true));
				output.write(username +" : "+ hashint(password));
				output.close();
				return true;
			} catch (IOException e) {
				return false;
			}
		 }
		 return false;
	 }
	 
	 public static boolean login(String username,String password) {
		File f = new File(FILEDB);
		password = ""+hashint(password);
			try {
				BufferedReader br = new BufferedReader(new FileReader(f)); 
				String st; 
				while ((st = br.readLine()) != null) {
					System.out.println(st.substring(0, username.length())+" against "+username);
					System.out.println(st.substring(username.length()+3,st.length())+" against "+password);
				  if(st.substring(0, username.length()).equalsIgnoreCase(username)) {
					  if(st.substring(username.length()+3,st.length()).equals(password)) {
						  br.close();
						  return true;
					  }
				  }
				} 
				br.close();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			return true;
	 }
	
}
	
