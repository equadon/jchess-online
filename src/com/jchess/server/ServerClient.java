package com.jchess.server;

import com.jchess.exceptions.JChessUnknownPacketException;
import com.jchess.network.ClientListener;
import com.jchess.network.PacketManager;
import com.jchess.network.ServerPacketManager;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerClient extends ClientListener implements Runnable {
    private static final Logger LOG = Logger.getLogger(ServerClient.class.getName());
    private ChessServer server;

    private PacketManager packetManager;

    public ServerClient(ChessServer server, Socket socket) throws IOException {
        super(socket);
        this.server = server;

        packetManager = new ServerPacketManager();

        LOG.info("Client connected: " + socket.getInetAddress().getCanonicalHostName() + ":" + socket.getPort());
    }

    @Override
    public void run() {
        while (isRunning()) {
            try {
                receive(packetManager);
            } catch (JChessUnknownPacketException pe) {
                LOG.warning(pe.getMessage());
            } catch (IOException ioe) {
                LOG.severe(ioe.getMessage());
                shutdown();
            }
        }
    }
}
