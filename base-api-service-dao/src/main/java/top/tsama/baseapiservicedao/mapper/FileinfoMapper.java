package top.tsama.baseapiservicedao.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import top.tsama.baseapiservicedomain.model.Fileinfo;
@Component
public interface FileinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Fileinfo record);

    Fileinfo selectByPrimaryKey(Integer id);

    List<Fileinfo> selectAll();

    int updateByPrimaryKey(Fileinfo record);
}