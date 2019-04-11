package top.tsama.baseapiserviceweb.controller;

import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tsama.baseapiserviceapi.ExpertsService;
import top.tsama.baseapiservicecommon.*;
import top.tsama.baseapiservicedomain.model.Expert_Fileinfo;
import top.tsama.baseapiservicedomain.model.Expertsinfo;
import top.tsama.baseapiservicedomain.model.Student_Fileinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/22.
 */
@Controller
@RequestMapping("/experts")
public class ExpertsController {
    Logger logger=Logger.getLogger(ExpertsController.class.getName());
    @Autowired
    public ExpertsService expertsService;

    /**
     * 获取专家列表
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/getexpertslist")
    public String getexpertslist(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String flag=req.getParameter("flag");
        String realname=req.getParameter("realname");
        List<Expert_Fileinfo> expertslist=new ArrayList<Expert_Fileinfo>();
        try {
            if(realname==null||"".equals(realname)) {
                expertslist = expertsService.selectAll(pagn);
            }
            else{
                Expert_Fileinfo expert_fileinfo=new Expert_Fileinfo();
                expert_fileinfo.setRealname(realname);
                model.put("realname", realname);
                expertslist = expertsService.selectByPrimaryKey(expert_fileinfo,pagn);
            }
            PageInfo<Expert_Fileinfo> page = new PageInfo<Expert_Fileinfo>(expertslist);
            model.put("expertslist", expertslist);
            model.put("page", page);
            if(flag==null||"".equals(flag)) {
                logger.info("获取专家列表成功");
                return "experts";
            }
            else{
                for(int i=0;i<expertslist.size();i++){
                    expertslist.get(i).setAccount("'"+expertslist.get(i).getAccount()+"'");
                }
                logger.info("获取专家列表成功");
                return "Lookup/expertlookup";
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logger.warning("获取专家列表失败"+e.getMessage());
            return null;
        }
    }

    /**
     * 验证账号邮箱
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/verifyAccount")
    @ResponseBody
    public String verifyAccount(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String account=req.getParameter("account");
        String email=req.getParameter("email");
        String id=req.getParameter("id");
        Expert_Fileinfo expert_fileinfo=new Expert_Fileinfo();
        if(id!=null&&!"".equals(id)){
            expert_fileinfo.setId(Integer.parseInt(id));
            List<Expert_Fileinfo> expert_fileinfos=expertsService.selectByPrimaryKey(expert_fileinfo,pagn);
            if(account!=null&&expert_fileinfos.get(0).getAccount().equals(account)){
                expert_fileinfo.setAccount(null);
                return JsonUtil.toJSON(expert_fileinfo).toString();
            }
            if(email!=null&&expert_fileinfos.get(0).getEmail().equals(email)){
                expert_fileinfo.setEmail(null);
                return JsonUtil.toJSON(expert_fileinfo).toString();
            }
        }
        List<Expert_Fileinfo> expertslist=new ArrayList<Expert_Fileinfo>();
        try {
            Expert_Fileinfo expert_fileinfonew=new Expert_Fileinfo();
            if(account!=null&&!"".equals(account))
                expert_fileinfonew.setAccount(account);
            if(email!=null&&!"".equals(email))
                expert_fileinfonew.setEmail(email);
            expertslist = expertsService.selectByPrimaryKey(expert_fileinfonew,pagn);
            if(expertslist.size()==1){
                return JsonUtil.toJSON(expert_fileinfonew).toString();
            }
            else{
                expert_fileinfo.setAccount(null);
                expert_fileinfo.setEmail(null);
                logger.info("验证账号邮箱成功");
                return JsonUtil.toJSON(expert_fileinfo).toString();
            }
        }
        catch (Exception e){
            logger.info("验证账号邮箱失败"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取专家详情
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/getexpertdetail")
    public String getexpertdetail(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        Integer id=Integer.parseInt(req.getParameter("id"));
        String flag=req.getParameter("flag");
        model.put("id",id);
        model.put("flag",Integer.parseInt(flag));
        List<Expert_Fileinfo> expertslist=new ArrayList<Expert_Fileinfo>();
        try {
            Expert_Fileinfo expert_fileinfo=new Expert_Fileinfo();
            expert_fileinfo.setId(id);
            expertslist = expertsService.selectByPrimaryKey(expert_fileinfo,pagn);
            model.put("expertlist", expertslist);
            expertslist.get(0).setUrl(ActionUtil.Rooturl+expertslist.get(0).getUrl());
            logger.info("获取专家详情成功");
            return "expertsdetail";
        }
        catch (Exception e){
            e.printStackTrace();
            logger.warning("获取专家详情失败"+e.getMessage());
            return null;
        }
    }

    /**
     * 添加专家页面
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/addexpert")
    public String addexpert(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String ids=req.getParameter("ids");
        return "addexpert";
    }

    /**
     * 添加专家
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/insertexpert")
    @ResponseBody
    public String insertexpert(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String realname=req.getParameter("realname");
        String account=req.getParameter("account");
        String email=req.getParameter("email");
        String phone=req.getParameter("phone");
        String academic=req.getParameter("academic");
        String employer=req.getParameter("employer");
        String summary=req.getParameter("content");
        String pic=req.getParameter("pic");
        String motto=req.getParameter("motto");
        String otheracademic=req.getParameter("otheracademic");
        String enname=req.getParameter("enname");
        String openemail=req.getParameter("openemail");
        String summarys=req.getParameter("summarys");
        Expertsinfo expertsinfo=new Expertsinfo();
        expertsinfo.setAcademic(academic);
        expertsinfo.setAccount(account);
        expertsinfo.setCreatetime(new Date());
        expertsinfo.setEmail(email);
        expertsinfo.setEmployer(employer);
        expertsinfo.setPassword(MD5.encodeString("000000"));
        expertsinfo.setPhone(phone);
        expertsinfo.setRealname(realname);
        expertsinfo.setEnname(enname);
        expertsinfo.setMotto(motto);
        expertsinfo.setOpenemail(openemail);
        expertsinfo.setOtheracademic(otheracademic);
        //默认设定不可见
        expertsinfo.setIsvisible(1);
        if(pic==null||"".equals(pic)){
            expertsinfo.setPhotoid(1);
        }
        else {
            expertsinfo.setPhotoid(Integer.parseInt(pic));
        }
        expertsinfo.setSummary(summary);
        expertsinfo.setSummarys(summarys);
        expertsinfo.setState(1);
        try{
            boolean flag=expertsService.insert(expertsinfo);
            if(flag==true){
                logger.info("添加专家信息成功");
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200","成功", "expertsfresh", "", "closeCurrent", ""));
            }
        }
        catch (Exception e){
            logger.warning("添加专家信息失败"+e.getMessage());
            e.printStackTrace();
        }
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","失败", "expertsfresh", "", "closeCurrent", ""));
    }

    /**
     * 更新专家信息
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/updateexpert")
    @ResponseBody
    public String updateexpert(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String id=req.getParameter("id");
        String state=req.getParameter("state");
        String email=req.getParameter("email");
        String photoid=req.getParameter("photoid");
        String phone=req.getParameter("phone");
        String account=req.getParameter("account");
        String realname=req.getParameter("realname");
        String academic=req.getParameter("academic");
        String employer=req.getParameter("employer");
        String summary=req.getParameter("summary");
        String isvisible=req.getParameter("isvisible");
        String enname=req.getParameter("enname");
        String openemail=req.getParameter("openemail");
        String otheracademic=req.getParameter("otheracademic");
        String motto=req.getParameter("motto");
        String iscommend=req.getParameter("iscommend");
        String summarys=req.getParameter("summarys");
        Expertsinfo expertsinfo=new Expertsinfo();
        expertsinfo.setEnname(enname);
        expertsinfo.setOpenemail(openemail);
        expertsinfo.setOtheracademic(otheracademic);
        expertsinfo.setMotto(motto);
        expertsinfo.setId(Integer.parseInt(id));
        if(state!=null&&!"".equals(state)) {
            expertsinfo.setState(Integer.parseInt(state));
        }
        if(photoid!=null&&!"".equals(photoid)){
            expertsinfo.setPhotoid(Integer.parseInt(photoid));
        }
        if(isvisible!=null&&!"".equals(isvisible)){
            expertsinfo.setIsvisible(Integer.parseInt(isvisible));
        }
        if(iscommend!=null&&!"".equals(iscommend)){
            expertsinfo.setIscommend(Integer.parseInt(iscommend));
        }
        expertsinfo.setSummarys(summarys);
        expertsinfo.setEmployer(employer);
        expertsinfo.setEmail(email);
        expertsinfo.setSummary(summary);
        expertsinfo.setRealname(realname);
        expertsinfo.setPhone(phone);
        expertsinfo.setAccount(account);
        expertsinfo.setAcademic(academic);
        try{
            boolean flag=expertsService.updateByPrimaryKey(expertsinfo);
            if(flag==true){
                logger.info("更新专家信息成功");
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200","成功", "expertsfresh", "", "", ""));
            }
        }
        catch (Exception e){
            logger.warning("更新专家信息失败"+e.getMessage());
            e.printStackTrace();
        }
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","失败", "expertsfresh", "", "", ""));
    }

    /**
     * 删除专家信息
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteexpert")
    @ResponseBody
    public String deletestudent(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn) throws Exception{
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String id=req.getParameter("id");
        try {
            if (id != null && !"".equals(id)) {
                boolean flag = expertsService.deleteByPrimaryKey(Integer.parseInt(id));
                if (flag == true) {
                    logger.info("删除专家信息成功");
                    return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200", "删除成功", "expertsfresh", "", "", ""));
                }
            }
            else{
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "请选择一条记录", "expertsfresh", "", "", "")) ;
            }
        }
        catch (Exception e){
            logger.warning("删除专家信息失败"+e.getMessage());
            e.printStackTrace();
        }
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "删除失败", "expertsfresh", "", "", "")) ;
    }

    /**
     * 导出专家信息到Excel
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     * @throws Exception
     */
    @RequestMapping("/export")
    @ResponseBody
    public String export(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn) throws Exception{
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        List<Expert_Fileinfo> expertinfos=new ArrayList<Expert_Fileinfo>();
        File file=new File("D:\\DWZ_expertinfo.xls");
        if(file.exists()) {
            if (!file.renameTo(file)) {
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "文件正在被其他应用暂用，请关闭后重试", "expertsfresh", "", "closeCurrent", "")) ;
            }
        }
        String [] str= {"手机号","账号","真实姓名","职称","单位","个人简介","创建时间"};
        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFSheet sheet=wb.createSheet();
        HSSFRow row=sheet.createRow(0);
        HSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCell cell = row.createCell(0);         //第一个单元格
        cell.setCellValue("邮箱");                  //设定值
        cell.setCellStyle(style);
        for(int i=0;i<str.length;i++){
            cell=row.createCell(i+1);
            cell.setCellValue(str[i]);
            cell.setCellStyle(style);
        }
        try {
            expertinfos=expertsService.selectAll(pagn);
            if(expertinfos!=null){
                for(int j=0;j<expertinfos.size();j++){
                    Expert_Fileinfo expert_fileinfo=expertinfos.get(j);
                    row=sheet.createRow(j+1);
                    //Date date=new Date(student_fileinfo.getCreatetime().toString());
                    String c =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expert_fileinfo.getCreatetime());
                    row.createCell(0).setCellValue(expert_fileinfo.getEmail());
                    row.createCell(1).setCellValue(expert_fileinfo.getPhone());
                    row.createCell(2).setCellValue(expert_fileinfo.getAccount());
                    row.createCell(3).setCellValue(expert_fileinfo.getRealname());
                    row.createCell(4).setCellValue(expert_fileinfo.getAcademic());
                    row.createCell(5).setCellValue(expert_fileinfo.getEmployer());
                    row.createCell(6).setCellValue(expert_fileinfo.getSummary());
                    if(expert_fileinfo.getCreatetime()!=null&&!"".equals(expert_fileinfo.getCreatetime())) {
                        row.createCell(7).setCellValue(c);
                    }
                    else {
                        row.createCell(7).setCellValue("");
                    }
                }
                FileOutputStream fout = new FileOutputStream("D:\\DWZ_expertinfo.xls");
                wb.write(fout);
                fout.close();
                logger.info("导出专家excel成功");
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200", "导出EXCEL文件成功", "expertsfresh", "", "", "")) ;
            }
        }
        catch (Exception e){
            logger.warning("导出专家excel失败"+e.getMessage());
            e.printStackTrace();
        }
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "导出EXCEL文件失败", "expertsfresh", "", "", "")) ;
    }
}
