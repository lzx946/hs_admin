package top.tsama.baseapiserviceapi;

import org.springframework.stereotype.Service;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedomain.model.CommentVoinfo;
import top.tsama.baseapiservicedomain.model.Commentinfo;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/29.
 */
@Service
public interface CommentService  {
    int deleteByPrimaryKey(Integer id);      //按主键id删除评论

    int insert(Commentinfo record);            //插入评论

    Commentinfo selectByPrimaryKey(Integer id);   //按id查询评论

    List<CommentVoinfo> selectAll(CommentVoinfo commentVoinfo, Pagination pagination);    //查询所有评论

    int updateByPrimaryKey(Commentinfo record);    //按主键更新
}
