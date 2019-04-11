package top.tsama.baseapiserviceapi;



import org.springframework.stereotype.Service;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedomain.model.New_Fileinfo;
import top.tsama.baseapiservicedomain.model.News;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/18.
 */
@Service
public interface NewsService {
   boolean deleteByPrimaryKey(Integer id);

    boolean insert(News record);

    New_Fileinfo selectByPrimaryKey(Integer id);

    List<New_Fileinfo> selectAll(News news, Pagination pagn) throws Exception;

    boolean updateByPrimaryKey(News record);
}
