package com.jchess.server;

import com.jchess.Config;
import com.jchess.exceptions.JCUnknownPacketException;
import com.jchess.network.ClientListener;
import com.jchess.network.PacketManager;
import com.jchess.network.ServerPacketManager;
import com.jchess.network.packets.auth.Handshake;

import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.logging.Logger;

public class ServerClient extends ClientListener implements Runnable {
    private static final Logger LOG = Logger.getLogger(ServerClient.class.getName());
    private ChessServer server;

    private PacketManager packetManager;

    public ServerClient(ChessServer server, Socket socket) throws IOException {
        super(socket);
        this.server = server;

        packetManager = new ServerPacketManager(this);

        LOG.info("Client connected: " + socket.getInetAddress().getCanonicalHostName() + ":" + socket.getPort());
    }

    @Override
    public void run() {
        while (isRunning()) {
            try {
                receive(packetManager);
            } catch (JCUnknownPacketException pe) {
                LOG.warning(pe.getMessage());
            } catch (IOException ioe) {
                LOG.severe(ioe.getMessage());
                shutdown();
            }
        }
    }

    public void updatePublicKey(PublicKey publicKey) {
        byte[] key;
        byte[] nounce;

        if (Config.ENCRYPTPED_PACKETS) {
            key = crypto.getKey();
            nounce = crypto.getNounce();
            this.publicKey = publicKey;
        } else {
            key = new byte[1];
            nounce = new byte[1];
        }

        Handshake handshake = new Handshake(Handshake.HandshakeStatus.OK, key, nounce);
        send(handshake);
    }
}
