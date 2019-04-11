package top.tsama.baseapiserviceservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tsama.baseapiserviceapi.DictionaryService;
import top.tsama.baseapiservicedao.mapper.DictionaryinfoMapper;
import top.tsama.baseapiservicedomain.model.Dictionaryinfo;

import java.util.List;

/**
 * Created by wangdaren on 2018/3/12.
 */
@Service
public class DictionaryServiceImpl implements DictionaryService{
    @Autowired
    private DictionaryinfoMapper dictionaryinfoMapper;
    @Override
    public List<Dictionaryinfo> selectByPrimaryKey(Dictionaryinfo dictionaryinfo) {
        List<Dictionaryinfo> dictionaryinfoList=dictionaryinfoMapper.selectByPrimaryKey(dictionaryinfo);
        if(dictionaryinfoList!=null){
            return dictionaryinfoList;
        }
        return null;
    }
}
