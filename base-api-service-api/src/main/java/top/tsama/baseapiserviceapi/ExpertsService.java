package top.tsama.baseapiserviceapi;

import org.springframework.stereotype.Service;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedomain.model.Expert_Fileinfo;
import top.tsama.baseapiservicedomain.model.Expertsinfo;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/22.
 */
@Service
public interface ExpertsService {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(Expertsinfo record);

    List<Expert_Fileinfo> selectByPrimaryKey(Expert_Fileinfo expert_fileinfo,Pagination pagination);

    List<Expert_Fileinfo> selectAll(Pagination pagination);

    boolean updateByPrimaryKey(Expertsinfo record);
}
