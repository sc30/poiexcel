package ie.sjc.poi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.apache.poi.common.usermodel.HyperlinkType.URL;

public class HttpRedirectExample {

    public static void main(String[] args) {

        try {

            //String url = "https://xgx.sooxie.com/96919.aspx";
            String url = "http://www.k3.cn/p/amiioai.html?_page=1&_cat=new&_pos=56&_type=img";
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setInstanceFollowRedirects(false);
            conn.setReadTimeout(50000);
            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            conn.addRequestProperty("User-Agent", "Mozilla");
            conn.addRequestProperty("Referer", "google.com");

            System.out.println("Request URL ... " + url);

            boolean redirect = false;
            conn.connect();
            // normally, 3xx is redirect
            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
            }

            System.out.println("Response Code ... " + status);

            if (redirect) {

                // get redirect url from "location" header field
                String newUrl = conn.getHeaderField("Location");

                // get the cookie if need, for login
                String cookies = conn.getHeaderField("Set-Cookie");

                // open the new connnection again
                conn = (HttpURLConnection) new URL(newUrl).openConnection();
                conn.setRequestProperty("Cookie", cookies);
                conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                conn.addRequestProperty("User-Agent", "Mozilla");
                conn.addRequestProperty("Referer", "google.com");

                System.out.println("Redirect to URL : " + newUrl);

            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer html = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();

            System.out.println("URL Content... \n" + html.toString());
            System.out.println("Done");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

