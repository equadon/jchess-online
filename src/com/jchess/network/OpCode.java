package com.jchess.network;

public enum OpCode {
    // Initial handshake
    HANDSHAKE_INIT(0x0000),
    HANDSHAKE(0x0001),

    // Authentication
    AUTH_INIT(0x1000),
    AUTH(0x1001);

    public final int opcode;

    OpCode(int opcode) {
        this.opcode = opcode;
    }

    /**
     * Get enum constant from opcode.
     *
     * TODO: Improve code so we don't have to loop through all each time.
     *
     * @param opcode opcode
     * @return OpCode enum constant
     */
    public static OpCode get(int opcode) {
        for (OpCode oc : OpCode.values())
            if (oc.opcode == opcode)
                return oc;
        return null;
    }
}
