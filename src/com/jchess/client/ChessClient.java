package com.jchess.client;

import com.jchess.Config;
import com.jchess.exceptions.JCUnknownPacketException;
import com.jchess.network.ClientListener;
import com.jchess.network.ClientPacketManager;
import com.jchess.network.SendablePacket;
import com.jchess.network.packets.auth.AuthRequest;
import com.jchess.network.packets.auth.Handshake;
import com.jchess.network.packets.auth.HandshakeInit;
import com.jchess.util.crypto.Utility;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

public class ChessClient extends ClientListener {
    private static final Logger LOG = Logger.getLogger(ChessClient.class.getName());

    private ClientPacketManager packetManager;

    public ChessClient(String hostname, int port) throws IOException {
        super(new Socket(hostname, port));
        LOG.info("Connected to " + hostname + ":" + port);

        packetManager = new ClientPacketManager(this);
    }

    public void run() {
        String userInput;
        SendablePacket packet;
        boolean keepAsking = true;

        while (isRunning()) {
            keepAsking = true;
            while (keepAsking) {
                userInput = Utility.readUserInput("> ");

                switch (userInput) {
                    case "handshake":
                        packet = new HandshakeInit(publicKey);
                        send(packet);
                        keepAsking = false;
                        break;

                    case "auth":
                        packet = new AuthRequest("user", "pass");
                        send(packet);
                        keepAsking = false;
                        break;
                }
            }

            // Wait for response
            LOG.info("Waiting for response...");
            try {
                receive(packetManager);
            } catch (IOException e) {
                LOG.severe(e.getMessage());
            } catch (JCUnknownPacketException e) {
                LOG.severe(e.getMessage());
            }
        }
    }

    public void updateSymmetricKeys(Handshake packet) {
        LOG.info("Key (before): " + Arrays.toString(crypto.getKey()));
        LOG.info("Nounce (before): " + Arrays.toString(crypto.getNounce()));

        crypto.setKeyNounce(packet.key, packet.nounce);

        LOG.info("Key (after): " + Arrays.toString(crypto.getKey()));
        LOG.info("Nounce (after): " + Arrays.toString(crypto.getNounce()));
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
