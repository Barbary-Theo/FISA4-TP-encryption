package tasks;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;

import static tasks.Utils.bytesToHex;

public class Task2 {

	public static void main(String[] args) throws Exception {

		// Question 1 et 2

		byte[] file1HashMD5 = computeMD5Hashes(new File("src/main/resources/files/Task2_letter.ps"));
		System.out.print("Hash MD5 file 1 : ");
		for(byte c:file1HashMD5)
			System.out.print(c);
		System.out.println();

		byte[] file2Hash = computeMD5Hashes(new File("src/main/resources/files/Task2_order.ps"));
		System.out.print("Hash MD5 file 2 : ");
		for(byte c:file1HashMD5)
			System.out.print(c);
		System.out.println();

		// Question 3 et 4

		byte[] file1HashSHA256 = computeSHA256Hashes(new File("src/main/resources/files/Task2_letter.ps"));
		System.out.print("Hash SHA-256 file 1 : ");
		for(byte c:file1HashSHA256)
			System.out.print(c);
		System.out.println();

		byte[] file2HashSHA256 = computeSHA256Hashes(new File("src/main/resources/files/Task2_order.ps"));
		System.out.print("Hash SHA-256 file 2 : ");
		for(byte c:file2HashSHA256)
			System.out.print(c);
		System.out.println();

		// Question 5 et 6


	}

	public static byte[] computeMD5Hashes(File fileToHash) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		FileInputStream fis = new FileInputStream(fileToHash);

		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}

		fis.close();

		byte[] bytes = digest.digest();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < bytes.length; i++) {

			sb.append(Integer
					.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString().getBytes();
	}

	public static byte[] computeSHA256Hashes(File fileToHash) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		FileInputStream fis = new FileInputStream(fileToHash);

		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}

		fis.close();

		byte[] bytes = digest.digest();

		StringBuilder sb = new StringBuilder();

		for (byte aByte : bytes) {

			sb.append(Integer
					.toString((aByte & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString().getBytes();
	}

	/*use HMAC-SHA256*/
	public static byte[] computeHMAC(File fileToHash) throws Exception {
		SecretKey key = Task1.generateSecretKey();
		Mac md = Mac.getInstance("HmacSHA256");
		md.init(key);

		FileInputStream fis = new FileInputStream(fileToHash);

		try {
			FileInputStream reader = new FileInputStream(fileToHash);
			byte[] allBytes = reader.readAllBytes();

			for(byte ele : allBytes ) {
				System.out.println("-> " + ele);
			}


		} catch (Exception e){
			System.out.println(e.toString());
		}

		byte[] contents = new byte[1024];
		int readSize;
		while ((readSize = in.read(contents)) != -1) {
			md.update(contents, 0, readSize);
		}
		byte[] hashValue = md.doFinal();


	}

}

