package top.tsama.baseapiservicedao.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import top.tsama.baseapiservicedomain.model.EnlistVoinfo;
import top.tsama.baseapiservicedomain.model.Enlistinfo;
@Component
public interface EnlistinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Enlistinfo record);

    Enlistinfo selectByPrimaryKey(Integer id);

    List<EnlistVoinfo> selectAll(EnlistVoinfo enlistVoinfo);

    int updateByPrimaryKey(Enlistinfo record);
}