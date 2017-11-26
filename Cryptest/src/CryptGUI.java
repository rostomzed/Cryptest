/**
 * CryptGUI.java
 */

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.swing.JFileChooser;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * @author Rostom Zouaghi
 * 4 Aug 2014
 * 15:06:09
 */
public class CryptGUI {

	protected Shell shlCryptest;
	private Text plainText;
	private Text cipherText;
	private Text paddedPlainText;
	private Label lbPaddedPlainText;
	private Text keyText;
	private Text ivText;
	private Button btnGenerateKey;
	private Button btnDecrypt;
	private ComboViewer comboViewer;
	private Combo comboCryptoAlg;
	private Combo comboModes;
	private ComboViewer comboViewer_1;
	private Combo comboPadding;
	private ComboViewer comboViewer_2;
	private Label lblModesOfOperation;
	private Label lblPadding;
	private Label lblIv;
	private Label lblKey;
	private Label lblPlaintext;
	private Label lblCiphertext;
	private Button btnGenerateIV;
	private Combo comboTypePlainText;
	private ComboViewer comboViewer_3;
	private Combo comboTypeCipherText;
	private ComboViewer comboViewer_4;
	private Button btnConvertPlaintext;
	private Button btnConvertCiphertext;
	private Text privateKeyText;
	private Label lblPrivateKey;
	private JFileChooser fileChooser;
	
	private String[] symmetricPadding = {"Zeros (0)","ISO/IEC 7816-4","PKCS#7","ANSI X.923"};
	private String[] rsaPadding = {"No Padding", "PKCS#1", "OAEP With SHA-1 And MGF1", "OAEP With SHA-256 And MGF1"};
	
	private String cry;
	private int size;
	private int blocksize;
	private int keylength;
	private AsymmetricKey aKey;
	private Button btnLoadKeyPair;
	private Combo comboAsymmetricKeyType;
	private ComboViewer comboViewerAsymmetricKeyType;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CryptGUI window = new CryptGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlCryptest.open();
		shlCryptest.layout();
		while (!shlCryptest.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCryptest = new Shell();
		shlCryptest.setToolTipText("");
		shlCryptest.setSize(979, 624);
		shlCryptest.setText("Cryptest");
		shlCryptest.setLayout(null);
		
		plainText = new Text(shlCryptest, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		plainText.setBackground(SWTResourceManager.getColor(153, 255, 153));
		plainText.setBounds(67, 361, 301, 163);
		
		cipherText = new Text(shlCryptest, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		cipherText.setBackground(SWTResourceManager.getColor(233, 150, 122));
		cipherText.setBounds(594, 361, 301, 163);
			
		keyText = new Text(shlCryptest, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		keyText.setBounds(594, 55, 301, 109);
		
		lblKey = new Label(shlCryptest, SWT.NONE);
		lblKey.setText("Key - the key length must be 8 bytes");
		lblKey.setBounds(594, 34, 301, 15);
		
		ivText = new Text(shlCryptest, SWT.BORDER | SWT.WRAP);
		ivText.setBounds(265, 55, 301, 72);
		
		lblIv = new Label(shlCryptest, SWT.NONE);
		lblIv.setText("IV - default (zeros) - the IV length must be 8 bytes");
		lblIv.setBounds(265, 34, 301, 15);
		
		lblPlaintext = new Label(shlCryptest, SWT.NONE);
		lblPlaintext.setBounds(67, 340, 301, 15);
		lblPlaintext.setText("Plaintext");
		
		lblCiphertext = new Label(shlCryptest, SWT.NONE);
		lblCiphertext.setText("Ciphertext");
		lblCiphertext.setBounds(594, 340, 301, 15);
		
		paddedPlainText = new Text(shlCryptest, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		paddedPlainText.setBounds(67, 215, 301, 119);
		
		lbPaddedPlainText = new Label(shlCryptest, SWT.NONE);
		lbPaddedPlainText.setText("Padded Plaintext");
		lbPaddedPlainText.setBounds(67, 195, 95, 15);
		
		SashForm sashForm = new SashForm(shlCryptest, SWT.NONE);
		sashForm.setBounds(105, 84, 57, -31);
		
		String[] cryptoAlgorithms = {"DES (56-bits)", "TDES (112-bits)", "TDES (168-bits)", "AES (128-bits)", "AES (192-bits)", 
				"AES (256-bits)", "RSA (512-bits)", "RSA (1024-bits)", "RSA (2048-bits)", "RSA (3072-bits)", "RSA (4096-bits)"};
		comboViewer = new ComboViewer(shlCryptest, SWT.NONE);
		comboCryptoAlg = comboViewer.getCombo();
		comboCryptoAlg.setToolTipText("Select the desired\r\nencryption algorithm");
		comboCryptoAlg.setItems(cryptoAlgorithms);
		comboCryptoAlg.setText("DES (56-bits)");
		comboCryptoAlg.setEnabled(true);
		comboCryptoAlg.setTouchEnabled(true);
		comboCryptoAlg.setBounds(67, 55, 145, 23);
		
		
		privateKeyText = new Text(shlCryptest, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		privateKeyText.setBounds(594, 195, 301, 108);
		
		lblPrivateKey = new Label(shlCryptest, SWT.NONE);
		lblPrivateKey.setText("Private Key - Not applicable");
		lblPrivateKey.setBounds(594, 171, 186, 18);
		
		
		comboCryptoAlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(comboCryptoAlg.getText().equals("DES (56-bits)")){
		    		lblKey.setText("Key - the key length must be 8 bytes");
		    		lblIv.setText("IV - default (zeros) - the IV length must be 8 bytes");
		    		initialiseSymmetric();
		    	}		
		    	else if(comboCryptoAlg.getText().equals("TDES (112-bits)")){
		    		lblKey.setText("Key - the key length must be 16 bytes");
		    		lblIv.setText("IV - default (zeros) - the IV length must be 8 bytes");
		    		initialiseSymmetric();
		    	}
		    	else if(comboCryptoAlg.getText().equals("TDES (168-bits)")){
		    		lblKey.setText("Key - the key length must be 24 bytes");
		    		lblIv.setText("IV - default (zeros) - the IV length must be 8 bytes");
		    		initialiseSymmetric();
		    	}
		    	else if(comboCryptoAlg.getText().equals("AES (128-bits)")){
		    		lblKey.setText("Key - the key length must be 16 bytes");
		    		lblIv.setText("IV - default (zeros) - the IV length must be 16 bytes");
		    		initialiseSymmetric();
		    	}
		    	else if(comboCryptoAlg.getText().equals("AES (192-bits)")){
		    		lblKey.setText("Key - the key length must be 24 bytes");
		    		lblIv.setText("IV - default (zeros) - the IV length must be 16 bytes");
		    		initialiseSymmetric();
		    	}
		    	else if(comboCryptoAlg.getText().equals("AES (256-bits)")){
		    		lblKey.setText("Key - the key length must be 32 bytes");
		    		lblIv.setText("IV - default (zeros) - the IV length must be 16 bytes");
		    		initialiseSymmetric();
		    	}
		    	else if(comboCryptoAlg.getText().equals("RSA (512-bits)")){
		    		lblKey.setText("Public Key - the key length must be 64 bytes");
		    		lblPrivateKey.setText("Private Key - the key length must be 64 bytes");
		    		initialiseAsymmetric(64);
		    	}
		    	else if(comboCryptoAlg.getText().equals("RSA (1024-bits)")){
		    		lblKey.setText("Public Key - the key length must be 128 bytes");
		    		lblPrivateKey.setText("Private Key - the key length must be 128 bytes");
		    		initialiseAsymmetric(128);
		    	}
		    	else if(comboCryptoAlg.getText().equals("RSA (2048-bits)")){
		    		lblKey.setText("Public Key - the key length must be 256 bytes");
		    		lblPrivateKey.setText("Private Key - the key length must be 256 bytes");
		    		initialiseAsymmetric(256);
		    	}
		    	else if(comboCryptoAlg.getText().equals("RSA (3072-bits)")){
		    		lblKey.setText("Public Key - the key length must be 384 bytes");
		    		lblPrivateKey.setText("Private Key - the key length must be 384 bytes");
		    		initialiseAsymmetric(384);
		    	}
		    	else if(comboCryptoAlg.getText().equals("RSA (4096-bits)")){
		    		//lblKey.setText("Public Key - the key length must be 512 bytes");
		    		//lblPrivateKey.setText("Private Key - the key length must be 512 bytes");
		    		initialiseAsymmetric(512);
		    	}
			}
		});
		
		Label lblCryptographicAlgorithms = new Label(shlCryptest, SWT.NONE);
		lblCryptographicAlgorithms.setBounds(67, 34, 145, 15);
		lblCryptographicAlgorithms.setText("encryption Algorithm");
		
		String[] modes = {"ECB", "CBC", "CFB", "OFB"};
		comboViewer_1 = new ComboViewer(shlCryptest, SWT.NONE);
		comboModes = comboViewer_1.getCombo();
		comboModes.setTouchEnabled(true);
		comboModes.setItems(modes);
		comboModes.setText("ECB");
		comboModes.setEnabled(true);
		comboModes.setBounds(67, 104, 145, 23);
		
		comboViewer_2 = new ComboViewer(shlCryptest, SWT.NONE);
		comboPadding = comboViewer_2.getCombo();
		comboPadding.setTouchEnabled(true);
		comboPadding.setItems(symmetricPadding);
		comboPadding.setText("Zeros (0)");
		comboPadding.setEnabled(true);
		comboPadding.setBounds(67, 150, 145, 23);
		
		String[] type = {"HEX", "ASCII"};
		comboViewer_3 = new ComboViewer(shlCryptest, SWT.NONE);
		comboTypePlainText = comboViewer_3.getCombo();
		comboTypePlainText.setToolTipText("Set the plaintext to either be in HEX or ASCII.");
		comboTypePlainText.setTouchEnabled(true);
		comboTypePlainText.setItems(type);
		comboTypePlainText.setText("HEX");
		comboTypePlainText.setEnabled(true);
		comboTypePlainText.setBounds(67, 529, 145, 23);
		
		comboViewer_4 = new ComboViewer(shlCryptest, SWT.NONE);
		comboTypeCipherText = comboViewer_4.getCombo();
		comboTypeCipherText.setToolTipText("Set the ciphertext to either be in HEX or ASCII.");
		comboTypeCipherText.setTouchEnabled(true);
		comboTypeCipherText.setItems(type);
		comboTypeCipherText.setText("HEX");
		comboTypeCipherText.setEnabled(true);
		comboTypeCipherText.setBounds(594, 530, 145, 23);
		
		lblModesOfOperation = new Label(shlCryptest, SWT.NONE);
		lblModesOfOperation.setText("Mode of Operation");
		lblModesOfOperation.setBounds(67, 84, 145, 15);
		
		lblPadding = new Label(shlCryptest, SWT.NONE);
		lblPadding.setToolTipText("Select the desired padding scheme");
		lblPadding.setText("Padding");
		lblPadding.setBounds(67, 133, 145, 15);
		
		
		comboViewerAsymmetricKeyType = new ComboViewer(shlCryptest, SWT.NONE);
		comboAsymmetricKeyType = comboViewerAsymmetricKeyType.getCombo();
		comboAsymmetricKeyType.setTouchEnabled(true);
		comboAsymmetricKeyType.setToolTipText("Set the plaintext to either be in HEX or ASCII.");
		comboAsymmetricKeyType.setItems(new String[] {"Public Key", "Private Key"});
		comboAsymmetricKeyType.setEnabled(false);
		comboAsymmetricKeyType.setBounds(594, 311, 133, 23);
		comboAsymmetricKeyType.setText("Public Key");
		
		btnGenerateKey = new Button(shlCryptest, SWT.NONE);
		btnGenerateKey.setToolTipText("Generate a random key. \r\nFirst select the desired encryption Algorithm,\r\nthen click.");
		btnGenerateKey.setText("Generate Key");
		btnGenerateKey.setBounds(786, 166, 109, 25);
		btnGenerateKey.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
			    	if(comboCryptoAlg.getText().equals("DES (56-bits)")){
			    		SymmetricKey key = new SymmetricKey("DES", 56);
						keyText.setText(key.displayKey());
			    	}		
			    	else if(comboCryptoAlg.getText().equals("TDES (112-bits)")){
			    		SymmetricKey key = new SymmetricKey("DESede", 112);
						keyText.setText(key.displayKey());
			    	}
			    	else if(comboCryptoAlg.getText().equals("TDES (168-bits)")){
			    		SymmetricKey key = new SymmetricKey("DESede", 168);
						keyText.setText(key.displayKey());
			    	}
			    	else if(comboCryptoAlg.getText().equals("AES (128-bits)")){
			    		SymmetricKey key = new SymmetricKey("AES", 128);
						keyText.setText(key.displayKey());
			    	}
			    	else if(comboCryptoAlg.getText().equals("AES (192-bits)")){
			    		SymmetricKey key = new SymmetricKey("AES", 192);
						keyText.setText(key.displayKey());
			    	}
			    	else if(comboCryptoAlg.getText().equals("AES (256-bits)")){
			    		SymmetricKey key = new SymmetricKey("AES", 256);
						keyText.setText(key.displayKey());
			    	}
			    	else if(comboCryptoAlg.getText().equals("RSA (512-bits)")){
			    		generateAsymmetricKey(512, "RSA");
			    	}
			    	else if(comboCryptoAlg.getText().equals("RSA (1024-bits)")){
			    		generateAsymmetricKey(1024, "RSA");
			    	}
			    	else if(comboCryptoAlg.getText().equals("RSA (2048-bits)")){
			    		generateAsymmetricKey(2048, "RSA");
			    	}
			    	else if(comboCryptoAlg.getText().equals("RSA (3072-bits)")){
			    		generateAsymmetricKey(3072, "RSA");
			    	}
			    	else if(comboCryptoAlg.getText().equals("RSA (4096-bits)")){
			    		generateAsymmetricKey(4096, "RSA");
			    	}
			    	
			    	else{
			    		MessageDialog.openError(shlCryptest, "Error", "Select an encryption Algorithm");
			    	}
				} catch (NoSuchAlgorithmException e1) {
					MessageDialog.openError(shlCryptest, "Error", "No Such Algorithm");
				}
			}
		});
		
		
		
		/**
		 * Encrypt button press
		 */
		Button btnEncrypt = new Button(shlCryptest, SWT.NONE);
		btnEncrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					
					//Get crypto algorithm selected and initialise required values
					getCryptoAlgorithm();
					
			    	//create two instances of Message: plaintext and ciphertext
			    	//get type from combo box set default type to hex
			    	String typeP = comboTypePlainText.getText().toLowerCase();
			    	String typeC = comboTypeCipherText.getText().toLowerCase();
			    	if(typeP.length()==0) typeP = "hex";
			    	if(typeC.length()==0) typeC = "hex";
			    	Message plaintext = new Message(plainText.getText(), typeP);
					Message ciphertext = new Message("", typeC);
					CryptoTool ct = new CryptoTool();
					
					//if Symmetric algorithm
					if(isSymmetric()){
						//set key
				    	SymmetricKey encryptionKey = new SymmetricKey(keyText.getText(), "hex", size);
				    	
				    	//set padding
						Padding pad = new Padding(blocksize);
						
				    	//set IV to zeros
				    	if(plaintext.removeSpaces(ivText.getText()).length()==0 && !comboModes.getText().equals("ECB")){
				    		String iv = "";
				    		for(int i=0;i<blocksize;i++){
				    			if(i==blocksize-1) iv = iv+"00";
				    			else iv = iv+"00 ";
				    		}
				    		ivText.setText(iv);
				    	}
				    	//set IV value
				    	byte[] IV = plaintext.hexStringToByteArray(plaintext.removeSpaces(ivText.getText()));
				    	
				    	//if the IV length is wrong, display error message
				    	if(IV.length!=blocksize && !(comboModes.getText().equals("ECB"))) 
				    		MessageDialog.openError(shlCryptest, "Error", "IV length is wrong");
				    	//verify if a mode of operation is selected
				    	if(comboModes.getText().equals("")) 
				    		MessageDialog.openError(shlCryptest, "Error", "Select a Mode of Operation");
				    	//verify if there is plaintext to encrypt
				    	if(plainText.getText().equals("")) 
				    		MessageDialog.openError(shlCryptest, "Error", "Enter some plaintext");
				    	//verify keylength
				    	if(plaintext.removeSpaces(keyText.getText()).length()!=keylength*2) 
				    		MessageDialog.openError(shlCryptest, "Error", "The key length is wrong");
				    	
			    		//pad plaintext - default is set to Zeros padding/unpadding
			    		if(comboPadding.getText().equals("Zeros (0)") || comboPadding.getText().equals("")) 
			    			plaintext.setMessage(pad.paddingZeros(plaintext.getMessage()));
			    		else if(comboPadding.getText().equals("ISO/IEC 7816-4")) 
			    			plaintext.setMessage(pad.padding7816_4(plaintext.getMessage()));
			    		else if(comboPadding.getText().equals("PKCS#7")) 
			    			plaintext.setMessage(pad.paddingPKCS7(plaintext.getMessage()));
			    		else if(comboPadding.getText().equals("ANSI X.923")) 
			    			plaintext.setMessage(pad.paddingANSI_X923(plaintext.getMessage()));
			    		
			    		//encrypt plaintext
			    		ciphertext.setMessage(ct.encryptSymmetric(plaintext.getMessage(), encryptionKey.getKey(), cry, comboModes.getText(), IV));
			    		
			    		//display padded plaintext
						paddedPlainText.setText(plaintext.byteArrayToHexString(plaintext.getMessage()));
					}
					else if(isAsymmetric()){
						//get X509 encoded public key from key text
						AsymmetricKey publicKey = new AsymmetricKey(keyText.getText(), privateKeyText.getText(), "hex", size);
						//verify if there is plaintext to encrypt
						if(plainText.getText().equals("")) 
				    		MessageDialog.openError(shlCryptest, "Error", "Enter some plaintext");
				    	//verify size of plaintext
				    	//if(plaintext.getMessage().length!=blocksize) 
				    		//MessageDialog.openError(shlCryptest, "Error", "The plaintext size is wrong");
				    	//verify size of public key
				    	/*System.out.println("key size = "+plaintext.removeSpaces(keyText.getText()).length());
				    	if(plaintext.removeSpaces(keyText.getText()).length()!=keylength*2) 
				    		MessageDialog.openError(shlCryptest, "Error", "The key length is wrong");*/
						//pad plaintext - default is set to Zeros padding/unpadding
			    		if(comboPadding.getText().equals("No Padding") || comboPadding.getText().equals("")) 
			    			ciphertext.setMessage(ct.encryptAsymmetric(plaintext.getMessage(), publicKey.getPublicKeyByte(), "RSA", "NoPadding"));
			    		else if(comboPadding.getText().equals("PKCS#1")) 
			    			ciphertext.setMessage(ct.encryptAsymmetric(plaintext.getMessage(), publicKey.getPublicKeyByte(), "RSA", "PKCS1Padding"));
			    		else if(comboPadding.getText().equals("OAEP With SHA-1 And MGF1")) 
			    			ciphertext.setMessage(ct.encryptAsymmetric(plaintext.getMessage(), publicKey.getPublicKeyByte(), "RSA", "OAEPWithSHA-1AndMGF1Padding"));
			    		else if(comboPadding.getText().equals("OAEP With SHA-256 And MGF1")){
			    			if(!comboCryptoAlg.getText().equals("RSA (512-bits)"))
			    					ciphertext.setMessage(ct.encryptAsymmetric(plaintext.getMessage(), publicKey.getPublicKeyByte(), "RSA", "OAEPWithSHA-256AndMGF1Padding"));
			    			else MessageDialog.openError(shlCryptest, "Error", "The key is too short for encryption using OAEPPadding with SHA-256 and MGF1SHA-1");
			    		}
					}
					
					
					//display ciphertext
					cipherText.setText(ciphertext.displayMessage());
				} catch (Exception e1) {
					MessageDialog.openError(shlCryptest, "Error", "Verify the key length, IV and encryption algorithm");
					e1.printStackTrace();
				}
				
			}
		});
		btnEncrypt.setToolTipText("Click to encrypt the plaintext.\r\nThe result is displayed on the right text box.\r\n\r\nNote: \r\nBefore you click on encrypt:\r\n- Verify that the key and IV length is correct.\r\n- Set the preferred plaintext and ciphertext type (HEX or ASCII).\r\n- Select the desired encryption Algorithm, Mode of operation\r\n  and Padding scheme.");
		btnEncrypt.setBounds(438, 399, 75, 25);
		btnEncrypt.setText("Encrypt >");
		
		/**
		 * Decrypt button press
		 */
		btnDecrypt = new Button(shlCryptest, SWT.NONE);
		btnDecrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					
					//Get crypto algorithm selected and initialise required values
					getCryptoAlgorithm();
			    	
			    	//create two instances of Message: plaintext and ciphertext
			    	//get type from combo box set default type to hex
			    	String typeP = comboTypePlainText.getText().toLowerCase();
			    	String typeC = comboTypeCipherText.getText().toLowerCase();
			    	if(typeP.length()==0) typeP = "hex";
			    	if(typeC.length()==0) typeC = "hex";
					
					Message ciphertext = new Message(cipherText.getText(),typeC);
					Message plaintext = new Message("",typeP);
			    	
			    	
					CryptoTool ct = new CryptoTool();
					
					//if Symmetric algorithm
					if(isSymmetric()){
						SymmetricKey decryptionKey = new SymmetricKey(keyText.getText(), "hex", size);
						Padding pad = new Padding(blocksize);
					
						//set IV to zeros
				    	if(plaintext.removeSpaces(ivText.getText()).length()==0 && !comboModes.getText().equals("ECB")){
				    		String iv = "";
				    		for(int i=0;i<blocksize;i++){
				    			if(i==blocksize-1) iv = iv+"00";
				    			else iv = iv+"00 ";
				    		}
				    		ivText.setText(iv);
				    	}
				    	
				    	//set IV value
				    	byte[] IV = plaintext.hexStringToByteArray(plaintext.removeSpaces(ivText.getText()));
				    	
		
				    	//if the IV length is wrong, display error message
				    	if(IV.length!=blocksize && !(comboModes.getText().equals("ECB"))) 
				    		MessageDialog.openError(shlCryptest, "Error", "IV length is wrong");
				    	//verify if a mode of operation is selected
				    	if(comboModes.getText().equals("")) 
				    		MessageDialog.openError(shlCryptest, "Error", "Select a Mode of Operation");
				    	//verify if there is ciphertext to encrypt
				    	if(cipherText.getText().equals("")) 
				    		MessageDialog.openError(shlCryptest, "Error", "Enter some ciphertext");
				    	//verify the keylength
				    	if(plaintext.removeSpaces(keyText.getText()).length()!=keylength*2) 
				    		MessageDialog.openError(shlCryptest, "Error", "The key length is wrong");		
				    	
						//decrypt ciphertext
						plaintext.setMessage(ct.decryptSymmetric(pad.paddingZeros(ciphertext.getMessage()), decryptionKey.getKey(), cry, comboModes.getText(), IV));
						
						//display padded plaintext
						paddedPlainText.setText(plaintext.byteArrayToHexString(plaintext.getMessage()));
						
						// remove padding - default is set to Zeros padding/unpadding
						if(comboPadding.getText().equals("Zeros (0)") || comboPadding.getText().equals("")) 
							plaintext.setMessage(pad.removePaddingZeros(plaintext.getMessage()));
			    		else if(comboPadding.getText().equals("ISO/IEC 7816-4")) 
			    			plaintext.setMessage(pad.removePadding7816_4(plaintext.getMessage()));
			    		else if(comboPadding.getText().equals("PKCS#7")) 
			    			plaintext.setMessage(pad.removePaddingPKCS7(plaintext.getMessage()));
			    		else if(comboPadding.getText().equals("ANSI X.923")) 
			    			plaintext.setMessage(pad.removePaddingANSI_X923(plaintext.getMessage()));
					}
					else if(isAsymmetric()){
						//get X509 encoded private key from key text
						AsymmetricKey privateKey = new AsymmetricKey(keyText.getText(), privateKeyText.getText(), "hex", size);
						//verify if there is plaintext to encrypt
				    	if(cipherText.getText().equals("")) 
				    		MessageDialog.openError(shlCryptest, "Error", "Enter some ciphertext");
				    	//verify keylength
				    	//if(plaintext.removeSpaces(keyText.getText()).length()!=keylength*2) 
				    		//MessageDialog.openError(shlCryptest, "Error", "The key length is wrong");
						//pad plaintext - default is set to Zeros padding/NoPadding
			    		if(comboPadding.getText().equals("No Padding") || comboPadding.getText().equals("")) 
			    			plaintext.setMessage(ct.decryptAsymmetric(ciphertext.getMessage(), privateKey.getPrivateKeyByte(), "RSA", "NoPadding"));
			    		else if(comboPadding.getText().equals("PKCS#1")) 
			    			plaintext.setMessage(ct.decryptAsymmetric(ciphertext.getMessage(), privateKey.getPrivateKeyByte(), "RSA", "PKCS1Padding"));
			    		else if(comboPadding.getText().equals("OAEP With SHA-1 And MGF1")) 
			    			plaintext.setMessage(ct.decryptAsymmetric(ciphertext.getMessage(), privateKey.getPrivateKeyByte(), "RSA", "OAEPWithSHA-1AndMGF1Padding"));
			    		else if(comboPadding.getText().equals("OAEP With SHA-256 And MGF1")){
			    			if(!comboCryptoAlg.getText().equals("RSA (512-bits)"))
			    				plaintext.setMessage(ct.decryptAsymmetric(ciphertext.getMessage(), privateKey.getPrivateKeyByte(), "RSA", "OAEPWithSHA-256AndMGF1Padding"));
			    			else MessageDialog.openError(shlCryptest, "Error", "The key is too short for encryption using OAEPPadding with SHA-256 and MGF1SHA-1");
			    		}
					}
						
					//display unpadded plaintext
					plainText.setText(plaintext.displayMessage());
				} catch (NoSuchAlgorithmException e1) {
					MessageDialog.openError(shlCryptest, "Error", "No Such Algorithm");
					e1.printStackTrace();
				} catch (Exception e2) {
					MessageDialog.openError(shlCryptest, "Error", "Bad Input");
					e2.printStackTrace();
				}
				
			}
		});
		btnDecrypt.setToolTipText("Click to decrypt the ciphertext.\r\nThe result is displayed on the left text box.\r\n\r\nNote:\r\nBefore you click on decrypt:\r\n- Verify that the key and IV length is correct.\r\n- Set the preferred plaintext and ciphertext type (HEX or ASCII).\r\n- Select the desired encryption Algorithm, Mode of operation\r\n  and Padding scheme.");
		btnDecrypt.setBounds(438, 450, 75, 25);
		btnDecrypt.setText("< Decrypt");
		
		btnGenerateIV = new Button(shlCryptest, SWT.NONE);
		btnGenerateIV.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
			    	if(comboCryptoAlg.getText().equals("DES (56-bits)") 
			    	   || comboCryptoAlg.getText().equals("TDES (112-bits)") 
			    	   || comboCryptoAlg.getText().equals("TDES (168-bits)")){
						SymmetricKey iv = new SymmetricKey("DES", 56);
						ivText.setText(iv.displayKey());
			    	}
			    	else if(comboCryptoAlg.getText().equals("AES (128-bits)")
			    			|| comboCryptoAlg.getText().equals("AES (192-bits)")
			    			|| comboCryptoAlg.getText().equals("AES (256-bits)")){
						SymmetricKey iv = new SymmetricKey("AES", 128);
						ivText.setText(iv.displayKey());
			    	}
			    	else{
			    		ivText.setEditable(false);
			    		MessageDialog.openError(shlCryptest, "Error", "IV is not applicable for this encryption algorithm");
			    		//Select an encryption Algorithm");
			    	}
				} catch (NoSuchAlgorithmException e1) {
					MessageDialog.openError(shlCryptest, "Error", "No Such Algorithm");
				}
			}
		});
		btnGenerateIV.setToolTipText("Generate a random initialisation value (IV).\r\nFirst select the desired encryption Algorithm,\r\nthen click.");
		btnGenerateIV.setText("Generate IV");
		btnGenerateIV.setBounds(485, 128, 81, 25);
		
		
		
		btnConvertPlaintext = new Button(shlCryptest, SWT.NONE);
		btnConvertPlaintext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(comboTypePlainText.getText().equals("ASCII")){
					Message plaintext = new Message(plainText.getText(),"ascii");
					plaintext.convertToHex();
					plainText.setText(plaintext.displayMessage());
					comboTypePlainText.setText("HEX");
				}
				else{
					try{
						Message plaintext = new Message(plainText.getText(),"hex");
						plaintext.convertToASCII();
						plainText.setText(plaintext.displayMessage());
						comboTypePlainText.setText("ASCII");
					}
					catch(Exception e1){
						MessageDialog.openError(shlCryptest, "Error", "The message contains non HEX characters.\n\n\nNote:\nHex characters can only be between 0 to 9 and A to F");
					}
				}
			}
		});
		btnConvertPlaintext.setToolTipText("Convert the plaintext from HEX/ASCII to ASCII/HEX.\r\nSelect text type entered in the text box, click to convert \r\nto the other type.");
		btnConvertPlaintext.setText("Convert to HEX/ASCII");
		btnConvertPlaintext.setBounds(235, 530, 133, 25);
		
		btnConvertCiphertext = new Button(shlCryptest, SWT.NONE);
		btnConvertCiphertext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(comboTypeCipherText.getText().equals("ASCII")){
					Message ciphertext = new Message(cipherText.getText(),"ascii");
					ciphertext.convertToHex();
					cipherText.setText(ciphertext.displayMessage());
					comboTypeCipherText.setText("HEX");
				}
				else{
					try{
						Message ciphertext = new Message(cipherText.getText(),"hex");
						ciphertext.convertToASCII();
						cipherText.setText(ciphertext.displayMessage());
						comboTypeCipherText.setText("ASCII");						
					}
					catch(Exception e1){
						MessageDialog.openError(shlCryptest, "Error", "The message contains non HEX characters.\n\n\nNote:\nHex characters can only be between 0 to 9 and A to F");
						e1.printStackTrace();
					}
				}
			}
		});
		btnConvertCiphertext.setToolTipText("Convert the ciphertext from HEX/ASCII to ASCII/HEX.\r\nSelect text type entered in the text box, click to convert \r\nto the other type.");
		btnConvertCiphertext.setText("Convert to HEX/ASCII");
		btnConvertCiphertext.setBounds(760, 527, 133, 25);
		
		btnLoadKeyPair = new Button(shlCryptest, SWT.NONE);
		btnLoadKeyPair.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//try {
					if(isAsymmetric()){
						MessageDialog.openError(shlCryptest, "Warning", "Sorry, feature not yet included!");
						/*fileChooser = new JFileChooser();
						AsymmetricKey ak = new AsymmetricKey();
						if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
							if(comboAsymmetricKeyType.getText().equals("Public Key")){
								System.out.println("----------LOAD----------");
								ak.loadPublicKey(fileChooser.getSelectedFile().getPath(), "RSA");
								keyText.setText(ak.displayPublicKey());
							}
							else if(comboAsymmetricKeyType.getText().equals("Private Key")){
								ak.loadPrivateKey(fileChooser.getSelectedFile().getPath(), "RSA");
								privateKeyText.setText(ak.displayPrivateKey());
							}
						}*/
					}
					else MessageDialog.openError(shlCryptest, "Error", "Select an Asymmetric encryption Algorithm");
				/*}catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			}
					
		});
		btnLoadKeyPair.setToolTipText("Generate a random key. \r\nFirst select the desired encryption Algorithm,\r\nthen click.");
		btnLoadKeyPair.setText("Load");
		btnLoadKeyPair.setBounds(733, 309, 51, 25);
	}
	
	/**
	 * initialise symmetric crypto GUI options
	 */
	public void initialiseSymmetric(){
		lblPrivateKey.setBounds(594, 171, 186, 18);
		lblPrivateKey.setText("Private Key - Not applicable");
		privateKeyText.setEditable(false);
		comboAsymmetricKeyType.setEnabled(false);
		privateKeyText.setText("");
		//ivText.setText("");
		ivText.setEditable(true);
		keyText.setText("");
		comboPadding.setItems(symmetricPadding);
		comboPadding.select(0);
		comboModes.setEnabled(true);
		comboModes.select(0);
		
		//place button near key text and rename
		btnGenerateKey.setBounds(786, 166, 109, 25);
		btnGenerateKey.setText("Generate Key");
		
	}
	
	/**
	 * initialise asymmetric crypto GUI options
	 */
	public void initialiseAsymmetric(int size){
		privateKeyText.setEditable(true);
		comboAsymmetricKeyType.setEnabled(true);
		privateKeyText.setText("");
		lblIv.setText("IV - Not required");
		ivText.setText("");
		ivText.setEditable(false);
		keyText.setText("");
		comboPadding.setItems(rsaPadding);
		comboPadding.select(0);
		comboModes.select(0);
		comboModes.setEnabled(false);
		lblKey.setText("Public Key - the key length must be "+size+" bytes");
		lblPrivateKey.setText("Private Key - the key length must be "+size+" bytes");
		lblPlaintext.setText("Plaintext - size must be less than "+size+" bytes");
		lblCiphertext.setText("Ciphertext - size cannot be larger than "+size+" bytes");
		paddedPlainText.setText("");
		
		//place button near private key text and rename
		btnGenerateKey.setBounds(786, 309, 109, 25);
		btnGenerateKey.setText("Generate Key Pair");
		lblPrivateKey.setBounds(594, 171, 301, 18);
	}
	
	/**
	 * checks if the crypto selected is symmetric
	 * @return true if the crypto selected is symmetric
	 */
	public boolean isSymmetric(){
		if(comboCryptoAlg.getText().equals("DES (56-bits)") || comboCryptoAlg.getText().equals("TDES (112-bits)") || comboCryptoAlg.getText().equals("TDES (168-bits)") || 
				comboCryptoAlg.getText().equals("AES (128-bits)") || comboCryptoAlg.getText().equals("AES (192-bits)") || comboCryptoAlg.getText().equals("AES (256-bits)"))
			return true;
		else return false;
	}
	
	/**
	 * checks if the crypto selected is asymmetric
	 * @return true if the crypto selected is asymmetric
	 */
	public boolean isAsymmetric(){
		if(comboCryptoAlg.getText().equals("RSA (512-bits)") || comboCryptoAlg.getText().equals("RSA (1024-bits)") || comboCryptoAlg.getText().equals("RSA (2048-bits)") || 
				comboCryptoAlg.getText().equals("RSA (3072-bits)") || comboCryptoAlg.getText().equals("RSA (4096-bits)"))
			return true;
		else return false;
	}
	
	/**
	 * Generate key pair
	 * @param size key size
	 * @throws NoSuchAlgorithmException
	 */
	public void generateAsymmetricKey(int size, String algorithm) throws NoSuchAlgorithmException{
		aKey = new AsymmetricKey(size, algorithm);
		keyText.setText(aKey.displayPublicKey());
		privateKeyText.setText(aKey.displayPrivateKey());
	}
	
	/**
	 * initialises the encryption algorithm from the selection in the GUI
	 */
	public void getCryptoAlgorithm(){
		if(comboCryptoAlg.getText().equals("DES (56-bits)")){ 
    		cry = "DES";
    		size = 56;
    		blocksize = 8;
    		keylength=8;
    	}		
    	else if(comboCryptoAlg.getText().equals("TDES (112-bits)")){
    		cry = "DESede";
    		size = 112;
    		blocksize = 8;
    		keylength=16;
    	}
    	else if(comboCryptoAlg.getText().equals("TDES (168-bits)")){
    		cry = "DESede";
    		size = 168;
    		blocksize = 8;
    		keylength=24;
    	}
    	else if(comboCryptoAlg.getText().equals("AES (128-bits)")){
    		cry = "AES";
    		size = 128;
    		blocksize = 16;
    		keylength=16;
    	}
    	else if(comboCryptoAlg.getText().equals("AES (192-bits)")){
    		cry = "AES";
    		size = 192;
    		blocksize = 16;
    		keylength=24;
    	}
    	else if(comboCryptoAlg.getText().equals("AES (256-bits)")){
    		cry = "AES";
    		size = 256;
    		blocksize = 16;
    		keylength=32;
    	}
    	else if(comboCryptoAlg.getText().equals("RSA (512-bits)")){
    		cry = "RSA";
    		size = 512;
    		blocksize = 64;
    		keylength=64;
    	}
    	else if(comboCryptoAlg.getText().equals("RSA (1024-bits)")){
    		cry = "RSA";
    		size = 1024;
    		blocksize = 128;
    		keylength=128;
    	}
    	else if(comboCryptoAlg.getText().equals("RSA (2048-bits)")){
    		cry = "RSA";
    		size = 2048;
    		blocksize = 256;
    		keylength=256;
    	}
    	else if(comboCryptoAlg.getText().equals("RSA (3072-bits)")){
    		cry = "RSA";
    		size = 3072;
    		blocksize = 384;
    		keylength=384;
    	}
    	else if(comboCryptoAlg.getText().equals("RSA (4096-bits)")){
    		cry = "RSA";
    		size = 4096;
    		blocksize = 512;
    		keylength=512;
    	}
    	else{
    		MessageDialog.openError(shlCryptest, "Error", "Select an encryption Algorithm");
    	}
	}
}
