package com.jchess.network;

public interface SendablePacket {
    OpCode getOpcode();
    byte[] getBytes();
}
