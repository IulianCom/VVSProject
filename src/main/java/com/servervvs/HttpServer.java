package com.servervvs;

import com.servervvs.config.ConfigManager;
import com.servervvs.config.Configuration;
import com.servervvs.core.ServerListenerThread;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


import java.io.IOException;



public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

public static void main(String[] args) {


    LOGGER.info("Server starting...");
    ConfigManager.getInstance().loadConfiguration("src/main/resources/http.json");

    Configuration conf = ConfigManager.getInstance().getCurrentConfig();

    LOGGER.info("Using port " + conf.getPort());
    LOGGER.info("Using webroot " + conf.getWebroot());

    ServerListenerThread serverListenerThread = null;
    try {
        serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
        serverListenerThread.start();

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
