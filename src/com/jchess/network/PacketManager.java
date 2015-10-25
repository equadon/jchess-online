package com.jchess.network;

public interface PacketManager {
    void manage(OpCode opcode, int length, byte[] payload);
}
