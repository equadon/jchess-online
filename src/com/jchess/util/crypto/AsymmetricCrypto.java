package com.jchess.util.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class AsymmetricCrypto {
    private static final Logger LOG = Logger.getLogger(AsymmetricCrypto.class.getName());
    private static final String ALGORITHM = "RSA";
    public static final int KEY_SIZE = 1024;

    private KeyPair keyPair;

    public AsymmetricCrypto() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public PublicKey generateKeys() {
        KeyPairGenerator keyGen = null;

        try {
            keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            LOG.severe(e.getMessage());
        }

        keyGen.initialize(KEY_SIZE);

        keyPair = keyGen.generateKeyPair();

        return keyPair.getPublic();
    }

    public String encrypt(String plainText, PublicKey key) throws UnsupportedEncodingException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        byte[] cipherText = encrypt(plainText.getBytes("UTF-8"), key);

        return Base64.toBase64String(cipherText);
    }

    public byte[] encrypt(byte[] plainText, PublicKey key) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return encrypt(plainText, key, 0);
    }

    public byte[] encrypt(byte[] plainText, PublicKey key, int offset) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] bytes = Arrays.copyOfRange(plainText, offset, plainText.length);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        bytes = cipher.doFinal(bytes);

        byte[] encrypted = new byte[bytes.length + offset];

        for (int i = 0; i < offset; i++)
            encrypted[i] = plainText[i];
        for (int i = 0; i < bytes.length; i++)
            encrypted[offset + i] = bytes[i];

        if (offset != 0)
            updateLength(encrypted);

        return encrypted;
    }

    public String decrypt(String encodedCipherText) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] cipherText = Base64.decode(encodedCipherText);
        byte[] plainText = decrypt(cipherText);

        return new String(plainText);
    }

    public byte[] decrypt(byte[] cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

        return cipher.doFinal(cipherText);
    }

    private void updateLength(byte[] bytes) {
        ByteBuffer.wrap(bytes).putShort(2, (short) (bytes.length - 4));
    }
}
