import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * AsymmetricKey.java
 */

/**
 * @author Rostom Zouaghi
 * 4 Aug 2014
 * 16:23:30
 */
public class AsymmetricKey {
	
	private byte[] publickey;
	private byte[] privatekey;
	KeyPair key;
	private String type;
	private int size;
	
	
	/**
	 * Constructor for the AsymmetricKey class - no assignments
	 */
	public AsymmetricKey(){}
	
	/**
	 * Constructor for the AsymmetricKey class - manually set the key
	 * @param String k key value
	 * @param String t type of key: "ascii" or "hex"
	 */
	public AsymmetricKey(String pk, String sk, String t, int s) {
		if(t.equals("ascii")){
			publickey = pk.getBytes();
			privatekey = sk.getBytes();
			type = t;
			size = s;
		}
		else{
			publickey = hexStringToByteArray(removeSpaces(pk));
			privatekey = hexStringToByteArray(removeSpaces(sk));
			type = t;
			size = s;
		}
	}
	
	/**
	 * Constructor for the AsymmetricKey class - generate key pair
	 * @param a Algorithm used
	 * @param s size of the key
	 * @throws NoSuchAlgorithmException 
	 */
	public AsymmetricKey(int s, String algorithm) throws NoSuchAlgorithmException{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
        keyGen.initialize(s);
        key = keyGen.genKeyPair();
        publickey = key.getPublic().getEncoded();
        privatekey = key.getPrivate().getEncoded();
        type = "hex";
	}
	
	/**
	 * Returns the public key as a PublicKey object
	 * @return PublicKey publickey
	 */
	public PublicKey getPublicKey(){
		return key.getPublic();
	}
	
	/**
	 * Returns the private key as a PrivateKey object
	 * @return PrivateKey privatekey
	 */
	public PrivateKey getPrivateKey(){
		return key.getPrivate();
	}
	
	/**
	 * Returns the public key in byte array
	 * @return byte[] publickey
	 */
	public byte[] getPublicKeyByte(){
		return publickey;
	}
	
	/**
	 * Returns the private key in byte array
	 * @return byte[] privatekey
	 */
	public byte[] getPrivateKeyByte(){
		return privatekey;
	}
	
	/**
	 * Returns the size of the key
	 * @return int size of key
	 */
	public int getSize(){
		return size;
	}
	/**
	 * Sets the key pair
	 * @param kp KeyPair
	 */
	public void setKeyPair(KeyPair kp){
		key = kp;
	}
	
	/**
	 * Sets the public key
	 * @param pk
	 */
	public void setPublicKey(byte[] pk){
		publickey = pk;
	}
	
	/**
	 * Sets the private key
	 * @param sk
	 */
	public void setPrivateKey(byte[] sk){
		privatekey = sk;
	}
	
	/**
	 * Return a String of the public key that can be displayed in HEX or ASCII
	 * @return publickey String
	 */
	public String displayPublicKey(){
		if(type == "ascii")	return new String(publickey);
		else return byteArrayToHexString(publickey);
	}
	
	/**
	 * Return a String of the private key that can be displayed in HEX or ASCII
	 * @return privatekey String
	 */
	public String displayPrivateKey(){
		if(type == "ascii")	return new String(privatekey);
		else return byteArrayToHexString(privatekey);
	}
	
	/**
	 * Convert hex string to a byte[] array
	 * @param s String
	 * @return data Byte[] array
	 */
	public byte[] hexStringToByteArray(String s){
		if (s.length() % 2 != 0) s = s+"0";
		byte[] data = new byte[s.length()/2];
		for (int i = 0; i < s.length(); i += 2)
			data[i/2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
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
	 * Load public key from file (x509 encoded)
	 * @param path public key path
	 * @param algorithm Asymmetric Algorithm
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public void loadPublicKey(String path, String algorithm)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		
		// Read Public Key
		File filePublicKey = new File(path);
		FileInputStream fis = new FileInputStream(path);
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();
 
		// Generate public key
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		
		publickey = publicKey.getEncoded();
		
	}
	
	/**
	 * Load private key from file (PKCS8 Encoded)
	 * @param path private key path
	 * @param algorithm Asymmetric Algorithm
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public void loadPrivateKey(String path, String algorithm)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		// Read Private Key
		File filePrivateKey = new File(path);
		FileInputStream fis = new FileInputStream(path);
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();
 
		// Generate private key
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		
        	privatekey = privateKey.getEncoded();		
	}
}
