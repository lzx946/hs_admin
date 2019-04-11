package top.tsama.baseapiservicedao.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import top.tsama.baseapiservicedomain.model.CommentVoinfo;
import top.tsama.baseapiservicedomain.model.Commentinfo;
@Component
public interface CommentinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Commentinfo record);

    Commentinfo selectByPrimaryKey(Integer id);

    List<CommentVoinfo> selectAll(CommentVoinfo commentVoinfo);

    int updateByPrimaryKey(Commentinfo record);
}