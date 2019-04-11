package top.tsama.baseapiserviceservices;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tsama.baseapiserviceapi.ExpertsService;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedao.mapper.ExpertsinfoMapper;
import top.tsama.baseapiservicedomain.model.Expert_Fileinfo;
import top.tsama.baseapiservicedomain.model.Expertsinfo;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/22.
 */
@Service
public class ExpertsServiceImpl  implements ExpertsService{
    Logger logger=Logger.getLogger(ExpertsServiceImpl.class.getName());
    @Autowired
    public ExpertsinfoMapper expertsinfoMapper;

    public boolean deleteByPrimaryKey(Integer id){
        Integer flag=expertsinfoMapper.deleteByPrimaryKey(id);
        try {
            if (flag == 1) {
                logger.info("删除专家信息成功");
                return true;
            }
            else{
                logger.info("删除专家信息失败");
            }
        }
        catch (Exception e){
            logger.warning("删除专家信息出错了");
        }
        return  false;
    }

    public boolean insert(Expertsinfo record){
        Integer flag=expertsinfoMapper.insert(record);
        try {
            if (flag == 1) {
                logger.info("插入专家信息成功");
                return true;
            }
            else{
                logger.info("插入专家信息失败");
            }
        }
        catch (Exception e){
            logger.warning("删除专家信息出错了");
        }
        return false;
    }

    public List<Expert_Fileinfo> selectByPrimaryKey(Expert_Fileinfo expert_fileinfo,Pagination pagination){
        PageHelper.startPage(pagination.getPageNum(),pagination.getNumPerPage());
        List<Expert_Fileinfo> expert_fileinfos=expertsinfoMapper.selectByPrimaryKey(expert_fileinfo);
        try{
            if(expert_fileinfos!=null){
                logger.info("查询专家信息成功");
                return expert_fileinfos;
            }
            else{
                logger.info("查询专家信息失败");
            }
        }
        catch (Exception e){
            logger.warning("查询专家信息出错了");
            e.printStackTrace();
        }
        return  null;
    }

    public List<Expert_Fileinfo> selectAll(Pagination pagination){
        PageHelper.startPage(pagination.getPageNum(),pagination.getNumPerPage());
        List<Expert_Fileinfo> expert_fileinfos=expertsinfoMapper.selectAll();
        try{
            if(expert_fileinfos!=null){
                logger.info("查询专家信息成功");
                return expert_fileinfos;
            }
            else{
                logger.info("查询专家信息失败");
            }
        }
        catch (Exception e){
            logger.warning("查询专家信息出错了");
            e.printStackTrace();
        }
        return  null;
    }

    public boolean updateByPrimaryKey(Expertsinfo record){
        Integer flag=expertsinfoMapper.updateByPrimaryKey(record);
        try {
            if (flag == 1) {
                logger.info("更新专家信息成功");
                return true;
            }
            else{
                logger.info("更新专家信息失败");
            }
        }
        catch (Exception e){
            logger.warning("更新专家信息出错了");
        }
        return false;
    }
}
