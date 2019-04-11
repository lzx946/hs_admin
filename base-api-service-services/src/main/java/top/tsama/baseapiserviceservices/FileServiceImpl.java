package top.tsama.baseapiserviceservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tsama.baseapiserviceapi.FileService;
import top.tsama.baseapiservicedao.mapper.FileinfoMapper;
import top.tsama.baseapiservicedomain.model.Fileinfo;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/23.
 */
@Service
public class FileServiceImpl implements FileService {
    Logger logger= Logger.getLogger(FileServiceImpl.class.getName());
    @Autowired
    public FileinfoMapper fileinfoMapper;
    public boolean deleteByPrimaryKey(Integer id){
        Integer flag=fileinfoMapper.deleteByPrimaryKey(id);
        if(flag==1){
            logger.info("删除文件成功");
            return true;
        }
        else{
            logger.warning("删除文件失败");
            return false;
        }
    }

    public Integer insert(Fileinfo record){
        Integer flag=0;
        flag=fileinfoMapper.insert(record);
        if(flag!=0){
            logger.info("插入文件成功");
            return flag;
        }
        else{
            logger.warning("插入文件失败");
            return 0;
        }
    }

    public Fileinfo selectByPrimaryKey(Integer id){
        Fileinfo fileinfo=fileinfoMapper.selectByPrimaryKey(id);
        if(fileinfo!=null){
            logger.info("查询某一个文件成功");
            return fileinfo;
        }
        else{
            logger.warning("查询某一个文件失败");
            return null;
        }
    }

    public List<Fileinfo> selectAll(){
        List<Fileinfo> fileinfos=fileinfoMapper.selectAll();
        if(fileinfos!=null){
            logger.info("查询所有文件成功");
            return fileinfos;
        }
        else{
            logger.warning("查询所有文件失败");
            return null;
        }
    }

    public boolean updateByPrimaryKey(Fileinfo record){
        return false;
    }
}
