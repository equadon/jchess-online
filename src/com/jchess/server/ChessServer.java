package com.jchess.server;

import com.jchess.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class ChessServer {
    private static final Logger LOG = Logger.getLogger(ChessServer.class.getName());

    private int port;
    private boolean running;
    private ServerSocket serverSocket;

    public ChessServer(int port) {
        this.port = port;
        running = true;
    }

    public void start() throws IOException {
        LOG.info("Starting the chess server on port: " + port);
        serverSocket = new ServerSocket(port);
        LOG.info("Waiting for clients to connect...");

        while (running) {
            ServerClient client = new ServerClient(this, serverSocket.accept());
            new Thread(client).start();
        }
    }

    public static void main(String[] args) {
        ChessServer server = new ChessServer(Config.SERVER_PORT);

        try {
            server.start();
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        }
    }
}
