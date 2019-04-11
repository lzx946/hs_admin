package top.tsama.baseapiserviceservices;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tsama.baseapiserviceapi.EnlistService;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedao.mapper.EnlistinfoMapper;
import top.tsama.baseapiservicedomain.model.EnlistVoinfo;
import top.tsama.baseapiservicedomain.model.Enlistinfo;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/30.
 */
@Service
public class EnlistServiceImpl implements EnlistService {
    @Autowired
    private EnlistinfoMapper enlistinfoMapper;
    public int deleteByPrimaryKey(Integer id){
        int flag=enlistinfoMapper.deleteByPrimaryKey(id);
        if(flag==1){
            return 1;
        }
        return 0;
    }

    public  int insert(Enlistinfo record){
        int flag=enlistinfoMapper.insert(record);
        if(flag==1){
            return 1;
        }
        return 0;
    }

    public  Enlistinfo selectByPrimaryKey(Integer id){
        return null;
    }

    public List<EnlistVoinfo> selectAll(EnlistVoinfo enlistVoinfo, Pagination pagination){
        PageHelper.startPage(pagination.getPageNum(),pagination.getNumPerPage());
        List<EnlistVoinfo> enlistVoinfos=enlistinfoMapper.selectAll(enlistVoinfo);
        if(enlistVoinfos!=null){
            return enlistVoinfos;
        }
        return null;
    }

    public int updateByPrimaryKey(Enlistinfo record){
      Integer flag=enlistinfoMapper.updateByPrimaryKey(record);
      if(flag==1){
          return 1;
      }
      return 0;
    }

}
