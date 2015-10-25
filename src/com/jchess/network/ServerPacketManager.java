package com.jchess.network;

import com.jchess.network.packets.auth.AuthRequest;
import com.jchess.server.handlers.AuthHandler;

public class ServerPacketManager implements PacketManager {
    @Override
    public void manage(OpCode opcode, int length, byte[] payload) {
        switch (opcode) {
            case AUTH_INIT:
                new AuthHandler(new AuthRequest(opcode, length, payload));
                break;
        }
    }
}
