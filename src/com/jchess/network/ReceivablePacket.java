package com.jchess.network;

public interface ReceivablePacket {
    OpCode getOpcode();
    byte[] getBytes();
}
