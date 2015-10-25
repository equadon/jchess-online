package com.jchess.server.handlers;

import com.jchess.network.packets.auth.AuthRequest;

import java.util.logging.Logger;

public class AuthHandler {
    private static final Logger LOG = Logger.getLogger(AuthHandler.class.getName());

    public AuthHandler(AuthRequest packet) {
        LOG.info("Username: " + packet.username);
        LOG.info("Password: " + packet.password);
    }
}
