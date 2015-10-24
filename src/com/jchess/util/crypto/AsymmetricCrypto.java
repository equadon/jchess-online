package com.jchess.util.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.util.logging.Logger;

public class AsymmetricCrypto {
    private static final Logger LOG = Logger.getLogger(AsymmetricCrypto.class.getName());
    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 1024;

    private KeyPair keyPair;

    public AsymmetricCrypto() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public PublicKey generateKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(KEY_SIZE);

        keyPair = keyGen.generateKeyPair();

        return keyPair.getPublic();
    }

    public String encrypt(String plainText, PublicKey key) throws Exception {
        byte[] cipherText = encrypt(plainText.getBytes("UTF-8"), key);

        return Base64.toBase64String(cipherText);
    }

    public byte[] encrypt(byte[] plainText, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(plainText);
    }

    public String decrypt(String encodedCipherText) throws Exception {
        byte[] cipherText = Base64.decode(encodedCipherText);
        byte[] plainText = decrypt(cipherText);

        return new String(plainText);
    }

    public byte[] decrypt(byte[] cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

        return cipher.doFinal(cipherText);
    }
}
