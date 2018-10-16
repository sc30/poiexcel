package ie.sjc.poi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckUrlConnection {
    public static void main(String[] args) throws Exception {
        boolean canBeConnected = isUrlExists("https://www.google.com");
        System.out.println(canBeConnected);
    }

    public static boolean isUrlExists(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        httpURLConnection.connect();
        int code = httpURLConnection.getResponseCode();

        httpURLConnection.disconnect();
        System.out.println("网站返回HTTP请求代码为： " + code + "。如果为200说明链接正常。");
        return (code == HttpURLConnection.HTTP_OK) ? true : false;
    }
}
