package top.tsama.baseapiserviceweb.controller;

import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tsama.baseapiserviceapi.CommentService;
import top.tsama.baseapiservicecommon.ActionUtil;
import top.tsama.baseapiservicecommon.JsonUtil;
import top.tsama.baseapiservicecommon.MD5;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedomain.model.CommentVoinfo;
import top.tsama.baseapiservicedomain.model.Commentinfo;
import top.tsama.baseapiservicedomain.model.Expert_Fileinfo;
import top.tsama.baseapiservicedomain.model.Expertsinfo;

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
 * Created by wangdaren on 2018/1/29.
 */
@Controller
@RequestMapping("comment")
public class CommentController {
    Logger logger=Logger.getLogger(CommentController.class.getName());
    @Autowired
    private CommentService commentService;

    /**
     * 获取评论列表
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/getcommentslist")
    public String getcommentslist(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn){
        //String flag=req.getParameter("flag");
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String account=req.getParameter("account");
        String realname=req.getParameter("realname");
        String coursename=req.getParameter("coursename");
        List<CommentVoinfo> commentVoinfoList=new ArrayList<CommentVoinfo>();
        try {
            CommentVoinfo commentVoinfo = new CommentVoinfo();
            if (account != null && !"".equals(account)) {
                commentVoinfo.setAccount(account);
                model.put("account", account);
            }
            if (realname != null && !"".equals(realname)) {
                commentVoinfo.setRealname(realname);
                model.put("realname", realname);
            }
            if (coursename != null && !"".equals(coursename)) {
                commentVoinfo.setCoursename(coursename);
                model.put("coursename", coursename);
            }
            commentVoinfoList = commentService.selectAll(commentVoinfo, pagn);
            PageInfo<CommentVoinfo> page = new PageInfo<CommentVoinfo>(commentVoinfoList);
            if (commentVoinfoList.size() > 0) {
                for (int i = 0; i < commentVoinfoList.size(); i++) {
                    if (commentVoinfoList.get(i).getContent().length() > 15) {
                        commentVoinfoList.get(i).setContentStr(commentVoinfoList.get(i).getContent().substring(0, 15));
                    } else {
                        commentVoinfoList.get(i).setContentStr(commentVoinfoList.get(i).getContent());
                    }
                }
            }
            model.put("commentVoinfoList", commentVoinfoList);
            model.put("page", page);
            logger.info("获取评论列表成功");
            return "commentlist";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        logger.warning("获取评论列表失败");
        return null;
    }

    /**
     * 获取评论详情
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/getcommentdetail")
    public String getcommentdetail(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        Integer id=Integer.parseInt(req.getParameter("id"));
        model.put("id",id);
        List<CommentVoinfo> commentVoinfoList=new ArrayList<CommentVoinfo>();
        try {
            CommentVoinfo commentVoinfo=new CommentVoinfo();
            commentVoinfo.setId(id);
            commentVoinfoList = commentService.selectAll(commentVoinfo,pagn);
            model.put("commentVoinfoList", commentVoinfoList);
            logger.info("获取评论详情成功");
            return "commentdetail";
        }
        catch (Exception e){
            e.printStackTrace();
            logger.warning("获取评论详情出错了");
            return null;
        }
    }

    /**
     * 更新评论
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/updatecomment")
    @ResponseBody
    public String updatecomment(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String id=req.getParameter("id");
        String state=req.getParameter("state");
        String iscommend=req.getParameter("iscommend");
        Commentinfo commentinfo=new Commentinfo();
        commentinfo.setId(Integer.parseInt(id));
        if(state!=null&&!"".equals(state))
            commentinfo.setState(Integer.parseInt(state));
        if(iscommend!=null&&!"".equals(iscommend))
            commentinfo.setIscommend(Integer.parseInt(iscommend));
        try{
            Integer flag=commentService.updateByPrimaryKey(commentinfo);
            if(flag==1){
                logger.info("更新评论状态成功");
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200","更新成功", "commentsfresh", "", "", ""));
            }
        }
        catch (Exception e){
            logger.warning("更新评论状态失败"+e.getMessage());
            e.printStackTrace();
        }
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","更新失败", "commentsfresh", "", "", ""));
    }

    /**
     * 删除评论
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     * @throws Exception
     */
    @RequestMapping("/deletecomment")
    @ResponseBody
    public String deletestudent(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn) throws Exception{
        response.setHeader("Access-Control-Allow-Origin",ActionUtil.CrossDomain);
        String id=req.getParameter("id");
        try {
            if (id != null && !"".equals(id)) {
                Integer flag=commentService.deleteByPrimaryKey(Integer.parseInt(id));
                if (flag == 1) {
                    logger.info("删除评论成功");
                    return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200", "评论删除成功", "commentsfresh", "", "closeCurrent", ""));
                }
            }
            else{
                return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "请选择一条记录", "commentsfresh", "", "closeCurrent", "")) ;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        logger.warning("删除评论失败");
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300", "评论删除失败", "commentsfresh", "", "closeCurrent", "")) ;
    }
}
