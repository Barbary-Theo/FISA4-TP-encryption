package tasks;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

public class Task1 {

	public static final int AES_KEY_SIZE = 128; // in bits
	public static final int GCM_IV_SIZE = 92; // in bits
	public static final int GCM_TAG_SIZE = 128; // in bits

	public static void main(String[] args) throws Exception {

		SecretKey key = generateSecretKey();

		byte [] ivBytes = new byte[GCM_IV_SIZE];
		generateRandomIV(ivBytes);

		File in = new File("src/main/resources/files/Task1_fileToEncrypt");
		File encrypted = new File("src/main/resources/files/Task1_fileToEncrypt_encrypted.txt");
		File decrypted = new File("src/main/resources/files/Task1_file_decrypted.txt");

		encryptFileWithAES(
				encrypted,
				key,
				new byte[GCM_IV_SIZE],
				in
		);

		decryptFileWithAES(
				decrypted,
				key,
				new byte[GCM_IV_SIZE],
				encrypted
		);

	}

	public static SecretKey generateSecretKey() throws Exception {
		// https://stackoverflow.com/questions/18228579/how-to-create-a-secure-random-aes-key-in-java
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(AES_KEY_SIZE);
		return keyGen.generateKey();
	}

	/* must use SecureRandom class*/
	public static void generateRandomIV(byte [] ivBytes) throws Exception {
		new SecureRandom().nextBytes(ivBytes);
	}

	public static void encryptFileWithAES(File out, SecretKey key, byte [] ivBytes, File in) throws Exception {

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		FileInputStream inputStream = new FileInputStream(in);
		FileOutputStream outputStream = new FileOutputStream(out);
		byte[] buffer = new byte[64];

		int bytesRead;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			byte[] output = cipher.update(buffer, 0, bytesRead);
			if (output != null) outputStream.write(output);
		}

		byte[] outputBytes = cipher.doFinal();
		if (outputBytes != null) outputStream.write(outputBytes);

		inputStream.close();
		outputStream.close();
	}

	public static void decryptFileWithAES(File out, SecretKey aesKey, byte[] ivBytes, File in) throws Exception {

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, aesKey);

		FileInputStream inputStream = new FileInputStream(in);
		FileOutputStream outputStream = new FileOutputStream(out);
		byte[] buffer = new byte[64];

		int bytesRead;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			byte[] output = cipher.update(buffer, 0, bytesRead);
			if (output != null) outputStream.write(output);
		}

		byte[] outputBytes = cipher.doFinal();

		if (outputBytes != null) outputStream.write(outputBytes);

		inputStream.close();
		outputStream.close();



	}
}
