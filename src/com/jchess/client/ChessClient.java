package com.jchess.client;

import com.jchess.Config;
import com.jchess.network.ClientListener;
import com.jchess.network.SendablePacket;
import com.jchess.network.packets.auth.AuthRequest;
import com.jchess.util.crypto.Utility;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ChessClient extends ClientListener {
    private static final Logger LOG = Logger.getLogger(ChessClient.class.getName());

    public ChessClient(String hostname, int port) throws IOException {
        super(new Socket(hostname, port));
        LOG.info("Connected to " + hostname + ":" + port);
    }

    public void run() {
        while (isRunning()) {
            Utility.readUserInput("Press <ENTER> to login...");
            SendablePacket packet = new AuthRequest("user", "pass");
            send(packet);
        }
    }

    public static void main(String[] args) {
        try {
            ChessClient client = new ChessClient("localhost", Config.SERVER_PORT);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
