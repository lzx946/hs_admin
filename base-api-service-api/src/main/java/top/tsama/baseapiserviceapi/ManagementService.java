package top.tsama.baseapiserviceapi;

import org.springframework.stereotype.Service;
import top.tsama.baseapiservicedomain.model.Managementinfo;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/30.
 */
@Service
public interface ManagementService {
    List<Managementinfo> selectAll(Managementinfo managementinfo);
    int updateByPrimaryKey(Managementinfo record);
}
