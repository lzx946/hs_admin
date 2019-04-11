package top.tsama.baseapiserviceapi;

import org.springframework.stereotype.Service;
import top.tsama.baseapiservicedomain.model.Dictionaryinfo;

import java.util.List;

/**
 * Created by wangdaren on 2018/3/12.
 */
@Service
public interface DictionaryService {
    /**
     * 根据字典字段查询字典
     * @param dictionaryinfo
     * @return
     */
    List<Dictionaryinfo> selectByPrimaryKey(Dictionaryinfo dictionaryinfo);
}
