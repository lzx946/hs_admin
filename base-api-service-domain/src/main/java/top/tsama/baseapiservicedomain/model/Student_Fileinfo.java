package top.tsama.baseapiservicedomain.model;

/**
 * Created by wangdaren on 2018/1/25.
 */
public class Student_Fileinfo extends Studentnfo{
    private String url;
    private String codeflagname;

    public String getCodeflagname() {
        return codeflagname;
    }

    public void setCodeflagname(String codeflagname) {
        this.codeflagname = codeflagname;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}
