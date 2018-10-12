package ie.sjc.poi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckUrlConnection {
    public static void main(String[] args) throws Exception {
        boolean canBeConnected = isUrlExists("https://www.google.com");
        System.out.println("");
    }

    public static boolean isUrlExists(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        httpURLConnection.connect();
        int code = httpURLConnection.getResponseCode();

//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//        String inputLine;
//        while ((inputLine = bufferedReader.readLine()) != null) {
//            System.out.println(inputLine);
//        }
//        bufferedReader.close();

        httpURLConnection.disconnect();
        System.out.println("code = " + code);
        return (code == HttpURLConnection.HTTP_OK) ? true : false;
    }
}
