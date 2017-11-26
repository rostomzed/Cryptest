/**
 * CryptoTool.java
 */

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Rostom Zouaghi
 * 21 Jul 2014
 * 13:39:31
 */
public class CryptoTool {
	
	/**
	 * Constructor for the CryptoTool class
	 */
	public CryptoTool(){}
		
	/**
	 * Symmetric Encryption method
	 * @param plainText byte[] plaintext to be encrypted
	 * @param encryptionKey byte[] key to be used
	 * @param alg String encryption algorithm to be used
	 * @param mode String mode of encryption
	 * @param iv byte[] Initialisation Value to be used
	 * @return ciphertext byte[] encrypted plaintext
	 * @throws Exception
	 */
	public byte[] encryptSymmetric(byte[] plainText, byte[] encryptionKey, String alg, String mode, byte[] iv)
	        throws Exception {
	    Cipher cipher = Cipher.getInstance(alg+"/"+mode+"/NoPadding");
	    SecretKeySpec key = new SecretKeySpec(encryptionKey, alg);
	    if(mode.equals("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
	    else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
	    return cipher.doFinal(plainText);
	}
	
	/**
	 * Symmetric Decryption method
	 * @param cipherText byte[] ciphertext to be decrypted
	 * @param encryptionKey byte[] key to be used
	 * @param alg String decryption algorithm to be used
	 * @param mode String mode of decryption
	 * @param iv byte[] Initialisation Value to be used
	 * @return plaintext byte[] decrypted ciphertext
	 * @throws Exception
	 */
	public byte[] decryptSymmetric(byte[] cipherText, byte[] encryptionKey, String alg, String mode, byte[] iv)
	        throws Exception {
	    Cipher cipher = Cipher.getInstance(alg+"/"+mode+"/NoPadding");
	    SecretKeySpec key = new SecretKeySpec(encryptionKey, alg);
	    if(mode.equals("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
	    else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
	    return cipher.doFinal(cipherText);
	}
	
	/**
	 * 
	 * @param plainText byte[] plaintext to be encrypted
	 * @param publickey byte[] public key to use for encryption
	 * @param alg String Asymmetric Cryptographic algorithm
	 * @param padding String
	 * @return ciphertext byte[] encrypted plaintext
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeySpecException 
	 */
	public byte[] encryptAsymmetric(byte[] plainText, byte[] publickey, String alg, String padding) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
		Cipher cipher = Cipher.getInstance(alg+"/ECB/"+padding);
		X509EncodedKeySpec key = new X509EncodedKeySpec(publickey);
		KeyFactory kf = KeyFactory.getInstance(alg);
		PublicKey pk = kf.generatePublic(key);
	    cipher.init(Cipher.ENCRYPT_MODE, pk);
		return cipher.doFinal(plainText);
	}
	
	/**
	 * 
	 * @param cipherText byte[] ciphertext to be decrypted
	 * @param privatekey byte[] private key to use for decryption
	 * @param alg String Asymmetric Cryptographic algorithm
	 * @param padding String
	 * @return plaintext byte[] decrypted ciphertext
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeySpecException 
	 */
	public byte[] decryptAsymmetric(byte[] cipherText, byte[] privatekey, String alg, String padding) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
		Cipher cipher = Cipher.getInstance(alg+"/ECB/"+padding);
		PKCS8EncodedKeySpec key = new PKCS8EncodedKeySpec(privatekey);
		KeyFactory kf = KeyFactory.getInstance(alg);
		PrivateKey sk = kf.generatePrivate(key);
	    cipher.init(Cipher.DECRYPT_MODE, sk);
		return cipher.doFinal(cipherText);
	}
}
