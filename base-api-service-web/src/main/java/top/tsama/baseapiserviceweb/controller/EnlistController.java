package top.tsama.baseapiserviceweb.controller;

import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tsama.baseapiserviceapi.EnlistService;
import top.tsama.baseapiservicecommon.ActionUtil;
import top.tsama.baseapiservicecommon.JsonUtil;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedomain.model.EnlistVoinfo;
import top.tsama.baseapiservicedomain.model.Enlistinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/30.
 */
@Controller
@RequestMapping("enlist")
public class EnlistController {
    Logger logger=Logger.getLogger(EnlistController.class.getName());
    @Autowired
    private EnlistService enlistService;

    /**
     * 获取报名列表
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/getenlistslist")
    public String getexpertslist(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn) {
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String flag = req.getParameter("flag");
        String account = req.getParameter("account");
        String name = req.getParameter("name");
        List<EnlistVoinfo> enlistVoinfos = new ArrayList<EnlistVoinfo>();
        try {
            EnlistVoinfo enlistVoinfo = new EnlistVoinfo();
            if (account != null && !"".equals(account)) {
                enlistVoinfo.setAccount(account);
                model.put("account", account);
            }
            if (name != null && !"".equals(name)) {
                enlistVoinfo.setCoursename(name);
                model.put("name", name);
            }
            enlistVoinfos = enlistService.selectAll(enlistVoinfo, pagn);
            PageInfo<EnlistVoinfo> page = new PageInfo<EnlistVoinfo>(enlistVoinfos);
            model.put("enlistslist", enlistVoinfos);
            model.put("page", page);
            if (flag == null || "".equals(flag)) {
                logger.info("获取报名列表成功");
                return "enlistlist";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("获取报名列表失败");
        return null;
    }

    @RequestMapping("addenlistshow")
    public String addenlistshow(){
        return "addenlist";
    }
    /**
     * 删除报名
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteenlist")
    @ResponseBody
    public String deletestudent(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn) throws Exception{
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String id=req.getParameter("id");
        try {
            if (id != null && !"".equals(id)) {
               Integer flag=enlistService.deleteByPrimaryKey(Integer.parseInt(id));
                if (flag == 1) {
                    logger.info("删除报名成功");
                    return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200", "删除成功", "enlistsfresh", "", "", ""));
                }
            }
            else{
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "请选择一条记录", "enlistsfresh", "", "", "")) ;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        logger.info("删除报名失败");
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "删除失败", "enlistsfresh", "", "", "")) ;
    }

    @RequestMapping("/addenlist")
    @ResponseBody
    public String addenlist(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn) throws Exception{
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String cid=req.getParameter("orgLookup.cid");
        String uid=req.getParameter("orgLookup.sid");
        try {
            if (cid != null && !"".equals(cid)&&uid!=null&&!"".equals(uid)) {
                EnlistVoinfo enlistVoinfo=new EnlistVoinfo();
                enlistVoinfo.setUid(Integer.parseInt(uid));
                enlistVoinfo.setCid(Integer.parseInt(cid));
                List<EnlistVoinfo> enlistVoinfolist=enlistService.selectAll(enlistVoinfo,pagn);
                if(enlistVoinfolist.size()==1){
                    return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "已经报过名了", "enlistsfresh", "", "closeCurrent", ""));
                }
                Enlistinfo enlistinfo=new Enlistinfo();
                enlistinfo.setCid(Integer.parseInt(cid));
                enlistinfo.setUid(Integer.parseInt(uid));
                enlistinfo.setResult(0);
                enlistinfo.setCreatetime(new Date());
                Integer flag=enlistService.insert(enlistinfo);
                if (flag == 1) {
                    logger.info("添加报名成功");
                    return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200", "添加报名成功", "enlistsfresh", "", "closeCurrent", ""));
                }
            }
            else{
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "请选择一条记录", "enlistsfresh", "", "closeCurrent", "")) ;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        logger.info("删除报名失败");
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "添加报名失败", "enlistsfresh", "", "closeCurrent", "")) ;
    }
    @RequestMapping(value="/export")
    @ResponseBody
    public String export(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        EnlistVoinfo enlistVoinfo=new EnlistVoinfo();
    //    String name=req.getParameter("name");
     /*   if(name!=null&&!"".equals(name)){
            try{
                name= URLDecoder.decode(name,"utf-8");
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            enlistVoinfo.setCoursename(name);
        }*/
        List<EnlistVoinfo> enlistVoinfoList=new ArrayList<EnlistVoinfo>();
        File file=new File("D:\\DWZ_enlistinfo.xls");
        if(file.exists()) {
            if (!file.renameTo(file)) {
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "文件正在被其他应用暂用，请关闭后重试", "studentsfresh", "", "closeCurrent", "")) ;
            }
        }
        String [] str= {"学员账号","学员姓名","上课地点","班次","创建时间"};
        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFSheet sheet=wb.createSheet();
        HSSFRow row=sheet.createRow(0);
        HSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCell cell = row.createCell(0);         //第一个单元格
        cell.setCellValue("课程名");                  //设定值
        cell.setCellStyle(style);
        for(int i=0;i<str.length;i++){
            cell=row.createCell(i+1);
            cell.setCellValue(str[i]);
            cell.setCellStyle(style);
        }
        try {
            enlistVoinfoList=enlistService.selectAll(enlistVoinfo,pagn);
            if(enlistVoinfoList!=null){
                for(int j=0;j<enlistVoinfoList.size();j++){
                    EnlistVoinfo enlistVoinfo1=enlistVoinfoList.get(j);
                    row=sheet.createRow(j+1);
                    //Date date=new Date(student_fileinfo.getCreatetime().toString());
                    String c =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(enlistVoinfo1.getCreatetime());
                    row.createCell(0).setCellValue(enlistVoinfo1.getCoursename());
                    row.createCell(1).setCellValue(enlistVoinfo1.getAccount());
                    row.createCell(2).setCellValue(enlistVoinfo1.getRealname());
                    row.createCell(3).setCellValue(enlistVoinfo1.getRoom());
                    row.createCell(4).setCellValue(enlistVoinfo1.getClassroom());
                    if(enlistVoinfo1.getCreatetime()!=null&&!"".equals(enlistVoinfo1.getCreatetime())) {
                        row.createCell(5).setCellValue(c);
                    }
                    else {
                        row.createCell(5).setCellValue("");
                    }
                }
                FileOutputStream fout = new FileOutputStream("D:\\DWZ_enlistinfo.xls");
                wb.write(fout);
                fout.close();
                logger.info("导出EXCEL文件成功");
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200", "导出EXCEL文件成功", "enlistfresh", "", "", "")) ;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logger.warning("导出EXCEL文件失败"+e.getMessage());
        }

        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "导出EXCEL文件失败", "enlistfresh", "", "", "")) ;
    }


}
