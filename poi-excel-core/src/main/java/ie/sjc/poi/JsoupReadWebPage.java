package ie.sjc.poi;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class JsoupReadWebPage {
    // TODO: create two fields in the SwingGUI to represent these cssQueries
    // such that it gives user an option to update cssQueries if websites get updated
    public static final String k3_hardcoded = "div .c_r a.button";
    public static final String k3_hardcoded_active = "div .c_r > p.bot > span.platform";
    public static final String sooxie_hardcoded = "div #jb > div.btnbox";

    private String k3;
    private String sooxie;

    private Connection.Request request;
    private Connection.Response response;

    private int statusCode;
    private String responseBody;

    public JsoupReadWebPage(String k3, String sooxie) {
        this.k3 = k3;
        this.sooxie = sooxie;
    }

    public static void main(String[] args) {
        JsoupReadWebPage jsoupReadWebPage = new JsoupReadWebPage(k3_hardcoded, sooxie_hardcoded);
        //System.out.println(jsoupReadWebPage.requestGetStatus("http://www.k3.cn/p/amiioai.html?_page=1&_cat=new&_pos=56&_type=img"));
        //System.out.println(jsoupReadWebPage.getItemStatus("http://www.k3.cn/p/amiioai.html?_page=1&_cat=new&_pos=56&_type=img"));
        //System.out.println(jsoupReadWebPage.requestGetStatus("https://xgx.sooxie.com/96919.aspx"));
        //System.out.println(jsoupReadWebPage.getItemStatus("https://xgx.sooxie.com/96919.aspx"));

        System.out.println(jsoupReadWebPage.requestGetStatus("http://www.k3.cn/p/iiuueiuafod.html?_page=1&_cat=new&_pos=96&_type=img?form=k3_detail"));
        System.out.println(jsoupReadWebPage.getItemStatus("http://www.k3.cn/p/iiuueiuafod.html?_page=1&_cat=new&_pos=96&_type=img?form=k3_detail"));
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setResponseBody (String responseBody) {
        this.responseBody = responseBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public boolean requestGetStatus(String url) {
        // if url is malformed, or URI has syntax issue when converting url to uri, return false
        if (!isValidUrl(url)) {
            System.out.println("链接:" + url + "，其格式不符合规则，请手动检查链接的正确性。跳过处理此链接。");
            return false;
        }

        Connection jsoupConnection = Jsoup.connect(url);
        jsoupConnection.ignoreHttpErrors(true);

        // Get request
        request = jsoupConnection.request();
        request.method(Connection.Method.GET);
        request.followRedirects(false);

        // Execute and get response
        try {
            response = jsoupConnection.execute();
        } catch (Exception e) {
            System.out.println("发送请求链接失败，请手动检查此链接：" + url);
            return false;
        }

        int statusCode = response.statusCode();
        setStatusCode(statusCode);
        String resposeBody = response.body();
        setResponseBody(resposeBody);
        //System.out.println("body = " + resposeBody);

        System.out.println("网站返回HTTP请求代码为： " + statusCode + "。如果为200说明链接正常。");
        return statusCode == 200;
    }

    public String getItemStatus(String url) {
        int code = getStatusCode();
        //if (code != 200) {
        //    System.out.println("HTTP请求返回值不为200，返回值为：" + code + ",请手动检测打开链接再次检测！");
        //    return "HTTP请求返回值不为200，返回值为：" + code + ",请手动检测打开链接再次检测！";
        //}

        String statusStr = null;
        Document doc = null;
        try {
            doc = response.parse();
            //System.out.println("Doc = " + doc.toString());
        } catch (IOException e) {
            // don't throw exception, return unable to handle website
            return "parser出错，无法分析网页，请手动检测！";
        }

        Elements elements = null;
        if (url.contains("k3.cn")) {
            if (code == 200) {
                elements = doc.select(k3);
                for (Element element : elements) {
                    statusStr = element.text();
                    System.out.println("k3.cn: 返回值为：" + statusStr);
                    if (statusStr.contains("下架")) {
                        return "下架";
                    }
                }

                elements = doc.select(k3_hardcoded_active);
                for (Element element : elements) {
                    statusStr = element.text();
                    System.out.println("k3.cn: 返回值为: " + statusStr);
                    if (statusStr.contains("淘宝") || statusStr.contains("拼多多") || statusStr.contains("美丽说") || statusStr.contains("阿里") || statusStr.contains("微信")) {
                        System.out.println("k3.cn:未下架");
                        return "未下架";
                    }
                }

                // 该商品不存在或已删除！同样返回200
                if (getResponseBody().contains("该商品不存在或已删除")) {
                    System.out.println("k3.cn: 链接失效: 该商品不存在或已删除");
                    return "链接失效";
                }
            }
        } else if (url.contains("sooxie.com")) {
            if (code == 200) {
                elements = doc.select(sooxie);
                for (Element element : elements) {
                    statusStr = element.text();
                    System.out.println("sooxie.com: 返回值为: " + statusStr);
                    if (statusStr.contains("下架")) {
                        System.out.println("sooxie.com: 下架");
                        return "下架";
                    } else if (statusStr.contains("数据下载") || statusStr.contains("一键上传") || statusStr.contains("一件代发")) {
                        System.out.println("sooxie.com: 未下架");
                        return "未下架";
                    } else {
                        System.out.println("sooxie.com: " + statusStr);
                        return statusStr;
                    }
                }
            } else if (code == 302) {
                if (getResponseBody().contains("sooxie.com/404.html")) {
                    System.out.println("sooxie.com: 链接失效: 该页面不存在");
                    return "链接失效";
                }
            } else {
                System.out.println("HTTP code = " + code + ",sooxie.com: 无法识别是否下架，请手动检测!");
                return "HTTP code = " + code + ",sooxie.com: 无法识别是否下架，请手动检测!";
            }
        }

        if (code != 200) {
            System.out.println("HTTP请求返回值不为200，返回值为：" + code + ",请手动检测打开链接再次检测！");
            return "HTTP请求返回值不为200，返回值为：" + code + ",请手动检测打开链接再次检测！";
        }

        System.out.println("目前仅支持k3和sooxie");
        return "目前仅支持k3和sooxie";
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
