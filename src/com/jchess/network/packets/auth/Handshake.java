package com.jchess.network.packets.auth;

import com.jchess.network.GamePacket;
import com.jchess.network.OpCode;
import com.jchess.network.ReceivablePacket;
import com.jchess.util.crypto.SymmetricCrypto;

public class Handshake extends GamePacket {
    public enum HandshakeStatus {
        OK, FAIL
    }

    public final HandshakeStatus status;
    public final byte[] key;
    public final byte[] nounce;

    public Handshake(HandshakeStatus status, byte[] key, byte[] nounce) {
        super(OpCode.HANDSHAKE);

        add((short) status.ordinal());
        add(key);
        add(nounce);

        this.status = status;
        this.key = key;
        this.nounce = nounce;
    }

    public Handshake(ReceivablePacket packet) {
        super(packet);

        this.status = HandshakeStatus.values()[readShort()];

        if (this.status == HandshakeStatus.OK) {
            this.key = readBytes(SymmetricCrypto.KEY_SIZE);
            this.nounce = readBytes(SymmetricCrypto.NOUNCE_SIZE);
        } else {
            this.key = null;
            this.nounce = null;
        }
    }

    public Handshake(OpCode opcode, int length, byte[] payload) {
        super(opcode, length, payload);

        this.status = HandshakeStatus.values()[readShort()];

        if (this.status == HandshakeStatus.OK) {
            this.key = readBytes(SymmetricCrypto.KEY_SIZE);
            this.nounce = readBytes(SymmetricCrypto.NOUNCE_SIZE);
        } else {
            this.key = null;
            this.nounce = null;
        }
    }
}
