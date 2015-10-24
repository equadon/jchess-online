package com.jchess.network.packets.auth;

import com.jchess.network.GamePacket;
import com.jchess.network.OpCode;

public class RequestAuth extends GamePacket {
    public final String username;
    public final String password;

    public RequestAuth(String username, String password) {
        super(OpCode.AUTH_INIT);

        add(username);
        add(password);

        this.username = username;
        this.password = password;
    }

    public RequestAuth(OpCode opcode, int length, byte[] payload) {
        super(opcode, length, payload);

        username = readString();
        password = readString();
    }
}
