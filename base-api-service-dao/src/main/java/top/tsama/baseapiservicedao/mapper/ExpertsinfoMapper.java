package top.tsama.baseapiservicedao.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import top.tsama.baseapiservicedomain.model.Expert_Fileinfo;
import top.tsama.baseapiservicedomain.model.Expertsinfo;
@Component
public interface ExpertsinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Expertsinfo record);

    List<Expert_Fileinfo>  selectByPrimaryKey(Expert_Fileinfo expert_fileinfo);

    List<Expert_Fileinfo> selectAll();

    int updateByPrimaryKey(Expertsinfo record);
}