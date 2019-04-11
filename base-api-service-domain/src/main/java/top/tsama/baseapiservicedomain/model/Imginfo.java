package top.tsama.baseapiservicedomain.model;

import java.io.Serializable;

/**
 * Created by wangdaren on 2018/2/11.
 */
public class Imginfo implements Serializable{
    private String err;
    private String msg;
    public String getErr() {
        return err;
    }
    @Override
    public String toString() {
        return "Imginfo{" +
                "err='" + err + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
