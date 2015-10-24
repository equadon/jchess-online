package com.jchess.network;

public enum OpCode {
    AUTH_REQUEST(0x0000);

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
