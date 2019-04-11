package top.tsama.baseapiservicedao.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import top.tsama.baseapiservicedomain.model.Dictionaryinfo;
@Component
public interface DictionaryinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dictionaryinfo record);

    List<Dictionaryinfo> selectByPrimaryKey(Dictionaryinfo dictionaryinfo);

    List<Dictionaryinfo> selectAll();

    int updateByPrimaryKey(Dictionaryinfo record);
}