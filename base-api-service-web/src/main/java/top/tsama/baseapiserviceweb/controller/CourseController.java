package top.tsama.baseapiserviceweb.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tsama.baseapiserviceapi.CourseService;
import top.tsama.baseapiserviceapi.DictionaryService;
import top.tsama.baseapiservicecommon.ActionUtil;
import top.tsama.baseapiservicecommon.JsonUtil;
import top.tsama.baseapiservicecommon.MD5;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedomain.model.CourseVoinfo;
import top.tsama.baseapiservicedomain.model.Courseinfo;
import top.tsama.baseapiservicedomain.model.Dictionaryinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/27.
 */
@Controller
@RequestMapping("course")
public class CourseController {
    Logger logger=Logger.getLogger(CourseController.class.getName());
    @Autowired
    public CourseService courseService;
    @Autowired
    public DictionaryService dictionaryService;
    /**
     * 获取课程列表
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/getcourseslist")
    public String getexpertslist(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String name=req.getParameter("name");
        String flag=req.getParameter("flag");
        List<CourseVoinfo> courseVoinfoList=new ArrayList<CourseVoinfo>();
        try {
                CourseVoinfo courseVoinfo=new CourseVoinfo();
            if (name != null&&!"".equals(name)) {
                courseVoinfo.setName(name);
                model.put("name", name);
            }
                courseVoinfoList = courseService.selectAll(courseVoinfo,pagn);
                PageInfo<CourseVoinfo> page = new PageInfo<CourseVoinfo>(courseVoinfoList);
                for(int i=0;i<courseVoinfoList.size();i++){
                    if(courseVoinfoList.get(i).getSummary()==null||"".equals(courseVoinfoList.get(i).getSummary())){
                        courseVoinfoList.get(i).setContentStr(null);
                    }
                    else if(courseVoinfoList.get(i).getSummary().length()>10){
                        courseVoinfoList.get(i).setContentStr(courseVoinfoList.get(i).getSummary().substring(0,10));
                    }
                    else {
                        courseVoinfoList.get(i).setContentStr(courseVoinfoList.get(i).getSummary());
                    }
                }
                model.put("page", page);
                logger.info("获取课程列表成功");
                if(flag!=null&&!"".equals(flag)&&Integer.parseInt(flag)==0){
                    for(int i=0;i<courseVoinfoList.size();i++){
                        courseVoinfoList.get(i).setName("'"+courseVoinfoList.get(i).getName()+"'");
                    }
                    model.put("courselist", courseVoinfoList);
                    return "Lookup/courselookup";
                }
                model.put("courselist", courseVoinfoList);
                return "courselist";
            }
        catch (Exception e){
            e.printStackTrace();
        }
        logger.warning("获取课程列表失败");
        return null;
    }

    /**
     * 添加课程界面
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/addcourseshow")
    public String addcourseshow(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn){
        Dictionaryinfo dictionaryinfo=new Dictionaryinfo();
        dictionaryinfo.setCode(100);
        List<Dictionaryinfo> dictionaryinfoList=dictionaryService.selectByPrimaryKey(dictionaryinfo);
        if(dictionaryinfoList!=null){
            model.put("dictionaryinfoList",dictionaryinfoList);
        }
        return "addcourse";
    }

    /**
     * 添加课程
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/insertcourse")
    @ResponseBody
    public String insertexpert(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String name=req.getParameter("name");
        String room=req.getParameter("room");
        String teacherid=req.getParameter("orgLookup.id");
        String type=req.getParameter("type");
        String state=req.getParameter("state");
    //    String posterid=req.getParameter("pic");
        String summary=req.getParameter("summary");
        String starttime=req.getParameter("starttime");
        String stoptime=req.getParameter("stoptime");
        String period=req.getParameter("period");
        String classroom=req.getParameter("classroom");
        Courseinfo courseinfo=new Courseinfo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        courseinfo.setName(name);
        courseinfo.setClassroom(classroom);
        courseinfo.setCreatetime(new Date());
        /*if(posterid!=null&&!"".equals(posterid)) {
            courseinfo.setPosterid(Integer.parseInt(posterid));
        }
        else {
            courseinfo.setPosterid(1);
        }*/
        courseinfo.setState(1);
        courseinfo.setType(1);
        courseinfo.setPeriod(period);
        courseinfo.setContent(999);
        if(teacherid!=null&&!"".equals(teacherid)){
            courseinfo.setTeacherid(Integer.parseInt(teacherid));
        }
        if(type!=null&&!"".equals(type)){
            courseinfo.setType(Integer.parseInt(type));
        }
        if(state!=null&&!"".equals(state)){
            courseinfo.setState(Integer.parseInt(state));
        }
        courseinfo.setRoom(room);
        courseinfo.setSummary(summary);
        try{
            courseinfo.setStarttime(sdf.parse(starttime));
            courseinfo.setStoptime(sdf.parse(stoptime));
            if(courseinfo.getStoptime().getTime()<courseinfo.getStarttime().getTime()){
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","结束时间不得早于开始时间", "", "", "", ""));
            }
            int flag=courseService.insert(courseinfo);
            if(flag==1){
                logger.info("添加课程信息成功");
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200","添加课程信息成功", "coursefresh", "", "closeCurrent", ""));
            }
        }
        catch (Exception e){
            logger.warning("添加课程信息出错了");
            e.printStackTrace();
        }
        logger.warning("添加课程信息失败");
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","添加课程信息失败", "coursefresh", "", "closeCurrent", ""));
    }

    /**
     * 删除课程
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     * @throws Exception
     */
    @RequestMapping("/deletecourse")
    @ResponseBody
    public String deletestudent(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn) throws Exception{
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String id=req.getParameter("id");
        try {
            if (id != null && !"".equals(id)) {
                int flag = courseService.deleteByPrimaryKey(Integer.parseInt(id));
                if (flag == 1) {
                    logger.info("删除课程成功");
                    return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200", "删除课程成功", "coursefresh", "", "", ""));
                }
            }
            else{
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "请选择一条记录", "coursefresh", "", "", "")) ;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        logger.warning("删除课程失败");
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "删除课程失败", "coursefresh", "", "", "")) ;
    }

    /**
     * 获取课程信息（flag==0）
     * 修改课程信息（flag==1）
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("getcoursedetail")
    public String getexpertdetail(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String flag=req.getParameter("flag");
        Integer id=Integer.parseInt(req.getParameter("id"));
        model.put("id",id);
        model.put("flag",Integer.parseInt(flag));
        if(Integer.parseInt(flag)==1) {
            Dictionaryinfo dictionaryinfo = new Dictionaryinfo();
            dictionaryinfo.setCodeflag(100);
            List<Dictionaryinfo> dictionaryinfoList = dictionaryService.selectByPrimaryKey(dictionaryinfo);
            if (dictionaryinfoList != null) {
                model.put("dictionaryinfoList", dictionaryinfoList);
            }
        }
        List<CourseVoinfo> courseVoinfoList=new ArrayList<CourseVoinfo>();
        try {
            CourseVoinfo courseVoinfo=new CourseVoinfo();
            courseVoinfo.setId(id);
            courseVoinfoList = courseService.selectAll(courseVoinfo,pagn);
            for(int i=0;i<courseVoinfoList.size();i++){
                courseVoinfoList.get(i).setClassroomint(Integer.parseInt(courseVoinfoList.get(i).getClassroom()));
                courseVoinfoList.get(i).setUrl(ActionUtil.Rooturl+courseVoinfoList.get(i).getUrl());
            }
            model.put("courseVoinfoList", courseVoinfoList);
            logger.info("获取或者修改课程信息成功");
            return "coursedetail";
        }
        catch (Exception e){
            e.printStackTrace();
            logger.warning("获取或者修改课程信息出错了");
            return null;
        }
    }

    /**
     * 更新课程信息
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/updatecourse")
    @ResponseBody
    public String updatecourse(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String id=req.getParameter("id");
        String name=req.getParameter("name");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String room=req.getParameter("room");
        String state=req.getParameter("state");
        String type=req.getParameter("type");
//        String posterid=req.getParameter("posterid");
        String summary=req.getParameter("summary");
        String content=req.getParameter("content");//容量
        String starttime=req.getParameter("starttime");
        String stoptime=req.getParameter("stoptime");
        String reason=req.getParameter("reason");
        String period=req.getParameter("period");
        String classroom=req.getParameter("area");
        String iscommend=req.getParameter("iscommend");
        Courseinfo courseinfo=new Courseinfo();
        if(id!=null&&!"".equals(id)) {
            courseinfo.setId(Integer.parseInt(id));
        }
        if(state!=null&&!"".equals(state)) {
            courseinfo.setState(Integer.parseInt(state));
        }
        if(iscommend!=null&&!"".equals(iscommend)) {
            courseinfo.setIscommend(Integer.parseInt(iscommend));
        }
        courseinfo.setRoom(room);
        courseinfo.setClassroom(classroom);
        if(content!=null&&!"".equals(content)) {
            courseinfo.setContent(Integer.parseInt(content));
        }
        if(type!=null&&!"".equals(type)) {
            courseinfo.setType(Integer.parseInt(type));
        }
      /*  if(posterid!=null&&!"".equals(posterid)) {
            courseinfo.setPosterid(Integer.parseInt(posterid));
        }*/
        if(period!=null&&!"".equals(period)){
            courseinfo.setPeriod(period);
        }
        courseinfo.setSummary(summary);
        courseinfo.setReason(reason);
        try{
            if(starttime!=null&&!"".equals(starttime)) {
                courseinfo.setStarttime(sdf.parse(starttime));
            }
            if(stoptime!=null&&!"".equals(stoptime)) {
                courseinfo.setStoptime(sdf.parse(stoptime));
            }
            int flag=courseService.updateByPrimaryKey(courseinfo);
            if(flag==1){
                logger.info("更新课程信息成功");
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200","更新课程信息成功", "coursefresh", "", "closeCurrent", ""));
            }
        }
        catch (Exception e){
            logger.warning("更新课程信息出错了");
            e.printStackTrace();
        }
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","更新课程信息失败", "coursefresh", "", "closeCurrent", ""));
    }

}
