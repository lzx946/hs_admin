package top.tsama.baseapiservicedao.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import top.tsama.baseapiservicedomain.model.TbTest;

@Component
public interface TbTestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbTest record);

    TbTest selectByPrimaryKey(Integer id);

    List<TbTest> selectAll();

    int updateByPrimaryKey(TbTest record);
}