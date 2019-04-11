package top.tsama.baseapiservicedao.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import top.tsama.baseapiservicedomain.model.Student_Fileinfo;
import top.tsama.baseapiservicedomain.model.Studentnfo;
@Component
public interface StudentnfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Studentnfo record);

    List<Student_Fileinfo> selectByPrimaryKey(Student_Fileinfo student_fileinfo);

    List<Student_Fileinfo> selectAll();

    int updateByPrimaryKey(Studentnfo record);
    /**
     * 批量更新审核状态
     * @param list
     * @return
     * @throws Exception
     */
    public boolean updateBatch(List<Studentnfo> list)throws Exception;
}