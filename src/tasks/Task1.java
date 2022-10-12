package tasks;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.security.SecureRandom;

public class Task1 {

	public static final int AES_KEY_SIZE = 128; // in bits
	public static final int GCM_IV_SIZE = 92; // in bits
	public static final int GCM_TAG_SIZE = 128; // in bits

	public static void main(String[] args) throws Exception {
		System.out.println("\n");
        System.out.println(generateSecretKey());
	}

	public static SecretKey generateSecretKey() throws Exception {
		KeyGenerator key = KeyGenerator.getInstance("AES");
		key.init(AES_KEY_SIZE);
		return key.generateKey();
	}

	/* must use SecureRandom class*/
	public static void generateRandomIV(byte [] ivBytes) throws Exception {
		SecureRandom random = SecureRandom.getInstanceStrong();
		random.nextBytes(ivBytes);
	}

	public static void encryptFileWithAES(File out, SecretKey key, byte [] ivBytes, File in) throws Exception {
		SecretKey key = generateSecretKey();
	}

	public static void decryptFileWithAES(File out, SecretKey aesKey, byte[] ivBytes, File in) throws Exception {

	}
}
