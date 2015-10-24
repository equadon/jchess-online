package com.jchess.network.packets.auth;

import com.jchess.network.GamePacket;
import com.jchess.network.OpCode;

public class RequestAuth extends GamePacket {
    public final String username;
    public final String password;

    public RequestAuth(String username, String password) {
        super(OpCode.AUTH_INIT);

        this.username = username;
        this.password = password;
    }

    public RequestAuth(byte[] bytes) {
        super(bytes);

        this.username = readString();
        this.password = readString();
    }
}
