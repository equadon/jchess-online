package com.jchess.network;

public interface ReceivablePacket {
    void decrypt();

    OpCode readOpcode();
    short readShort();
    int readInt();
    long readLong();
    float readFloat();
    double readDouble();
    char readCharacter();
    String readString();
}
