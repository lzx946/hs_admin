package top.tsama.baseapiserviceapi;

import org.springframework.stereotype.Service;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedomain.model.CourseVoinfo;
import top.tsama.baseapiservicedomain.model.Courseinfo;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/27.
 */
@Service
public interface CourseService {
    public int deleteByPrimaryKey(Integer id);    //按id删除课程

    public int insert(Courseinfo record);    //插入课程

    public Courseinfo selectByPrimaryKey(Integer id);   //按id查询课程

    public List<CourseVoinfo> selectAll(CourseVoinfo courseVoinfo,Pagination pagination);    //获取所有课程

    public int updateByPrimaryKey(Courseinfo record);  //按id更新课程
}
