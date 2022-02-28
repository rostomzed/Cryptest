/**
 * Padding.java
 */

/**
 * @author Rostom Zouaghi
 * 3 Aug 2014
 * 15:32:17
 */
public class Padding {
	
	private int blocksize;
	
	/**
	 * Constructor for the Padding Object
	 * @param b set the block size of the ciphertext for padding
	 */
	public Padding(int b){
		blocksize = b;
	}
	
	/**
	 * Zero Padding method
	 * @param plainText Byte array containing the unpadded plaintext
	 * @return Byte array containing plaintext with zero (0) padding
	 */
	public byte[] paddingZeros(byte[] plainText){
		
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
	public byte[] removePaddingZeros(byte[] plainText){
		
		byte[] unpadded = null;
		if(plainText.length>0){// && plainText.length>8){
			int counter = 0;
			for(int i=plainText.length-1; i>0; i--){
				if(plainText[i] == 0x00){
					counter++;
				}
				else break;
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
	public byte[] padding7816_4(byte[] plainText){
		
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
	public byte[] removePadding7816_4(byte[] plainText){
		
		byte[] unpadded = null;
		if(plainText.length>0){// && plainText.length>8){
			int counter_0 = 0;
			int counter_8 = 0;
			for(int i=plainText.length-1; i>=0; i--){
				if(plainText[i] == 0x00){
					counter_0++;
				}
				else if(plainText[i] == (byte) 0x80){
					counter_0++;
					counter_8++;
					break;
				}
			}
			if(counter_8==1){
				unpadded = new byte[plainText.length - counter_0];
				System.arraycopy(plainText, 0, unpadded, 0, plainText.length-counter_0);
			}
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
	public byte[] paddingPKCS7(byte[] plainText){
		
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
	public byte[] removePaddingPKCS7(byte[] plainText){
		
		byte[] unpadded = null;
		int counter = (int) plainText[plainText.length-1];
		int b = plainText.length%blocksize;
		if(plainText.length>0 && plainText.length>counter && b==0 && counter>0){
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
	public byte[] paddingANSI_X923(byte[] plainText){
		
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
	public byte[] removePaddingANSI_X923(byte[] plainText){
		byte[] unpadded = null;
		int counter = (int) plainText[plainText.length-1];
		int b = plainText.length%blocksize;
		if(plainText.length>0 && plainText.length>counter && b==0 && counter>0){
			unpadded = new byte[plainText.length - counter];
			System.arraycopy(plainText, 0, unpadded, 0, plainText.length-counter);
		}
		else{
			unpadded = new byte[plainText.length];
			System.arraycopy(plainText, 0, unpadded, 0, plainText.length);
		}
		return unpadded;
	}
}
