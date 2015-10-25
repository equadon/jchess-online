package com.jchess.network;

import com.jchess.Config;
import com.jchess.network.packets.auth.AuthRequest;
import com.jchess.network.packets.auth.HandshakeInit;
import com.jchess.server.ServerClient;
import com.jchess.server.handlers.AuthHandler;

import java.util.logging.Logger;

public class ServerPacketManager implements PacketManager {
    private static final Logger LOG = Logger.getLogger(ServerPacketManager.class.getName());
    private final ServerClient client;

    public ServerPacketManager(ServerClient client) {
        this.client = client;
    }

    @Override
    public void manage(OpCode opcode, int length, byte[] payload) {
        switch (opcode) {
            case HANDSHAKE_INIT:
                HandshakeInit handshakeInit = new HandshakeInit(opcode, length, payload);
                client.updatePublicKey(handshakeInit.getPublicKey());
                LOG.info("Updated public key!");
                break;

            case AUTH_INIT:
                new AuthHandler(new AuthRequest(opcode, length, payload));
                break;
        }
    }
}
