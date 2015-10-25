package com.jchess.network;

public abstract class PacketHandler <T> {
    protected T packet;

    public PacketHandler(T packet) {
        this.packet = packet;
    }

    public abstract void run();
}
