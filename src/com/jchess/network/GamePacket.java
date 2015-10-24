package com.jchess.network;

import com.jchess.network.packets.auth.RequestAuth;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Logger;

public class GamePacket implements ReceivablePacket, SendablePacket {
    private static final Logger LOG = Logger.getLogger(GamePacket.class.getName());
    private static byte NULL = 0x00;

    private OpCode opcode;

    private int seek;
    private int length;
    private byte[] bytes;

    /**
     * Construct a new game packet with the provided opcode.
     * @param opcode opcode for created packet
     */
    public GamePacket(OpCode opcode) {
        this.bytes = new byte[4];
        this.opcode = opcode;
        this.seek = 0;

        putOpcode(opcode);
    }

    /**
     * Construct a new game packet from a byte array.
     */
    public GamePacket(OpCode opcode, int length, byte[] payload) {
        this.opcode = opcode;
        this.length = length;
        this.bytes = new byte[length + 4];
        this.seek = 4;
        System.arraycopy(payload, 0, this.bytes, 4, length);
        ByteBuffer.wrap(this.bytes).putShort(0, (short) opcode.opcode);
        updateLength();
    }

    public OpCode getOpcode() {
        return opcode;
    }

    /**
     * SendablePacket overrides
     */
    @Override
    public byte[] getBytes() {
        updateLength();
        return bytes;
    }

    private void updateLength() {
        this.length = bytes.length - 4;
        putLength(this.length);
    }

    private void putOpcode(OpCode opcode) {
        ByteBuffer.wrap(bytes).putShort(0, (short) opcode.opcode);
    }

    private void putLength(int length) {
        ByteBuffer.wrap(bytes).putShort(2, (short) length);
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
            if (bytes[seek] == NULL) {
                seek++;
                break;
            }

            sb.append((char) bytes[seek]);
        }

        return sb.toString();
    }
}