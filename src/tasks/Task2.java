package tasks;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class Task2 {

	public static void main(String[] args) throws Exception {

		// Question 1 et 2

		byte[] file1Hash = computeMD5Hashes(new File("src/main/resources/files/Task2_letter.ps"));
		System.out.print("Hash file 1 : ");
		for(byte c:file1Hash)
			System.out.print(c);
		System.out.println();

		byte[] file2Hash = computeMD5Hashes(new File("src/main/resources/files/Task2_order.ps"));
		System.out.print("Hash file 2 : ");
		for(byte c:file1Hash)
			System.out.print(c);
		System.out.println();

		// Question 3


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
		//Use SHA-1 algorithm
		MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");

		//SHA-1 checksum
		String shaChecksum = getFileChecksum(shaDigest, fileToHash);
	}

	/*use HMAC-SHA256*/
	public static byte[] computeHMAC(File fileToHashMac) throws Exception {
		return null;
	}

}

