package tasks;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECPrivateKeySpec;

public class Task3 {

	public static void main(String[] args) throws Exception {
		KeyPair aliceKeyPair = aliceGenerateDHKeypair();
		var bobKeYPair = bobGenerateDHKeypair(aliceKeyPair.getPublic().getEncoded());
	}

	public static KeyPair aliceGenerateDHKeypair() throws Exception{
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DH");
		keyGenerator.initialize(1024);
		return keyGenerator.genKeyPair();
	}

	public static KeyPair bobGenerateDHKeypair(byte[] AlicePublicKeyBytes) throws Exception{

		Security.addProvider(new BouncyCastleProvider());
		KeyFactory factory = KeyFactory.getInstance("ECDSA", "BC");
		ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("prime256v1");
		ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(new BigInteger(AlicePublicKeyBytes), spec);
		PrivateKey privateKey = factory.generatePrivate(ecPrivateKeySpec);

		//KeyPair keyPart = new KeyPair(AlicePublicKeyBytes, privateKey.getEncoded());

		return null;
	}

	public static byte[] aliceGenerateSharedSecret(byte[] BobPublicKeyBytes, KeyPair AliceKeys) throws Exception {
		return null;
	}

	public static byte[] bobGenerateSharedSecret(byte[] AlicePublicKeyBytes, KeyPair BobKeys) throws Exception {
		return null;
	}
}
