package top.tsama.baseapiservicedao.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import top.tsama.baseapiservicedomain.model.New_Fileinfo;
import top.tsama.baseapiservicedomain.model.News;

@Component
public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    New_Fileinfo selectByPrimaryKey(Integer id);

    List<New_Fileinfo> selectAll(News news);

    int updateByPrimaryKey(News record);
}