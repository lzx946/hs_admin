package top.tsama.baseapiserviceservices;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tsama.baseapiserviceapi.CommentService;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedao.mapper.CommentinfoMapper;
import top.tsama.baseapiservicedomain.model.CommentVoinfo;
import top.tsama.baseapiservicedomain.model.Commentinfo;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/29.
 */
@Service
public class CommentServiceImpl implements CommentService {
    Logger logger=Logger.getLogger(CommentServiceImpl.class.getName());
    @Autowired
    private CommentinfoMapper commentinfoMapper;
   public int deleteByPrimaryKey(Integer id){
       Integer flag=commentinfoMapper.deleteByPrimaryKey(id);
       if(flag==1){
           logger.info("删除评论成功");
           return 1;
       }
       else {
           logger.warning("删除评论失败");
           return 0;
       }
   }

    public int insert(Commentinfo record){
       return 0;
    }

    public Commentinfo selectByPrimaryKey(Integer id){
        return null;
    }

    public List<CommentVoinfo> selectAll(CommentVoinfo commentVoinfo, Pagination pagination){
        PageHelper.startPage(pagination.getPageNum(),pagination.getNumPerPage());
        List<CommentVoinfo> commentVoinfoList=commentinfoMapper.selectAll(commentVoinfo);
        if(commentVoinfoList!=null){
            logger.info("查询评论成功");
            return commentVoinfoList;
        }
        else{
            logger.warning("查询评论失败");

        }
        return  null;
    }

    public int updateByPrimaryKey(Commentinfo record){
        Integer flag=commentinfoMapper.updateByPrimaryKey(record);
        if(flag==1){
            logger.info("更新评论成功");
            return 1;
        }
        else{
            logger.warning("更新评论失败");
            return 0;
        }
    }
}
