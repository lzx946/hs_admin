package top.tsama.baseapiserviceweb.controller;

import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.ir.IdentNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tsama.baseapiserviceapi.NewsService;
import top.tsama.baseapiservicecommon.ActionUtil;
import top.tsama.baseapiservicecommon.JsonUtil;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedomain.model.New_Fileinfo;
import top.tsama.baseapiservicedomain.model.News;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/18.
 */
@Controller
public class NewController {
    Logger logger=Logger.getLogger(NewController.class.getName());
    @Autowired
    private NewsService newsService;

    /**
     * 获取新闻列表
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/getnewslist")
    public String news(HttpServletRequest req, HttpServletResponse response, ModelMap model, Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        String title=req.getParameter("title");
        News news=new News();
        if(title!=null&&!"".equals(title)){
            news.setTitle(title);
            model.put("title",title);
        }
        try {
            List<New_Fileinfo> newslist = newsService.selectAll(news,pagn);
            if(newslist.size()>0) {
                for (int i = 0; i < newslist.size(); i++) {
                    if (newslist.get(i).getTitle().length() > 10) {
                        newslist.get(i).setTitleStr(newslist.get(i).getTitle().substring(0, 10));
                    } else {
                        newslist.get(i).setTitleStr(newslist.get(i).getTitle());
                    }
                    if (newslist.get(i).getContent().length() > 25) {
                        newslist.get(i).setContentStr(newslist.get(i).getContent().substring(0, 25));
                    } else {
                        newslist.get(i).setContentStr(newslist.get(i).getContent());
                    }
                }
            }
            PageInfo<New_Fileinfo> page = new PageInfo<New_Fileinfo>(newslist);
            model.put("newslist", newslist);
            model.put("page", page);
            logger.info("获取新闻列表成功");
            return "news";
        }
        catch (Exception e){
            e.printStackTrace();
            logger.warning("获取新闻列表失败"+e.getMessage());
        }
        return null;
    }

    /**
     * 删除新闻
     * @param req
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/deletenew")
    @ResponseBody
    public String deletenew(HttpServletRequest req, HttpServletResponse response, ModelMap model){
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        Integer id=Integer.parseInt(req.getParameter("id"));
        boolean flag=newsService.deleteByPrimaryKey(id);
        if(flag==true){
            logger.info("删除新闻成功");
            return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200","成功", "", "", "", ""));
        }
        logger.warning("删除新闻失败");
        return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","失败", "", "", "", ""));
    }

    /**
     * 添加新闻界面
     * @param req
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/addnews")
    public String addnews(HttpServletRequest req, HttpServletResponse response, ModelMap model){
       return "addnews";
    }

    /**
     * 添加新闻
     * @param req
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/addnewsdetail")
    @ResponseBody
    public String addnewsdetail(HttpServletRequest req, HttpServletResponse response, ModelMap model){
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        String pic=req.getParameter("pic");
        String title=req.getParameter("title");
        String content=req.getParameter("content");
        News news=new News();
        news.setTitle(title);
        news.setContent(content);
        news.setPhotoid(Integer.parseInt(pic));
        news.setCreatetime(new Date());
        news.setState(0);
        boolean flag=newsService.insert(news);
        if(flag==true){
            logger.info("添加新闻成功");
            return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200","新闻发布成功", "newsfresh", "", "closeCurrent", ""));
        }
        else{
            logger.warning("添加新闻失败");
            return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","新闻发布失败", "newsfresh", "", "closeCurrent", ""));
        }

    }

    /**
     * 新闻详情
     * @param req
     * @param response
     * @param model
     * @param pagn
     * @return
     */
    @RequestMapping("/getnewsdetail")
    public String getexpertdetail(HttpServletRequest req, HttpServletResponse response, ModelMap model,Pagination pagn){
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        String flag=req.getParameter("flag");
        Integer id=Integer.parseInt(req.getParameter("id"));
        model.put("id",id);
        model.put("flag",Integer.parseInt(flag));
        try {
            New_Fileinfo newsList = newsService.selectByPrimaryKey(id);
            model.put("title", newsList.getTitle());
            model.put("content",newsList.getContent());
            model.put("createtime",newsList.getCreatetime());
            model.put("url",ActionUtil.Rooturl+newsList.getUrl());
            logger.info("查询新闻详情成功");
            return "newdetail";
        }
        catch (Exception e){
            e.printStackTrace();
            logger.warning("查询新闻详情失败"+e.getMessage());
            return null;
        }
    }

    /**
     * 更新新闻信息
     * @param req
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/updatenew")
    @ResponseBody
    public String updatecourse(HttpServletRequest req, HttpServletResponse response, ModelMap model){
        response.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        String id=req.getParameter("id");
        String title=req.getParameter("title");
        String content=req.getParameter("content");
        String state=req.getParameter("state");
        String iscommend=req.getParameter("iscommend");
        News news=new News();
        news.setId(Integer.parseInt(id));
        if(title!=null) {
            news.setTitle(title);
        }
        if(content!=null) {
            news.setContent(content);
        }
        if(iscommend!=null&&!"".equals(iscommend)) {
            news.setIscommend(Integer.parseInt(iscommend));
        }
        if(state==null||"".equals(state)) {
            news.setState(0);
        }
        else if("-1".equals(state)){
        }
        else {
            news.setState(Integer.parseInt(state));
        }
        boolean flag=newsService.updateByPrimaryKey(news);
        if(flag==true){
            logger.info("更新新闻信息成功");
            return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("200","更新新闻成功", "newsfresh", "", "closeCurrent", ""));
        }
        else{
            logger.warning("更新新闻信息失败");
            return JsonUtil.toJSONString(ActionUtil.getAjaxToMap("300","更新新闻失败", "newsfresh", "", "closeCurrent", ""));
        }
    }
}
