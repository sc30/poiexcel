package ie.sjc.poi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class JsoupReadWebPage {
    public static final String k3_hardcoded = "div .c_r a.button";
    public static final String sooxie_hardcoded = "div #jb > div.btnbox";

    private String k3;
    private String sooxie;

    public JsoupReadWebPage(String k3, String sooxie) {
        this.k3 = k3;
        this.sooxie = sooxie;
    }

    public static void main(String[] args) throws Exception {
        JsoupReadWebPage jsoupReadWebPage = new JsoupReadWebPage(k3_hardcoded, sooxie_hardcoded);
        System.out.println(jsoupReadWebPage.getStatus("http://www.k3.cn/p/eeafeei.html"));
        System.out.println(jsoupReadWebPage.getStatus("https://xgx.sooxie.com/96919.aspx"));
    }

    public String getStatus(String url) throws IOException {
        String statusStr = null;
        Document doc = Jsoup.connect(url).get();

        Elements elements = null;
        if (url.contains("k3.cn")) {
            elements = doc.select(k3);
        } else if (url.contains("sooxie.com")) {
            elements = doc.select(sooxie);
        } else {
            System.out.println("无法检测此网站是否下架");
            return "无法检测此网站是否下架";
        }
        for (Element element : elements) {
            statusStr = element.text();
            System.out.println("返回值为: " + statusStr);
            if (statusStr.contains("下架")) {
                return "下架";
            } else if (statusStr.contains("数据下载") || statusStr.contains("一键上传") || statusStr.contains("一件代发")) {
                // 处理sooxie.com
                return "未下架";
            } else {
                return statusStr;
            }
        }
        System.out.println("未下架");
        return "未下架";
    }
}
