# Cryptest - Encryption Test Tool

The tool is an encryption verification tool with different algorithms, modes of operation and padding schemes.

This is an old tool I developed a while ago. It's based on JDK v1.6. 
Please be advised that the tool might have a few bugs and feel free to fork and update it as you wish.

	1. Encryption Algorithms
		a. DES/TDES (56, 112, 168 bit keys)
		b. AES (128, 192, 256 bit keys) - AES 192 and 256 require different strong
						  crypto policy files to run
		c. RSA (512, 1204, 2048, 3072, 4096 bit keys)
	2. Mode of operations
		a. ECB
		b. CBC
		c. CFB
		d. OFB
	3. Padding schemes
		a. Zeros
		b. ISO/IEC 7816-4
		c. PKCS#7
		d. ANSI X.923