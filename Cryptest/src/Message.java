import javax.xml.bind.DatatypeConverter;

/**
 * Message.java
 */

/**
 * @author Rostom Zouaghi
 * 21 Jul 2014
 * 10:09:35
 */
/**
 * This Class contains the attributes and return methods of a typical Message to be processed
 * for encryption or hashing.
 */
public class Message {
	private byte[] message;
	private String type;
	
	/**
	 * Constructor for the Message Object
	 * @param String m message
	 * @param String t type of message: "ascii" or "hex"
	 */
	public Message(String m, String t) {
		if(t.equals("ascii")){
			message = m.getBytes();
			type = t;
		}
		else{
			message = hexStringToByteArray(removeSpaces(m));
			type = t;
		}
	}
	
	/**
	 * Returns the message
	 * @return byte[] message
	 */
	public byte[] getMessage(){
		return message;
	}
	
	/**
	 * Returns the type of the message
	 * @return String type
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Set the message value
	 * @param byte[] m message
	 */
	public void setMessage(byte[] m){
		message = m;
	}
	
	/**
	 * Set the message type
	 * @param String t type
	 */
	public void setType(String t){
		type = t;
	}
	
	/**
	 * Return a String that can be displayed in HEX or ASCII
	 * @return output String
	 */
	public String displayMessage(){
		if(type.equals("ascii")) return new String(message);
		else return byteArrayToHexString(message);
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
	/*public byte[] hexStringToByteArray(String s){
		//return DatatypeConverter.parseHexBinary(s);
	}*/
	
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
	 * Convert the message from HEX to ASCII
	 */
	public void convertToASCII(){
		String s = removeSpaces(byteArrayToHexString(message));
		StringBuilder output = new StringBuilder("");
		for(int i=0; i<s.length(); i+=2){				
			String str = s.substring(i, i+2);
			output.append((char)Integer.parseInt(str, 16));
		}
		s = output.toString();
		message = s.getBytes();
		type = "ascii";
	}	
	
	/**
	 * Convert the message from ASCII to HEX
	 */
	public void convertToHex(){
		String temp = DatatypeConverter.printHexBinary(message);
		message = hexStringToByteArray(temp);
		type = "hex";
	}
	/* previous code
	public void convertToHex(){
		String s = new String(message);
		char[] chars = s.toCharArray();
		StringBuffer output = new StringBuffer();
		for(int i=0; i<chars.length; i++){
			output.append(Integer.toHexString((int) chars[i]));
		}
		s = output.toString();
		message = hexStringToByteArray(s);
		type = "hex";
	}*/
}