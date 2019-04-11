package top.tsama.baseapiserviceservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tsama.baseapiserviceapi.ManagementService;
import top.tsama.baseapiservicedao.mapper.ManagementinfoMapper;
import top.tsama.baseapiservicedomain.model.Managementinfo;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/30.
 */
@Service
public class ManagementServiceImpl implements ManagementService {
    @Autowired
    private ManagementinfoMapper managementinfoMapper;
    public List<Managementinfo> selectAll(Managementinfo managementinfo){
        List<Managementinfo> managementinfos=managementinfoMapper.selectAll(managementinfo);
        if(managementinfos!=null){
            return managementinfos;
        }
        else {
            return null;
        }
    }
    public  int updateByPrimaryKey(Managementinfo record){
        int flag=managementinfoMapper.updateByPrimaryKey(record);
        if(flag==1){
            return 1;
        }
        else{
            return 0;
        }
    }
}
