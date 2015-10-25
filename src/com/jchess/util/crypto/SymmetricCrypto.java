package com.jchess.util.crypto;

import com.jchess.network.packets.auth.AuthRequest;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class SymmetricCrypto {
    private static final Logger LOG = Logger.getLogger(SymmetricCrypto.class.getName());

    private static final int MAC_SIZE = 128;
    public static final int KEY_SIZE = 32;
    public static final int NOUNCE_SIZE = 12;

    private static final SecureRandom RANDOM = new SecureRandom();

    private BlockCipher engine;
    private AEADBlockCipher encryptCipher;
    private AEADBlockCipher decryptCipher;

    private byte[] key;
    private byte[] nounce;

    public SymmetricCrypto() {
        this.key = new byte[KEY_SIZE];
        this.nounce = new byte[NOUNCE_SIZE];

        RANDOM.nextBytes(this.key);
        RANDOM.nextBytes(this.nounce);

        engine = new AESEngine();
        encryptCipher = new GCMBlockCipher(engine);
        decryptCipher = new GCMBlockCipher(engine);

        encryptCipher.init(true, getParams());
        decryptCipher.init(false, getParams());
    }

    public byte[] getKey() {
        return key;
    }

    public byte[] getNounce() {
        return nounce;
    }

    public void setKeyNounce(byte[] key, byte[] nounce) {
        this.key = key;
        this.nounce = nounce;

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

        updateLength(cipherText);

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

    private void updateLength(byte[] bytes) {
        ByteBuffer.wrap(bytes).putShort(2, (short) (bytes.length - 4));
    }

    public static void main(String[] args) {
        AuthRequest p = new AuthRequest("user", "pass");
        System.out.println("Original: " + Arrays.toString(p.getBytes()));
    }
}
