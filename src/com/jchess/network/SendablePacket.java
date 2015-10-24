package com.jchess.network;

public interface SendablePacket {
    byte[] getBytes();

    void add(byte value);
    void add(byte[] values);
    void add(short value);
    void add(int value);
    void add(long value);
    void add(float value);
    void add(double value);
    void add(char value);
    void add(String value);
}
