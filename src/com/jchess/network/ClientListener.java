package com.jchess.network;

import com.jchess.Config;
import com.jchess.exceptions.JChessUnknownPacketException;
import com.jchess.util.crypto.AsymmetricCrypto;
import com.jchess.util.crypto.SymmetricCrypto;
import org.bouncycastle.crypto.CryptoException;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

public class ClientListener {
    private static final Logger LOG = Logger.getLogger(ClientListener.class.getName());

    private AsymmetricCrypto asymmetricCrypto;
    private SymmetricCrypto crypto;

    private Socket socket;
    protected DataInputStream input;
    protected DataOutputStream output;

    private boolean running;

    public ClientListener(Socket socket) throws IOException {
        this.socket = socket;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        if (Config.ENCRYPT_PACKETS) {
            asymmetricCrypto = new AsymmetricCrypto();
            crypto = new SymmetricCrypto();
        }

        running = true;
    }

    public boolean isRunning() {
        return running;
    }

    public void shutdown() {
        running = false;
    }

    public void send(SendablePacket packet) {
        try {
            byte[] bytes = packet.getBytes();
            if (Config.ENCRYPT_PACKETS)
                bytes = crypto.encrypt(packet.getBytes(), 4);
            output.write(bytes);

            LOG.info("Sent packet: " + Arrays.toString(packet.getBytes()) + " (" + packet.getOpcode() + ")");
            output.flush();
        } catch (IOException ioe) {
            LOG.warning(ioe.getMessage());
        } catch (CryptoException ce) {
            LOG.severe(ce.getMessage());
        }
    }

    public ReceivablePacket receive() throws IOException, JChessUnknownPacketException {
        int opId = input.readShort();
        OpCode opcode = OpCode.get(opId);

        if (opcode == null)
            throw new JChessUnknownPacketException("Unknown packet: " + opId);

        int length = input.readUnsignedShort();
        byte[] payload = new byte[length];
        input.read(payload, 0, length);

        ReceivablePacket packet = new GamePacket(opcode, length, payload);
        LOG.info("Received packet: " + Arrays.toString(packet.getBytes()) + " (" + packet.getOpcode() + ")");

        return null;
    }
}
