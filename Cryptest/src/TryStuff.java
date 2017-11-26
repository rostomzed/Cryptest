/**
 * TryStuff.java
 */

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * @author Rostom Zouaghi
 * 21 Jul 2014
 * 16:00:59
 */

/**
 * Class used to try methods or functions in console
 */
public class TryStuff {

	public static void main(String[] args) {
	    try {
	    	/*
	    	SymmetricKey k = new SymmetricKey("DESede", 112);
	    	System.out.println("Key generated:\t"+k.displayKey());
	    	
	    	SymmetricKey k2 = new SymmetricKey("1122334455667788AABBCCDDEEFFAABB", "hex", 112);
	    	System.out.println("Key created:\t"+k2.displayKey());
	    	
	    	String str = "012345678abcdef0";
	    	byte[] encryptionKey = hexStringToByteArray(removeSpaces(str));
	    	String mode = "CBC";
	        String alg = "DES";
	        String initial = "0000000000000000";
	    	byte[] IV = hexStringToByteArray(removeSpaces(initial));
	    	int size = 32;*/
	    	/*
	    	Message plaintext = new Message("crypto is cool!!!	0", "ascii");
	    	Message ciphertext = new Message("110","hex");
	    	System.out.println("cipher hex:\t"+ciphertext.displayMessage());
	    	byte[] b = DatatypeConverter.parseHexBinary("0101010101FF");
	    	System.out.println("printHexBinary(): "+plaintext.byteArrayToHexString(b));
	    	
	    	System.out.println("printHexBinary(): "+DatatypeConverter.printHexBinary(plaintext.getMessage()));
	    	
	    	String str = "123";
	    
	    	String str2 = "";
	    	for(int i=0; i<str.length(); i++){
	    		String temp = str.substring(i, i);
	    		byte[] beta = temp.getBytes();
	    		str2 = str2+DatatypeConverter.printHexBinary(beta);
	    	}
	    	
	    	str2 = DatatypeConverter.printHexBinary(plaintext.getMessage());
	    	System.out.println("String ascii:\t"+str);
	    	System.out.println("String hex:\t"+str2);
	    	System.out.println("String hex:\t"+plaintext.byteArrayToHexString(plaintext.hexStringToByteArray(str2)));
	    	*/
	    	AsymmetricKey ak = new AsymmetricKey(512, "RSA");
	    	
	    	System.out.println("public key: "+ak.displayPublicKey()+"\nkey length: "+ ak.getPublicKey().getEncoded().length);
	    	System.out.println("private key: "+ ak.displayPrivateKey() +"\nkey length: "+ ak.getPrivateKey().getEncoded().length);
	    	
	    	/*
	    	KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	        keyGen.initialize(512);
	        KeyPair key = keyGen.genKeyPair();
	        byte[] publicKey = key.getPublic().getEncoded();
	        byte[] privateKey = key.getPrivate().getEncoded();
	        StringBuffer pubString = new StringBuffer();
	        StringBuffer privString = new StringBuffer();
	        for (int i = 0; i < publicKey.length; ++i) {
	            pubString.append(Integer.toHexString(0x0100 + (publicKey[i] & 0x00FF)).substring(1)+" ");
	            privString.append(Integer.toHexString(0x0100 + (privateKey[i] & 0x00FF)).substring(1)+" ");
	        }*/
	       // System.out.println("Public Key: "+pubString+"\nkey length: "+ publicKey.length);
	        //System.out.println("Private Key: "+privString+"\nkey length: "+ privateKey.length);
	        
	        //CryptoTool ct = new CryptoTool();
	        
	        String plaintext = "really!!! this is cool";
	        //String cipher = "";
	        
	        //Padding pad = new Padding(64);
	        
	        //byte[] cipher = ct.encryptSymmetric(plain.getBytes(), publicKey, "RSA", "ECB", "".getBytes());
	        //cipher  = pad.paddingPKCS7(cipher);
	        CryptoTool ct = new CryptoTool();
	        Message ptext = new Message("this is cool","ascii");
	        System.out.println("plain: " + ptext.displayMessage());
	        
	        //ptext.convertToHex();
	        Message ctext = new Message("","hex");
	        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		    cipher.init(Cipher.ENCRYPT_MODE, ak.getPublicKey());
			ctext.setMessage(cipher.doFinal(ptext.getMessage()));
	        //ctext.setMessage(ct.encryptAsymmetric(ptext.getMessage(), ak.getPublicKey(), "RSA", "NoPadding"));
	        
	        //Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");///None/NoPadding", "BC");
	        //cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
	        //cipher.init(Cipher.ENCRYPT_MODE, ak.getPublicKey());

	        //byte[] ciphertext = cipher.doFinal(plaintext.getBytes());
	        System.out.println("plain: " + ptext.displayMessage()+"\nsize: "+ptext.getMessage().length);
	        System.out.println("cipher: " + ctext.displayMessage()+"\nsize: "+ctext.getMessage().length);

	        //cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
	        //cipher.init(Cipher.DECRYPT_MODE, ak.getPrivateKey());
	        //byte[] plainText = cipher.doFinal(ciphertext);
	        
	        //ptext.setMessage(ct.decryptAsymmetric(ctext.getMessage(), ak.getPrivateKey(), "RSA", "NoPadding"));
	        /*
	        System.out.println("plain : " + ptext.displayMessage()+"\nsize: "+ptext.getMessage().length);
	        ptext.convertToASCII();
	        System.out.println("plain : " + ptext.displayMessage()+"\nsize: "+ptext.getMessage().length);*/
	        //System.out.println("Plain Text: "+plain);
	       // System.out.println("Cipher Text: "+new String(cipher));
	        
	    	/*
	    	Message ecb = new Message("","hex");
	    	Message cbc = new Message("","hex");
	    	Message cfb = new Message("","hex");
	    	Message ofb = new Message("","hex");
	    	
	    	Padding pad = new Padding(32);
	    	CryptoTool ct = new CryptoTool();
	    	
	    	String s = "	";
	    	System.out.println("String: "+s);
	    	plaintext.convertToHex();
	    	System.out.println("to HEX: "+plaintext.displayMessage());
	    	plaintext.convertToASCII();
	    	System.out.println("to ASCII: "+plaintext.displayMessage());
	    	/*
	    	//////////////////////////// checking conversion
	    	Message crypto = new Message("crypto is cool!!! 007", "ascii");
	    	System.out.println("message in hex1: "+ new String(crypto.getMessage()));
	    	crypto.convertToHex();
	    	System.out.println("message in hex2: "+ crypto.displayMessage());
	    	crypto.convertToASCII();
	    	System.out.println("message in hex3: "+ new String(crypto.getMessage()));
	    	
	    	
	    	
	    	/////////////////////////////
	    	String stree = "0011223344556677AABBCCDDEEFFAABB";
	    	System.out.println("String full: "+stree+" String half: "+stree.substring(16,stree.length()));
	    	
	    	
	    	System.out.println("plain Ascii:\t"+plaintext.displayMessage());//byteArrayToHexString(plaintext.getMessage()));
	    	System.out.println("plain hex:\t"+plaintext.byteArrayToHexString(plaintext.getMessage()));
	    	plaintext.convertToHex();
	    	System.out.println("plain hex:\t"+plaintext.displayMessage());
	    	
	    	plaintext.setMessage(pad.paddingZeros(plaintext.getMessage()));
	    	ecb.setMessage(ct.encryptSymmetric(plaintext.getMessage(), encryptionKey, alg, "ECB", IV));
	    	cbc.setMessage(ct.encryptSymmetric(plaintext.getMessage(), encryptionKey, alg, "CBC", IV));
	    	cfb.setMessage(ct.encryptSymmetric(plaintext.getMessage(), encryptionKey, alg, "CFB", IV));
	    	ofb.setMessage(ct.encryptSymmetric(plaintext.getMessage(), encryptionKey, alg, "OFB", IV));
	    	
	    	//System.out.println("ECB:\t\t"+ecb.displayMessage());
	    	System.out.println("plain hex:\t"+plaintext.displayMessage());
	    	System.out.println("ECB:\t\t"+ecb.displayMessage()+" - type: "+ecb.getType());
	    	ecb.convertToASCII();
	    	System.out.println("ECB:\t\t"+ecb.displayMessage()+" - type: "+ecb.getType());
	    	ecb.convertToHex();
	    	System.out.println("ECB:\t\t"+ecb.displayMessage()+" - type: "+ecb.getType());
	    	
	    	
	    	//System.out.println("CBC:\t\t"+cbc.displayMessage());
	    	System.out.println("CBC:\t\t"+cbc.byteArrayToHexString(cbc.getMessage()));
	    	
	    	//System.out.println("CFB:\t\t"+cfb.displayMessage());
	    	System.out.println("CFB:\t\t"+cfb.byteArrayToHexString(cfb.getMessage()));
	    	
	    	//System.out.println("OFB:\t\t"+ofb.displayMessage());
	    	System.out.println("OFB:\t\t"+ofb.byteArrayToHexString(ofb.getMessage()));
	    	
			*/
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static String convertToASCII(String message){
		StringBuilder output = new StringBuilder("");
		for(int i=0; i<message.length(); i+=2){
			String str = message.substring(i, i+2);
			output.append((char)Integer.parseInt(str, 16));
		}
		message = output.toString();
		return message;
	}
	
	public static String convertToHex(String message){
		char[] chars = message.toCharArray();
		StringBuffer output = new StringBuffer();
		for(int i=0; i<chars.length; i++){
			output.append(Integer.toHexString((int) chars[i]));
		}
		message = output.toString();
		return message;
	}
	
	/**
	 * 
	 * @param s String
	 * @return data Byte[] array
	 */
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len/2];
	    for (int i = 0; i < len; i += 2) {
	        data[i/2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	
	/**
	 * 
	 * @param s String
	 * @return String without spaces
	 */
	public static String removeSpaces(String s) {
	    s = s.replaceAll("\\s+", "");
	    return s;
	}
	
	/**
	 * Zero Padding method
	 * @param plainText Byte array containing the unpadded plaintext
	 * @return Byte array containing plaintext with zero (0) padding
	 */
	public static byte[] paddingZeros(byte[] plainText, int blocksize){
		
		// calculate the number of bytes required for padding
		int numberofBytes = 0;
		if (plainText.length % blocksize != 0) numberofBytes = blocksize-((plainText.length) % blocksize);
		
		byte[] paddedPlaintext = new byte[plainText.length + numberofBytes];
		
		if(numberofBytes>0){
			byte[] padding = new byte[numberofBytes];
			for(int i=0; i<numberofBytes; i++){
				padding[i] = 0x00;
			}
			System.arraycopy(plainText, 0, paddedPlaintext, 0, plainText.length);
			System.arraycopy(padding, 0, paddedPlaintext, plainText.length, padding.length);
		}
		else System.arraycopy(plainText, 0, paddedPlaintext, 0, plainText.length);
		return paddedPlaintext;
	}
	
	/**
	 * Remove Zero Padding method
	 * @param plainText Byte array containing the padded plaintext
	 * @return Byte array containing plaintext without zero (0) padding
	 */
	public static byte[] removePaddingZeros(byte[] plainText, int blocksize){
		
		byte[] unpadded = null;
		if(plainText.length>0 && plainText.length>8){
			int counter = 0;
			for(int i=plainText.length-1; i>=0; i--){
				if(plainText[i] == 0x00){
					counter++;
				}
			}
			unpadded = new byte[plainText.length - counter];
			System.arraycopy(plainText, 0, unpadded, 0, plainText.length-counter);
		}
		else{
			unpadded = new byte[plainText.length];
			System.arraycopy(plainText, 0, unpadded, 0, plainText.length);
		}
		return unpadded;
	}
	
	/**
	 * ISO/IEC 7816-4 Padding method
	 * @param plainText Byte array containing the unpadded plaintext
	 * @return Byte array containing the plaintext with padding that starts with 8 followed by zeros
	 */
	public static byte[] padding7816_4(byte[] plainText, int blocksize){
		
		// calculate the number of bytes required for padding
		int numberofBytes = 0;
		if (plainText.length % blocksize != 0) numberofBytes = blocksize-((plainText.length) % blocksize);
		
		byte[] paddedPlaintext = new byte[plainText.length + numberofBytes];
		
		if(numberofBytes>0){
			byte[] padding = new byte[numberofBytes];
			for(int i=0; i<numberofBytes; i++){
				if(i==0) padding[i] = (byte) 0x80;
				else padding[i] = (byte) 0x00;
			}
			System.arraycopy(plainText, 0, paddedPlaintext, 0, plainText.length);
			System.arraycopy(padding, 0, paddedPlaintext, plainText.length, padding.length);
		}
		else System.arraycopy(plainText, 0, paddedPlaintext, 0, plainText.length);
		return paddedPlaintext;
	}
	
	/**
	 * Remove ISO/IEC 7816-4 Padding method
	 * @param plainText Byte array containing the padded plaintext
	 * @return Byte array containing the plaintext without padding that starts with 8 followed by zeros
	 */
	public static byte[] removePadding7816_4(byte[] plainText, int blocksize){
		
		byte[] unpadded = null;
		if(plainText.length>0 && plainText.length>8){
			int counter = 0;
			for(int i=plainText.length-1; i>=0; i--){
				if(plainText[i] == 0x00){
					counter++;
				}
				else if(plainText[i] == (byte) 0x80){
					counter++;
				}
			}
			unpadded = new byte[plainText.length - counter];
			System.arraycopy(plainText, 0, unpadded, 0, plainText.length-counter);
		}
		else{
			unpadded = new byte[plainText.length];
			System.arraycopy(plainText, 0, unpadded, 0, plainText.length);
		}
		return unpadded;
	}
	
	/**
	 * PKCS7 Padding method
	 * @param plainText Byte array containing the unpadded plaintext
	 * @return Byte array containing the plaintext with PKCS7 padding
	 */
	public static byte[] paddingPKCS7(byte[] plainText, int blocksize){
		
		// calculate the number of bytes required for padding
		int numberofBytes = 0;
		if (plainText.length % blocksize != 0) numberofBytes = blocksize-((plainText.length) % blocksize);
		
		byte[] paddedPlaintext = new byte[plainText.length + numberofBytes];
		
		if(numberofBytes>0){
			byte[] padding = new byte[numberofBytes];
			for(int i=0; i<numberofBytes; i++){
				padding[i] = (byte) numberofBytes;
			}
			System.arraycopy(plainText, 0, paddedPlaintext, 0, plainText.length);
			System.arraycopy(padding, 0, paddedPlaintext, plainText.length, padding.length);
		}
		else System.arraycopy(plainText, 0, paddedPlaintext, 0, plainText.length);
		return paddedPlaintext;
	}
	
	/**
	 * Remove PKCS7 Padding method
	 * @param plainText Byte array containing the padded plaintext
	 * @return Byte array containing the plaintext without PKCS7 padding
	 */
	public static byte[] removePaddingPKCS7(byte[] plainText, int blocksize){
		
		byte[] unpadded = null;
		if(plainText.length>0 && plainText.length>8){
			int counter = (int) plainText[plainText.length-1];
			unpadded = new byte[plainText.length - counter];
			System.arraycopy(plainText, 0, unpadded, 0, plainText.length-counter);
		}
		else{
			unpadded = new byte[plainText.length];
			System.arraycopy(plainText, 0, unpadded, 0, plainText.length);
		}
		return unpadded;
	}
	
	/**
	 * ANSI X.923 Padding method
	 * @param plainText Byte array containing the unpadded plaintext
	 * @return Byte array containing the plaintext with ANSI X.923 padding
	 */
	public static byte[] paddingANSI_X923(byte[] plainText, int blocksize){
		
		// calculate the number of bytes required for padding
		int numberofBytes = 0;
		if (plainText.length % blocksize != 0) numberofBytes = blocksize-((plainText.length) % blocksize);
		
		byte[] paddedPlaintext = new byte[plainText.length + numberofBytes];
		
		if(numberofBytes>0){
			byte[] padding = new byte[numberofBytes];
			for(int i=0; i<numberofBytes; i++){
				if(i == numberofBytes-1) padding[i] = (byte) numberofBytes;
				else padding[i] = (byte) 0x00;
			}
			System.arraycopy(plainText, 0, paddedPlaintext, 0, plainText.length);
			System.arraycopy(padding, 0, paddedPlaintext, plainText.length, padding.length);
		}
		else System.arraycopy(plainText, 0, paddedPlaintext, 0, plainText.length);
		return paddedPlaintext;
	}
	
	/**
	 * Remove ANSI X.923 Padding method
	 * @param plainText Byte array containing the padded plaintext
	 * @return Byte array containing the plaintext without ANSI X.923 padding
	 */
	public static byte[] removePaddingANSI_X923(byte[] plainText, int blocksize){
		
		byte[] unpadded = null;
		
		if(plainText.length>0 && plainText.length>8){
			int counter = (int) plainText[plainText.length-1];
			unpadded = new byte[plainText.length - counter];
			System.arraycopy(plainText, 0, unpadded, 0, plainText.length-counter);
		}
		else{
			unpadded = new byte[plainText.length];
			System.arraycopy(plainText, 0, unpadded, 0, plainText.length);
		}
		return unpadded;
	}
	
	/**
	 * Convert byte[] to hex String with spaces between every byte block
	 * @param data byte[] to be converted
	 * @return String hex String with spaces
	 */
	public static String toJavaHex(byte[] data) {
	    StringBuilder sb = new StringBuilder(13 * data.length);
	    for (int i = 0; i < data.length; i++) {
	        if (i != 0) {
	            sb.append(" ");
	        }
	        sb.append(String.format("%02x", data[i]));
	    }
	    return sb.toString().toUpperCase();
	}
	
	public static byte[] encrypt(byte[] plainText, byte[] encryptionKey, String alg, String mode, byte[] iv)
	        throws Exception {
	    Cipher cipher = Cipher.getInstance(alg+"/"+mode+"/NoPadding");
	    SecretKeySpec key = new SecretKeySpec(encryptionKey, alg);
	    
	    if(mode == "ECB") cipher.init(Cipher.ENCRYPT_MODE, key);
	    else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
	    //System.out.println("IV:\t\t"+toJavaHex(IV));
	    return cipher.doFinal(plainText);
	}
	
	public static byte[] decrypt(byte[] cipherText, byte[] encryptionKey, String alg, String mode, byte[] iv)
	        throws Exception {
	    Cipher cipher = Cipher.getInstance(alg+"/"+mode+"/NoPadding");
	    SecretKeySpec key = new SecretKeySpec(encryptionKey, alg);
	    
	    if(mode=="ECB") cipher.init(Cipher.DECRYPT_MODE, key);
	    else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
	    
	    return cipher.doFinal(cipherText);
	}
}