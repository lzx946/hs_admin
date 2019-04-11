package top.tsama.baseapiserviceapi;

import org.springframework.stereotype.Service;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedomain.model.Student_Fileinfo;
import top.tsama.baseapiservicedomain.model.Studentnfo;

import java.util.List;

/**
 * Created by wangdaren on 2018/1/25.
 */
@Service
public interface StudentService {
    int deleteByPrimaryKey(Integer id);

    int insert(Studentnfo record);

    List<Student_Fileinfo> selectByPrimaryKey(Student_Fileinfo student_fileinfo,Pagination pagination);

    List<Student_Fileinfo> selectAll(Pagination pagn);

    int updateByPrimaryKey(Studentnfo record);

    public boolean updateBatch(List<Studentnfo> list)throws Exception;
}
