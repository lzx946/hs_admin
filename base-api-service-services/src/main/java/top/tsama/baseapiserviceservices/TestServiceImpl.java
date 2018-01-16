package top.tsama.baseapiserviceservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tsama.baseapiserviceapi.TestService;
import top.tsama.baseapiservicedao.mapper.TbTestMapper;
import top.tsama.baseapiservicedomain.model.TbTest;

import java.util.List;

@Service
public class TestServiceImpl implements TestService{

    @Autowired
    TbTestMapper tbTestMapper;


    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    public int insert(TbTest record) {
        return 0;
    }

    public TbTest selectByPrimaryKey(Integer id) {
        return null;
    }

    public List<TbTest> selectAll() {
        return tbTestMapper.selectAll();
    }

    public int updateByPrimaryKey(TbTest record) {
        return 0;
    }
}
