package ru.akirakozov.ermishina.mocks.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author akirakozov
 */
public class UrlReader {

    public String readAsText(String sourceUrl) throws IOException {
        URL url = toUrl(sourceUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        String bearerToken = readResourceFile();
        con.addRequestProperty("Authorization", "Bearer " + bearerToken);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder result = new StringBuilder();
        try {
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
        } finally {
            try {
                in.close();
            } catch (Exception ignore) {
            }
        }
        return result.toString();
    }

    private URL toUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed url: " + url);
        }
    }

    private static String readResourceFile() throws IOException {
        File file = new File("src/main/java/ru/akirakozov/ermishina/mocks/env.txt");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        int ignored = fis.read(data);
        fis.close();
        return new String(data, StandardCharsets.UTF_8);
    }
}

