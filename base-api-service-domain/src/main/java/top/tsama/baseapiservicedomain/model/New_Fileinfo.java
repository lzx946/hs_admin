package top.tsama.baseapiservicedomain.model;

/**
 * Created by wangdaren on 2018/1/26.
 */
public class New_Fileinfo extends News {
    private String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}
