package ie.sjc.poi;

import org.jsoup.Jsoup;

public class JsoupReadWebPage {
    public static void main(String[] args) throws Exception {
        System.out.println(getHtml("http://www.163.com"));
    }

    public static String getHtml(String webPage) throws Exception {
        String html = Jsoup.connect(webPage).get().html();
        return html;
    }
}
