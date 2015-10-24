package com.jchess.network;

import com.jchess.Config;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

public abstract class GamePacket implements ReceivablePacket, SendablePacket {
    private static final Logger LOG = Logger.getLogger(GamePacket.class.getName());
    private static byte NULL = 0x00;

    public final OpCode opcode;

    private byte[] bytes;
    private int seek = 0;

    /**
     * Construct a new game packet with the provided opcode.
     * @param opcode opcode for created packet
     */
    public GamePacket(OpCode opcode) {
        this.bytes = new byte[2];
        this.opcode = opcode;

        addOpcode(opcode);
    }

    /**
     * Construct a new game packet from a byte array.
     * @param bytes packet bytes
     */
    public GamePacket(byte[] bytes) {
        this.bytes = bytes;
        this.opcode = readOpcode();
    }

    /**
     * SendablePacket overrides
     */
    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public void addOpcode(OpCode opcode) {
        // Opcode should only be added in constructor so we assume that the size is at least 2
        seek = 0;
        add((short) opcode.opcode);
    }

    @Override
    public final void add(byte value) {
        ByteBuffer.allocate(bytes.length + 1).put(bytes).put(value);
    }

    @Override
    public final void add(byte[] values) {
        ByteBuffer.allocate(bytes.length + values.length).put(bytes);
    }

    @Override
    public final void add(short value) {
        bytes = ByteBuffer.allocate(bytes.length + 2).put(bytes).putShort(value).array();
    }

    @Override
    public final void add(int value) {
        bytes = ByteBuffer.allocate(bytes.length + 4).put(bytes).putInt(value).array();
    }

    @Override
    public final void add(long value) {
        bytes = ByteBuffer.allocate(bytes.length + 8).put(bytes).putLong(value).array();
    }

    @Override
    public final void add(float value) {
        bytes = ByteBuffer.allocate(bytes.length + 4).put(bytes).putFloat(value).array();
    }

    @Override
    public final void add(double value) {
        bytes = ByteBuffer.allocate(bytes.length + 8).put(bytes).putDouble(value).array();
    }

    @Override
    public final void add(char value) {
        bytes = ByteBuffer.allocate(bytes.length + 8).put(bytes).putChar(value).array();
    }

    @Override
    public final void add(String value) {
        bytes = ByteBuffer.allocate(bytes.length + value.length() + 1).put(bytes).put(value.getBytes()).put(NULL).array();
    }

    /**
     * ReceivablePacket overrides
     */
    @Override
    public final OpCode readOpcode() {
        return OpCode.get(readShort());
    }

    @Override
    public final short readShort() {
        seek += 2;
        return ByteBuffer.wrap(bytes, seek - 2, 2).getShort();
    }

    @Override
    public final int readInt() {
        seek += 4;
        return ByteBuffer.wrap(bytes, seek-4, 4).getInt();
    }

    @Override
    public final long readLong() {
        seek += 8;
        return ByteBuffer.wrap(bytes, seek-8, 8).getLong();
    }

    @Override
    public final float readFloat() {
        seek += 4;
        return ByteBuffer.wrap(bytes, seek-4, 4).getFloat();
    }

    @Override
    public final double readDouble() {
        seek += 8;
        return ByteBuffer.wrap(bytes, seek-8, 8).getDouble();
    }

    @Override
    public final char readCharacter() {
        return (char) bytes[seek++];
    }

    @Override
    public final String readString() {
        StringBuilder sb = new StringBuilder();

        for (; seek < bytes.length; seek++) {
            if (bytes[seek] == NULL)
                break;

            sb.append((char) bytes[seek]);
        }

        return sb.toString();
    }
}