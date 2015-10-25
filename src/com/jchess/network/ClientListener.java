package com.jchess.network;

import com.jchess.Config;
import com.jchess.exceptions.JCUnknownPacketException;
import com.jchess.util.crypto.AsymmetricCrypto;
import com.jchess.util.crypto.SymmetricCrypto;
import org.bouncycastle.crypto.CryptoException;

import java.io.*;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.logging.Logger;

public class ClientListener {
    private static final Logger LOG = Logger.getLogger(ClientListener.class.getName());

    protected AsymmetricCrypto asymmetricCrypto;
    protected SymmetricCrypto crypto;

    private Socket socket;
    protected DataInputStream input;
    protected DataOutputStream output;

    protected PublicKey publicKey;

    private boolean running;

    public ClientListener(Socket socket) throws IOException {
        this.socket = socket;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        if (Config.ENCRYPTPED_PACKETS) {
            asymmetricCrypto = new AsymmetricCrypto();
            publicKey = asymmetricCrypto.generateKeys();
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

            if (Config.ENCRYPTPED_PACKETS) {
                if (packet.getOpcode() == OpCode.HANDSHAKE)
                    bytes = asymmetricCrypto.encrypt(bytes, publicKey, 4);
                else if (packet.getOpcode() != OpCode.HANDSHAKE_INIT)
                    bytes = crypto.encrypt(packet.getBytes(), 4);
            }

            output.write(bytes);

            LOG.info("Sent packet: " + Arrays.toString(packet.getBytes()) + " (" + packet.getOpcode() + ")");
            LOG.info("  encrypted: " + Arrays.toString(bytes) + " (" + packet.getOpcode() + ")");
            output.flush();
        } catch (IOException ioe) {
            LOG.warning(ioe.getMessage());
        } catch (CryptoException ce) {
            LOG.severe(ce.getMessage());
        } catch (Exception e) {
            LOG.severe(e.getMessage());
        }
    }

    public void receive(PacketManager handler) throws IOException, JCUnknownPacketException {
        int opId = input.readShort();
        OpCode opcode = OpCode.get(opId);

        if (opcode == null)
            throw new JCUnknownPacketException("Unknown packet: " + opId);

        int length = input.readUnsignedShort();
        byte[] payload = new byte[length];
        input.read(payload, 0, length);

        LOG.info("Received packet (encrypted): " + opcode + ", length: " + length + ", payload: " + Arrays.toString(payload));

        try {
            byte[] decrypted;

            if (Config.ENCRYPTPED_PACKETS) {
                if (opcode == OpCode.HANDSHAKE)
                    decrypted = asymmetricCrypto.decrypt(payload);
                else if (opcode != OpCode.HANDSHAKE_INIT)
                    decrypted = crypto.decrypt(payload);
                else
                    decrypted = payload;
                length = decrypted.length;
            } else {
                decrypted = payload;
            }

            LOG.info("Received packet (decrypted): " + opcode + ", length: " + length + ", payload: " + Arrays.toString(decrypted));

            handler.manage(opcode, length, decrypted);
        } catch (CryptoException ce) {
            LOG.severe(ce.getMessage());
        } catch (Exception e) {
            LOG.severe(e.getMessage());
        }
    }
}
