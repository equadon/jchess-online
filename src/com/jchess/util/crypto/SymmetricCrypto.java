package com.jchess.util.crypto;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class SymmetricCrypto {
    private static final Logger LOG = Logger.getLogger(SymmetricCrypto.class.getName());
    private static final int MAC_SIZE = 128;
    private static final SecureRandom RANDOM = new SecureRandom();

    private BlockCipher engine;
    private AEADBlockCipher encryptCipher;
    private AEADBlockCipher decryptCipher;

    private byte[] key;
    private byte[] nounce;

    public SymmetricCrypto() {
        engine = new AESEngine();
        encryptCipher = new GCMBlockCipher(engine);
        decryptCipher = new GCMBlockCipher(engine);

        key = new byte[32];
        nounce = new byte[12];

        RANDOM.nextBytes(key);
        RANDOM.nextBytes(nounce);

        encryptCipher.init(true, getParams());
        decryptCipher.init(false, getParams());
    }

    private AEADParameters getParams() {
        return new AEADParameters(new KeyParameter(key), MAC_SIZE, nounce);
    }

    public String encrypt(String plainText) throws CryptoException, UnsupportedEncodingException {
        byte[] cipherText = encrypt(plainText.getBytes("UTF-8"));

        return Base64.toBase64String(cipherText);
    }

    public byte[] encrypt(byte[] plainText) throws CryptoException {
        return encrypt(plainText, 0);
    }

    public byte[] encrypt(byte[] plainText, int offset) throws CryptoException {
        byte[] cipherText = Arrays.copyOf(plainText, encryptCipher.getOutputSize(plainText.length));

        int outputLen = encryptCipher.processBytes(plainText, offset, plainText.length - offset, cipherText, offset);

        encryptCipher.doFinal(cipherText, outputLen + offset);

        return cipherText;
    }

    public String decrypt(String encodedCipherText) throws CryptoException, UnsupportedEncodingException {
        byte[] cipherText = Base64.decode(encodedCipherText);

        return new String(decrypt(cipherText));
    }

    public byte[] decrypt(byte[] cipherText) throws CryptoException {
        return decrypt(cipherText, 0);
    }

    public byte[] decrypt(byte[] cipherText, int offset) throws CryptoException {
        byte[] plainText = Arrays.copyOf(cipherText, decryptCipher.getOutputSize(cipherText.length));

        int outputLen = decryptCipher.processBytes(cipherText, offset, cipherText.length - offset, cipherText, offset);

        decryptCipher.doFinal(plainText, outputLen + offset);

        return plainText;
    }
}
