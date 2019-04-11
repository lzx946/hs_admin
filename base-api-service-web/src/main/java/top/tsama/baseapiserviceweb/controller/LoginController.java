package top.tsama.baseapiserviceweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tsama.baseapiserviceapi.ManagementService;
import top.tsama.baseapiservicecommon.ActionUtil;
import top.tsama.baseapiservicecommon.JsonUtil;
import top.tsama.baseapiservicecommon.MD5;
import top.tsama.baseapiservicedomain.model.Managementinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/30.
 */
@Controller
public class LoginController {
    Logger logger=Logger.getLogger(LoginController.class.getName());
    @Autowired
    private ManagementService managementService;

    /**
     * 退出
     * @return
     */
    @RequestMapping("loginout")
    public String loginout(){
        return "login";
    }

    /**
     * 登录界面
     * @return
     */
    @RequestMapping("/")
    private String hello(){
        return "login";
    }

    /**
     * 登录
     * @param req
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse response, ModelMap model){
        response.setHeader("Access-Control-Allow-Origin","*");
        String name=req.getParameter("username");
        String password=req.getParameter("password");
        Managementinfo managementinfo=new Managementinfo();
        managementinfo.setUsername(name);
        managementinfo.setPassword(MD5.encodeString(password));
        List<Managementinfo> managementinfoList=managementService.selectAll(managementinfo);
        if(name==null||password==null||name.equals("")||password.equals("")){
            model.put("msg", "账户或密码为空");
            return "login";
        }
        if(managementinfoList.size()==1){
            logger.info("登录成功");
            return "index";
        }
        else {
            model.put("msg","账户或密码不正确");
            logger.warning("登录失败");
            return "login";
        }
    }

    /**
     * 修改密码界面显示
     * @return
     */
    @RequestMapping("/changepwdshow")
    public String changepwdshow(){
        return "changepwd";
    }

    /**
     * 修改密码
     * @param req
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/updatepassword")
    @ResponseBody
    public String updatepassword(HttpServletRequest req, HttpServletResponse response, ModelMap model){
        response.setHeader("Access-Control-Allow-Origin","*");
        String oldPassword=req.getParameter("oldPassword");
        String rnewPassword=req.getParameter("rnewPassword");
        Managementinfo managementinfo=new Managementinfo();
        List<Managementinfo> managementinfos=managementService.selectAll(managementinfo);
        if(!managementinfos.get(0).getPassword().equals(MD5.encodeString(oldPassword))){
            return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","原始密码错误", "", "", "closeCurrent", ""));
        }
        managementinfo.setId(1);
        managementinfo.setPassword(MD5.encodeString(rnewPassword));
        int flag=managementService.updateByPrimaryKey(managementinfo);
        try {
            if (flag == 1) {
                logger.info("修改密码成功");
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200","密码修改成功", "", "", "closeCurrent", ""));
            }
        }catch (Exception e){
            logger.warning("修改密码失败"+e.getMessage());
            e.printStackTrace();
        }
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","密码修改失败", "", "", "closeCurrent", ""));
    }
}
