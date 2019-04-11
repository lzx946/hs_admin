package top.tsama.baseapiserviceservices;


import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tsama.baseapiserviceapi.NewsService;
import top.tsama.baseapiservicedao.mapper.NewsMapper;
import top.tsama.baseapiservicedomain.model.New_Fileinfo;
import top.tsama.baseapiservicedomain.model.News;
import top.tsama.baseapiservicecommon.Pagination;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/18.
 */
@Service
public class NewsServiceImpl implements NewsService{
    @Autowired
    private NewsMapper newsMapper;

    /**
     * 对新闻增删改查
     * @param pagn
     * @return
     */
    public List<New_Fileinfo> selectAll(News news, Pagination pagn) throws Exception{
        PageHelper.startPage(pagn.getPageNum(),pagn.getNumPerPage());
        List<New_Fileinfo> list=newsMapper.selectAll(news);
        try {
            if(list!=null)
                return list;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
    public boolean deleteByPrimaryKey(Integer id){
        Integer flag=newsMapper.deleteByPrimaryKey(id);
        if(flag==1){
            return true;
        }
        return false;
    }
    public boolean insert(News record){
        int flag=newsMapper.insert(record);
        if(flag==1){
            return true;
        }
        return false;
    }

    public New_Fileinfo selectByPrimaryKey(Integer id){
        New_Fileinfo news=newsMapper.selectByPrimaryKey(id);
        if(news!=null){
            return news;
        }
        else {
            return null;
        }
    }


    public boolean updateByPrimaryKey(News record){
        int flag=newsMapper.updateByPrimaryKey(record);
        if(flag==1){
            return true;
        }
        return false;
    }
}
