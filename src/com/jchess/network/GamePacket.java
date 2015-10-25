package com.jchess.network;

import java.nio.ByteBuffer;
import java.util.Arrays;

public abstract class GamePacket implements ReceivablePacket, SendablePacket {
    protected static byte NULL = 0x00;

    protected OpCode opcode;

    protected int seek;
    protected int length;
    protected byte[] bytes;

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

    public GamePacket(ReceivablePacket packet) {
        this(packet.getBytes());
    }

    public GamePacket(byte[] bytes) {
        this.bytes = bytes;
        this.opcode = readOpcode();
        this.length = readShort();
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

    protected void updateLength() {
        this.length = bytes.length - 4;
        putLength(this.length);
    }

    protected void putOpcode(OpCode opcode) {
        ByteBuffer.wrap(bytes).putShort(0, (short) opcode.opcode);
    }

    protected void putLength(int length) {
        ByteBuffer.wrap(bytes).putShort(2, (short) length);
    }

    protected void add(byte value) {
        bytes = Arrays.copyOf(bytes, bytes.length + 1);
        bytes[bytes.length-1] = value;
    }

    protected void add(byte[] values) {
        int length = bytes.length;
        bytes = Arrays.copyOf(bytes, length + values.length);

        for (int i = 0; i < values.length; i++)
            bytes[length + i] = values[i];
    }

    protected void add(short value) {
        bytes = ByteBuffer.allocate(bytes.length + 2).put(bytes).putShort(value).array();
    }

    protected void add(int value) {
        bytes = ByteBuffer.allocate(bytes.length + 4).put(bytes).putInt(value).array();
    }

    protected void add(long value) {
        bytes = ByteBuffer.allocate(bytes.length + 8).put(bytes).putLong(value).array();
    }

    protected void add(float value) {
        bytes = ByteBuffer.allocate(bytes.length + 4).put(bytes).putFloat(value).array();
    }

    protected void add(double value) {
        bytes = ByteBuffer.allocate(bytes.length + 8).put(bytes).putDouble(value).array();
    }

    protected void add(char value) {
        bytes = ByteBuffer.allocate(bytes.length + 8).put(bytes).putChar(value).array();
    }

    protected void add(String value) {
        bytes = ByteBuffer.allocate(bytes.length + value.length() + 1).put(bytes).put(value.getBytes()).put(NULL).array();
    }

    /**
     * ReceivablePacket
     */
    protected OpCode readOpcode() {
        return OpCode.get(readShort());
    }

    protected byte[] readBytes(int length) {
        byte[] range = Arrays.copyOfRange(bytes, seek, seek + length);
        seek += length;
        return range;
    }

    protected short readShort() {
        seek += 2;
        return ByteBuffer.wrap(bytes, seek - 2, 2).getShort();
    }

    protected int readInt() {
        seek += 4;
        return ByteBuffer.wrap(bytes, seek-4, 4).getInt();
    }

    protected long readLong() {
        seek += 8;
        return ByteBuffer.wrap(bytes, seek-8, 8).getLong();
    }

    protected float readFloat() {
        seek += 4;
        return ByteBuffer.wrap(bytes, seek-4, 4).getFloat();
    }

    protected double readDouble() {
        seek += 8;
        return ByteBuffer.wrap(bytes, seek-8, 8).getDouble();
    }

    protected char readCharacter() {
        return (char) bytes[seek++];
    }

    protected String readString() {
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