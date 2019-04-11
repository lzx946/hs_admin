package top.tsama.baseapiservicedao.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import top.tsama.baseapiservicedomain.model.Managementinfo;
@Component
public interface ManagementinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Managementinfo record);

    Managementinfo selectByPrimaryKey(Integer id);

    List<Managementinfo> selectAll(Managementinfo managementinfo);

    int updateByPrimaryKey(Managementinfo record);
}