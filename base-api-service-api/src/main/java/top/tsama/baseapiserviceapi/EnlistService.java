package top.tsama.baseapiserviceapi;

import org.springframework.stereotype.Service;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedomain.model.EnlistVoinfo;
import top.tsama.baseapiservicedomain.model.Enlistinfo;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/30.
 */
@Service
public interface EnlistService {
    int deleteByPrimaryKey(Integer id);

    int insert(Enlistinfo record);

    Enlistinfo selectByPrimaryKey(Integer id);

    List<EnlistVoinfo> selectAll(EnlistVoinfo enlistVoinfo, Pagination pagination);

    int updateByPrimaryKey(Enlistinfo record);
}


