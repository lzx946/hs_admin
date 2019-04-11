package top.tsama.baseapiserviceweb.controller;

import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tsama.baseapiserviceapi.DictionaryService;
import top.tsama.baseapiserviceapi.StudentService;
import top.tsama.baseapiservicecommon.ActionUtil;
import top.tsama.baseapiservicecommon.JsonUtil;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedomain.model.Dictionaryinfo;
import top.tsama.baseapiservicedomain.model.Expertsinfo;
import top.tsama.baseapiservicedomain.model.Student_Fileinfo;
import top.tsama.baseapiservicedomain.model.Studentnfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/25.
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    Logger logger=Logger.getLogger(StudentController.class.getName());
    @Autowired
    public StudentService studentService;
    @Autowired
    private DictionaryService dictionaryService;
    /**
     * 学员列表
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/getstudentslist")
    public String getexpertslist(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        String realname=req.getParameter("realname");
        String flag=req.getParameter("flag");
        List<Student_Fileinfo> studentslist=new ArrayList<Student_Fileinfo>();
        try {
            if(realname==null||"".equals(realname)) {
                studentslist = studentService.selectAll(pagn);
            }
            else{
                Student_Fileinfo student_fileinfo=new Student_Fileinfo();
                student_fileinfo.setRealname(realname);
                model.put("realname", realname);
                studentslist = studentService.selectByPrimaryKey(student_fileinfo,pagn);
            }
            PageInfo<Student_Fileinfo> page = new PageInfo<Student_Fileinfo>(studentslist);
            model.put("page", page);
            logger.info("获取学员列表成功");
            if(flag!=null&&!"".equals(flag)&&Integer.parseInt(flag)==0){
                for(int i=0;i<studentslist.size();i++){
                    studentslist.get(i).setRealname("'"+studentslist.get(i).getRealname()+"'");
                }
                model.put("studentslist",studentslist);
                return "Lookup/studentlookup";
            }
            model.put("studentslist", studentslist);
            return "studentlist";
        }
        catch (Exception e){
            e.printStackTrace();
            logger.warning("获取学员列表出错了");
            return null;
        }
    }

    /**
     * 批处理学员审核通过状态
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     * @throws Exception
     */
    @RequestMapping("/updatestatemost")
    @ResponseBody
    public String updatestatemost(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        String ids=req.getParameter("ids");
        List<Studentnfo> studentnfos=new ArrayList<Studentnfo>();
        if(ids!=null&&!"".equals(ids)) {
            String[] id = ids.substring(0, ids.length() - 1).split(",");
            for (int i = 0; i < id.length; i++) {
                Studentnfo studentnfo = new Studentnfo();
                studentnfo.setId(Integer.parseInt(id[i]));
                studentnfo.setState(1);
                studentnfos.add(studentnfo);
            }
        }
        else{
            return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "请至少选择一条记录", "studentsfresh", "", "closeCurrent", "")) ;
        }
        try {
            boolean flag = studentService.updateBatch(studentnfos);
            if(flag==true){
                logger.info("批处理学员状态成功");
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200", "操作成功", "studentsfresh", "", "closeCurrent", "")) ;
            }
        }
        catch (Exception e){
            logger.warning("批处理学员状态失败"+e.getMessage());
            e.printStackTrace();
        }

        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "操作失败", "studentsfresh", "", "closeCurrent", "")) ;
    }

    /**
     * 生成Excel文件
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
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        List<Student_Fileinfo> studentnfos=new ArrayList<Student_Fileinfo>();
        File file=new File("D:\\DWZ_studentinfo.xls");
        if(file.exists()) {
            if (!file.renameTo(file)) {
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "文件正在被其他应用暂用，请关闭后重试", "studentsfresh", "", "closeCurrent", "")) ;
            }
        }
        String [] str= {"手机号","账号","真实姓名","职务","公司","个人简介","公司网址","创建时间"};
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
            studentnfos=studentService.selectAll(pagn);
            if(studentnfos!=null){
                for(int j=0;j<studentnfos.size();j++){
                    Student_Fileinfo student_fileinfo=studentnfos.get(j);
                    row=sheet.createRow(j+1);
                    //Date date=new Date(student_fileinfo.getCreatetime().toString());
                    String c =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(student_fileinfo.getCreatetime());
                    row.createCell(0).setCellValue(student_fileinfo.getEmail());
                    row.createCell(1).setCellValue(student_fileinfo.getPhone());
                    row.createCell(2).setCellValue(student_fileinfo.getAccount());
                    row.createCell(3).setCellValue(student_fileinfo.getRealname());
                    row.createCell(4).setCellValue(student_fileinfo.getPosition());
                    row.createCell(5).setCellValue(student_fileinfo.getCompany());
                    row.createCell(6).setCellValue(student_fileinfo.getSummary());
                    row.createCell(7).setCellValue(student_fileinfo.getCompanyurl());
                    if(student_fileinfo.getCreatetime()!=null&&!"".equals(student_fileinfo.getCreatetime())) {
                        row.createCell(8).setCellValue(c);
                    }
                    else {
                        row.createCell(8).setCellValue("");
                    }
                }
                FileOutputStream fout = new FileOutputStream("D:\\DWZ_studentinfo.xls");
                wb.write(fout);
                fout.close();
                logger.info("导出EXCEL文件成功");
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200", "导出EXCEL文件成功", "studentsfresh", "", "", "")) ;
            }
        }
       catch (Exception e){
           e.printStackTrace();
           logger.warning("导出EXCEL文件失败"+e.getMessage());
       }

        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "导出EXCEL文件失败", "studentsfresh", "", "", "")) ;
    }

    /**
     * 删除学员
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     * @throws Exception
     */
    @RequestMapping("/deletestudent")
    @ResponseBody
    public String deletestudent(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        String id=req.getParameter("id");
        try {
            if (id != null && !"".equals(id)) {
                Integer flag = studentService.deleteByPrimaryKey(Integer.parseInt(id));
                if (flag == 1) {
                    logger.info("删除学员成功");
                    return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200", "删除成功", "studentsfresh", "", "closeCurrent", ""));
                }
            }
            else{
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "请选择一条记录", "studentsfresh", "", "closeCurrent", "")) ;
            }
        }
        catch (Exception e){
            logger.warning("删除学员出错了");
            e.printStackTrace();
        }
        logger.warning("删除学员失败");
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "删除失败", "studentsfresh", "", "closeCurrent", "")) ;
    }

    /**
     * 获取学员个人信息（flag==0）
     * 修改学员信息（flag==1）
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/getstudentdetail")
    public String getexpertdetail(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        String flag=req.getParameter("flag");
        Integer id=Integer.parseInt(req.getParameter("id"));
        model.put("id",id);
        model.put("flag",Integer.parseInt(flag));
        List<Student_Fileinfo> student_fileinfos=new ArrayList<Student_Fileinfo>();
        Dictionaryinfo dictionaryinfo=new Dictionaryinfo();
        dictionaryinfo.setCode(100);
        List<Dictionaryinfo> dictionaryinfoList=dictionaryService.selectByPrimaryKey(dictionaryinfo);
        if(dictionaryinfoList!=null){
            model.put("dictionaryinfoList",dictionaryinfoList);
        }
        try {
            Student_Fileinfo student_fileinfo=new Student_Fileinfo();
            student_fileinfo.setId(id);
            student_fileinfos = studentService.selectByPrimaryKey(student_fileinfo,pagn);
            model.put("student_fileinfo", student_fileinfos);
            student_fileinfos.get(0).setUrl(ActionUtil.Rooturl+student_fileinfos.get(0).getUrl());
            logger.info("获取学员或者修改学员信息成功");
            return "studentdetail";
        }
        catch (Exception e){
            e.printStackTrace();
            logger.warning("获取学员或者修改学员信息出错了"+e.getMessage());
        }
        return null;
    }

    /**
     * 更新学员信息
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/updatestudent")
    @ResponseBody
    public String updateexpert(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        String id=req.getParameter("id");
        String email=req.getParameter("email");
        String phone=req.getParameter("phone");
        String account=req.getParameter("account");
        String realname=req.getParameter("realname");
        String position=req.getParameter("position");
        String company=req.getParameter("company");
        String summary=req.getParameter("summary");
        String companyurl=req.getParameter("companyurl");
        String state=req.getParameter("state");
        String area=req.getParameter("area");
        String otherposition=req.getParameter("otherposition");
        String enname=req.getParameter("enname");
        String motto=req.getParameter("motto");
        String wechat=req.getParameter("wechat");
        Studentnfo studentnfo=new Studentnfo();
        if(id!=null&&!"".equals(id)) {
            studentnfo.setId(Integer.parseInt(id));
        }
        if(state!=null&&!"".equals(state)) {
            studentnfo.setState(Integer.parseInt(state));
        }
        if(area!=null&&!"".equals(area)) {
            studentnfo.setArea(Integer.parseInt(area));
        }
        studentnfo.setAccount(account);
        studentnfo.setCompany(company);
        studentnfo.setCompanyurl(companyurl);
        studentnfo.setEmail(email);
        studentnfo.setPhone(phone);
        studentnfo.setPosition(position);
        studentnfo.setRealname(realname);
        studentnfo.setSummary(summary);
        studentnfo.setOtherposition(otherposition);
        studentnfo.setEnname(enname);
        studentnfo.setMotto(motto);
        studentnfo.setWechat(wechat);
        try{
            int flag=studentService.updateByPrimaryKey(studentnfo);
            if(flag==1){
                logger.info("更新学员信息成功");
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200","更新学员信息成功", "studentsfresh", "", "closeCurrent", ""));
            }
        }
        catch (Exception e){
            logger.warning("更新学员信息出错了"+e.getMessage());
            e.printStackTrace();
        }
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","更新学员信息失败", "studentsfresh", "", "closeCurrent", ""));
    }

    /**
     * 验证账号信息
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
        String phone=req.getParameter("phone");
        Student_Fileinfo student_fileinfo=new Student_Fileinfo();
        if(id!=null&&!"".equals(id)){
            student_fileinfo.setId(Integer.parseInt(id));
            List<Student_Fileinfo> student_fileinfos=studentService.selectByPrimaryKey(student_fileinfo,pagn);
            if(account!=null&&student_fileinfos.get(0).getAccount().equals(account)){
                student_fileinfo.setAccount(null);
                return JsonUtil.toJSON(student_fileinfo).toString();
            }
            if(email!=null&&student_fileinfos.get(0).getEmail().equals(email)){
                student_fileinfo.setEmail(null);
                return JsonUtil.toJSON(student_fileinfo).toString();
            }
            if(phone!=null&&student_fileinfos.get(0).getPhone().equals(phone)){
                student_fileinfo.setPhone(null);
                return JsonUtil.toJSON(student_fileinfo).toString();
            }
        }
        List<Student_Fileinfo> expertslist=new ArrayList<Student_Fileinfo>();
        try {
            Student_Fileinfo student_fileinfonew=new Student_Fileinfo();
            if(account!=null&&!"".equals(account))
                student_fileinfonew.setAccount(account);
            if(email!=null&&!"".equals(email))
                student_fileinfonew.setEmail(email);
            if(phone!=null&&!"".equals(phone))
                student_fileinfonew.setPhone(phone);
            expertslist = studentService.selectByPrimaryKey(student_fileinfonew,pagn);
            if(expertslist.size()==1){
                return JsonUtil.toJSON(student_fileinfonew).toString();
            }
            else{
                student_fileinfo.setAccount(null);
                student_fileinfo.setEmail(null);
                logger.info("验证手机号账号邮箱成功");
                return JsonUtil.toJSON(student_fileinfo).toString();
            }
        }
        catch (Exception e){
            logger.info("验证手机号账号邮箱失败"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
