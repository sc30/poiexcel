package ie.sjc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckUrlConnection {
    public static void main(String[] args) throws Exception {
        boolean canBeConnected = isUrlExists("http://www.k3.cn/p/iiaauoeuimb.html?_page=4&_cat=new&_pos=110&_type=img?form=k3_detail");
        System.out.println("");
    }

    public static boolean isUrlExists(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("HEAD");
        httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        httpURLConnection.connect();
        int code = httpURLConnection.getResponseCode();
        httpURLConnection.disconnect();
        System.out.println("code = " + code);
        return (code == HttpURLConnection.HTTP_OK) ? true : false;
    }
}
