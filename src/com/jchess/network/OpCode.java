package com.jchess.network;

public enum OpCode {
    PING(0x0000);

    public final int opcode;

    OpCode(int opcode) {
        this.opcode = opcode;
    }

    public static OpCode get(int id) {
        for (OpCode oc : OpCode.values())
            if (oc.opcode == id)
                return oc;
        return null;
    }
}
