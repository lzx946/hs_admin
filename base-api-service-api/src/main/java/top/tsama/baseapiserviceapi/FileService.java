package top.tsama.baseapiserviceapi;

import org.springframework.stereotype.Service;
import top.tsama.baseapiservicedomain.model.Fileinfo;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/23.
 */
@Service
public interface FileService {
    boolean deleteByPrimaryKey(Integer id);

    Integer insert(Fileinfo record);

    Fileinfo selectByPrimaryKey(Integer id);

    List<Fileinfo> selectAll();

    boolean updateByPrimaryKey(Fileinfo record);
}
