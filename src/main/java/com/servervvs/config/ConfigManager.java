package com.servervvs.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.servervvs.util.Json;
import org.seleniumhq.jetty9.server.HttpConfiguration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigManager {
    private static ConfigManager myConfigManager;
    private static Configuration myCurrentConfiguration;
    private ConfigManager(){
    }

    public static ConfigManager getInstance(){
        if(myConfigManager==null)
            myConfigManager=new ConfigManager();
        return myConfigManager;
    }

    public void loadConfiguration(String filePath){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb =new StringBuffer();
        int i;
        while (true){
            try {
                if (!(( i = fileReader.read()) != -1)) break;
            } catch (IOException e) {
                throw new HttpConfigurationException(e);
            }
            sb.append((char)i);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("error parsing the config file", e);
        }
        try {
            myCurrentConfiguration= Json.fromJson(conf,Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("error parsing the config file, internal",e);
        }

    }

    public Configuration getCurrentConfig(){
        if (myCurrentConfiguration==null){
            throw new HttpConfigurationException("No current configuration set");
        }
        return myCurrentConfiguration;
    }
}
