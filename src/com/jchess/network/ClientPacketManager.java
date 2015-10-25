package com.jchess.network;

import com.jchess.client.ChessClient;
import com.jchess.network.packets.auth.Handshake;

import java.util.logging.Logger;

public class ClientPacketManager implements PacketManager {
    private static final Logger LOG = Logger.getLogger(ClientPacketManager.class.getName());

    private ChessClient client;

    public ClientPacketManager(ChessClient client) {
        this.client = client;
    }

    @Override
    public void manage(OpCode opcode, int length, byte[] payload) {
        switch (opcode) {
            case HANDSHAKE:
                client.updateSymmetricKeys(new Handshake(opcode, length, payload));
                LOG.info("Updated symmetric keys!");
                break;
        }
    }
}
