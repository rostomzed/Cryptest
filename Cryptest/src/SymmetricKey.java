/**
 * SymmetricKey.java
 */

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @author Rostom Zouaghi
 * 4 Aug 2014
 * 13:48:43
 */
public class SymmetricKey {
	
	private byte[] key;
	private String type;
	private int size;
	
	/**
	 * Constructor for the Key class - manually set the key
	 * @param String k key value
	 * @param String t type of key: "ascii" or "hex"
	 */
	public SymmetricKey(String k, String t, int s) {
		if(t.equals("ascii")){
			key = k.getBytes();
			type = t;
			size = s;
		}
		else{
			if (s==112){
				k = k+"0000000000000000";
				key = hexStringToByteArray(removeSpaces(k));					
				for(int i=0;i<8;i++) key[i+16] = key[i];
			}
			else key = hexStringToByteArray(removeSpaces(k));
			type = t;
			size = s;
		}
	}
	
	/**
	 * Constructor for the Key class - generate key
	 * @param a Algorithm used
	 * @param s size of the key
	 * @throws NoSuchAlgorithmException 
	 */
	public SymmetricKey(String algorithm, int s) throws NoSuchAlgorithmException {
		size = s;
		KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
		keygen.init(s);			
		SecretKey k = keygen.generateKey();
		key = k.getEncoded();
	}
	
	/**
	 * Returns the key
	 * @return byte[] k key
	 */
	public byte[] getKey(){
		return key;
	}
	
	/**
	 * Returns the type of the message
	 * @return String type
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Set the key value
	 * @param byte[] k key
	 */
	public void setKey(byte[] k){
		key = k;
	}
	
	/**
	 * Set the key type
	 * @param String t type
	 */
	public void setType(String t){
		type = t;
	}
	
	/**
	 * Return a String that can be displayed in HEX or ASCII
	 * @return output String
	 */
	public String displayKey(){
		if(size == 112){
			if(type == "ascii")	return new String(key);
			else return byteArrayToHexString(key).substring(0,byteArrayToHexString(key).length()-23);
		}
		else{
			if(type == "ascii")	return new String(key);
			else return byteArrayToHexString(key);
		}
	}
	
	/**
	 * Convert hex string to a byte[] array
	 * @param s String
	 * @return data Byte[] array
	 */
	public byte[] hexStringToByteArray(String s){
	    if (s.length() % 2 != 0){
	    	s = s+"0";
	    }
	    byte[] data = new byte[s.length()/2];
	    for (int i = 0; i < s.length(); i += 2) {
	        data[i/2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	/**
	 * Remove spaces from string
	 * @param s String
	 * @return String without spaces
	 */
	public String removeSpaces(String s){
	    return s.replaceAll("\\s+", "");
	}
	
	/**
	 * Convert byte[] to hex String with spaces between every byte block
	 * @param data byte[] to be converted
	 * @return String hex String with spaces
	 */
	public String byteArrayToHexString(byte[] data){
	    StringBuilder sb = new StringBuilder(13 * data.length);
	    for(int i = 0; i < data.length; i++){
	        if(i != 0){
	            sb.append(" ");
	        }
	        sb.append(String.format("%02x", data[i]));
	    }
	    return sb.toString().toUpperCase();
	}
	
	/**
	 * Convert the key from HEX to ASCII
	 */
	public void convertToASCII(){
		if(type != "ascii"){
			String s = removeSpaces(byteArrayToHexString(key));
			StringBuilder output = new StringBuilder("");
			for(int i=0; i<s.length(); i+=2){				
				String str = s.substring(i, i+2);
				output.append((char)Integer.parseInt(str, 16));
			}
			s = output.toString();
			key = s.getBytes();
			type = "ascii";
		}
	}	
	
	/**
	 * Convert the key from ASCII to HEX
	 */
	public void convertToHex(){
		if(type != "hex"){
			String s = new String(key);
			char[] chars = s.toCharArray();
			StringBuffer output = new StringBuffer();
			for(int i=0; i<chars.length; i++){
				output.append(Integer.toHexString((int) chars[i]));
			}
			s = output.toString();
			key = hexStringToByteArray(s);
			type = "hex";
		}
	}

}
