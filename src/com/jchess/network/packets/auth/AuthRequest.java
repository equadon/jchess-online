package com.jchess.network.packets.auth;

import com.jchess.network.GamePacket;
import com.jchess.network.OpCode;
import com.jchess.network.ReceivablePacket;

public class AuthRequest extends GamePacket {
    public final String username;
    public final String password;

    public AuthRequest(String username, String password) {
        super(OpCode.AUTH_INIT);

        add(username);
        add(password);

        this.username = username;
        this.password = password;
    }

    public AuthRequest(ReceivablePacket packet) {
        super(packet);

        this.username = readString();
        this.password = readString();
    }

    public AuthRequest(OpCode opcode, int length, byte[] payload) {
        super(opcode, length, payload);

        this.username = readString();
        this.password = readString();
    }
}
