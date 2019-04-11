package top.tsama.baseapiserviceservices;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tsama.baseapiserviceapi.CourseService;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedao.mapper.CourseinfoMapper;
import top.tsama.baseapiservicedomain.model.CourseVoinfo;
import top.tsama.baseapiservicedomain.model.Courseinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/27.
 */
@Service
public class CourseServiceImpl implements CourseService {
    Logger logger=Logger.getLogger(CourseServiceImpl.class.getName());
    @Autowired
    public CourseinfoMapper courseinfoMapper;
    public int deleteByPrimaryKey(Integer id){
        try {
            int flag=courseinfoMapper.deleteByPrimaryKey(id);
            if(flag==1){
                return 1;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int insert(Courseinfo record){
        try {
            Integer flag = courseinfoMapper.insert(record);
            if(flag==1){
                logger.info("插入课程信息成功");
                return 1;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logger.warning("插入课程出错了");
        }
        return 0;
    }

    public Courseinfo selectByPrimaryKey(Integer id){
        return null;
    }

    public List<CourseVoinfo> selectAll(CourseVoinfo courseVoinfo,Pagination pagination){
        PageHelper.startPage(pagination.getPageNum(),pagination.getNumPerPage());
        List<CourseVoinfo> courseVoinfoList=new ArrayList<CourseVoinfo>();
        try{
            courseVoinfoList=courseinfoMapper.selectAll(courseVoinfo);
            if(courseVoinfoList!=null){
                return courseVoinfoList;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int updateByPrimaryKey(Courseinfo record){
        try {
            int flag=courseinfoMapper.updateByPrimaryKey(record);
            if(flag==1){
                return 1;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
