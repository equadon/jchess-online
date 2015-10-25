package com.jchess.network.packets.auth;

import com.jchess.network.GamePacket;
import com.jchess.network.OpCode;
import com.jchess.network.ReceivablePacket;
import com.jchess.util.crypto.AsymmetricCrypto;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class HandshakeInit extends GamePacket {
    private PublicKey publicKey;

    public HandshakeInit(PublicKey publicKey) {
        super(OpCode.HANDSHAKE_INIT);

        add(publicKey.getEncoded());

        this.publicKey = publicKey;
    }

    public HandshakeInit(ReceivablePacket packet) {
        super(packet);

        byte[] bytes = readBytes(AsymmetricCrypto.KEY_SIZE);

        try {
            this.publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public HandshakeInit(OpCode opcode, int length, byte[] payload) {
        super(opcode, length, payload);

        byte[] bytes = readBytes(AsymmetricCrypto.KEY_SIZE);

        try {
            this.publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
