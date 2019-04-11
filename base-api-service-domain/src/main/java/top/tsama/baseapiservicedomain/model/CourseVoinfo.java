package top.tsama.baseapiservicedomain.model;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/27.
 */
public class CourseVoinfo extends Courseinfo {
    //教师账号（唯一性）
    private String account;
    //海报URL
    private String url;

    private String contentStr;

    private String realname;

    private Integer photoid;

    private Integer classroomint;

    public Integer getClassroomint() {
        return classroomint;
    }

    public void setClassroomint(Integer classroomint) {
        this.classroomint = classroomint;
    }

    public Integer getPhotoid() {
        return photoid;
    }

    public void setPhotoid(Integer photoid) {
        this.photoid = photoid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount() {

        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
