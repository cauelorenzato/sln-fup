package com.veridu.main;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * JsonReaderHelper Class
 */
public class JsonReaderHelper {
    /**
     * Reads environment variables from json file
     *
     * @return JsonObject json
     *
     * @throws IOException
     */
    public JsonObject read() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/environment.json");
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        StringBuilder configString = new StringBuilder();
        String line;
        try {
            line = bf.readLine();
            while (line != null) {
                configString.append(line);
                line = bf.readLine();
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(configString.toString());

        return json;
    }

    /**
     * Instantiates JsonReaderHelper Class
     * @return JsonReaderHelper instance
     */
    public static JsonReaderHelper create() {
        return new JsonReaderHelper();
    }

}
