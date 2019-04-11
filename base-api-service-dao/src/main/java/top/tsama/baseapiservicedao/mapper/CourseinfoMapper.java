package top.tsama.baseapiservicedao.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import top.tsama.baseapiservicedomain.model.CourseVoinfo;
import top.tsama.baseapiservicedomain.model.Courseinfo;
@Component
public interface CourseinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Courseinfo record);

    Courseinfo selectByPrimaryKey(Integer id);

    List<CourseVoinfo> selectAll(CourseVoinfo courseVoinfo);

    int updateByPrimaryKey(Courseinfo record);
}