package com.jchess.network;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Logger;

public class GamePacket implements ReceivablePacket, SendablePacket {
    private static final Logger LOG = Logger.getLogger(GamePacket.class.getName());
    private static byte NULL = 0;

    public final OpCode opcode;

    private byte[] bytes;
    private int seek = 0;

    public GamePacket(OpCode opcode) {
        bytes = new byte[0];
        this.opcode = opcode;
        add((short) opcode.opcode);
    }

    private GamePacket(boolean fromBytes, byte[] bytes) {
        this.bytes = bytes;
        this.opcode = readOpcode();
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public final OpCode readOpcode() {
        return OpCode.get(readShort());
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

    @Override
    public final short readShort() {
        seek += 2;
        return ByteBuffer.wrap(bytes, seek-2, 2).getShort();
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

    @Override
    public String toString() {
        return "BasePacket{" + "opcode=" + opcode + ", bytes=" + Arrays.toString(bytes) + '}';
    }

    public static GamePacket fromBytes(byte[] bytes) {
        return new GamePacket(true, bytes);
    }
}