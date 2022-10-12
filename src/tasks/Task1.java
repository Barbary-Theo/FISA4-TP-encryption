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

		encryptFileWithAES(
				new File("src/main/resources/files/Task1_fileToEncrypt_encrypted.txt"),
				key,
				new byte[GCM_IV_SIZE],
				new File("src/main/resources/files/Task1_fileToEncrypt")
		);

		decryptFileWithAES(
				new File("src/main/resources/files/Task1_file_decrypted.txt"),
				key,
				new byte[GCM_IV_SIZE],
				new File("src/main/resources/files/Task1_fileToEncrypt_encrypted.txt")
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
		SecureRandom random = SecureRandom.getInstanceStrong();
		random.nextBytes(ivBytes);
	}

	public static void encryptFileWithAES(File out, SecretKey key, byte [] ivBytes, File in) throws Exception {

		generateRandomIV(ivBytes);
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_SIZE, ivBytes);
		cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);
		InputStream reader = new FileInputStream(in.getPath());

		byte[] cipherText = cipher.doFinal(reader.readAllBytes());

		FileOutputStream writer = new FileOutputStream(out);
		writer.write(cipherText);

		writer.close();
		reader.close();
	}

	public static void decryptFileWithAES(File out, SecretKey aesKey, byte[] ivBytes, File in) throws Exception {

		File file = new File(in.getPath());
		InputStream reader = new FileInputStream(file);
		ByteBuffer byteBuffer = ByteBuffer.wrap(reader.readAllBytes());
		byteBuffer.get(GCM_IV_SIZE);

		//get the rest of encrypted data
		byte[] cipherBytes = new byte[byteBuffer.remaining()];
		byteBuffer.get(cipherBytes);

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_SIZE, ivBytes);
		cipher.init(Cipher.DECRYPT_MODE, aesKey, gcmParameterSpec);

		byte[] cipherText = cipher.doFinal(cipherBytes);

		FileOutputStream writer = new FileOutputStream(out);
		writer.write(cipherText);

		writer.close();
		reader.close();
	}
}
