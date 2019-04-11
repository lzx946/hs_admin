package top.tsama.baseapiserviceservices;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tsama.baseapiserviceapi.StudentService;
import top.tsama.baseapiservicecommon.Pagination;
import top.tsama.baseapiservicedao.mapper.StudentnfoMapper;
import top.tsama.baseapiservicedomain.model.Student_Fileinfo;
import top.tsama.baseapiservicedomain.model.Studentnfo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/25.
 */
@Service
public class StudentServiceImpl implements StudentService {
    Logger logger=Logger.getLogger(StudentServiceImpl.class.getName());
    @Autowired
    public StudentnfoMapper studentnfoMapper;

    public int deleteByPrimaryKey(Integer id){
        try {
            Integer flag=studentnfoMapper.deleteByPrimaryKey(id);
            if(flag==1){
                logger.info("删除某个学员成功");
                return 1;
            }
            else{
                logger.warning("删除某个学员失败");
            }
        }
        catch (Exception e){
                logger.warning("删除某个学员出错了");
        }
        return 0;
    }

    public int insert(Studentnfo record){
        return 0;
    }

    public  List<Student_Fileinfo> selectByPrimaryKey(Student_Fileinfo student_fileinfo,Pagination pagn){
        PageHelper.startPage(pagn.getPageNum(),pagn.getNumPerPage());
        List<Student_Fileinfo> student_file=studentnfoMapper.selectByPrimaryKey(student_fileinfo);
        try {
            if (student_file != null) {
                logger.info("查询某个学员信息成功");
                return student_file;
            }
        }
        catch (Exception e){
            logger.warning("查询某个学员信息出错了");
            e.printStackTrace();
        }
        return null;
    }

    public List<Student_Fileinfo> selectAll(Pagination pagination){
        List<Student_Fileinfo> student_fileinfos=new ArrayList<Student_Fileinfo>();
        PageHelper.startPage(pagination.getPageNum(),pagination.getNumPerPage());
        try{
            student_fileinfos=studentnfoMapper.selectAll();
            if(student_fileinfos!=null){
                logger.info("查询所有学员信息成功");
                return student_fileinfos;
            }
        }
        catch (Exception e){
            logger.warning("查询所有学员信息出错了");
            e.printStackTrace();
        }
        return null;
    }

    public int updateByPrimaryKey(Studentnfo studentnfo){
        try {
            Integer flag = studentnfoMapper.updateByPrimaryKey(studentnfo);
            if(flag==1){
                return 1;
            }
        }
        catch (Exception e){
            e.printStackTrace();;
        }
        return  0;
    }

    public boolean updateBatch(List<Studentnfo> record){
        try{
            boolean flag=studentnfoMapper.updateBatch(record);
            if(flag==true){
                return true;
            }
        }
        catch (Exception e) {
            logger.warning("updateBatch异常"+e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();
        }
        return false;
    }

}
