package top.tsama.baseapiservicedomain.model;

/**
 * Created by wangdaren on 2018/1/22.
 */
public class Expert_Fileinfo extends Expertsinfo {
    private String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}
