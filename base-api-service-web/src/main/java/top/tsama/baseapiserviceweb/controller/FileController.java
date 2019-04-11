package top.tsama.baseapiserviceweb.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.tsama.baseapiserviceapi.FileService;
import top.tsama.baseapiservicecommon.ActionUtil;
import top.tsama.baseapiservicecommon.JsonUtil;
import top.tsama.baseapiservicedomain.model.Fileinfo;
import top.tsama.baseapiservicedomain.model.Imginfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Created by wangdaren on 2018/1/23.
 */
@Controller
public class FileController {
    Logger logger=Logger.getLogger(FileController.class.getName());
    @Autowired
    public FastFileStorageClient fastFileStorageClient;
    @Autowired
    public FileService fileService;

    /**
     * 上传文件（图片）
     * @param
     * @param req
     * @param resp
     * @return
     * @throws IOException
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public String uploadImg(@RequestParam("uploadFile") MultipartFile uploadFile,HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        //String fileurl=req.getParameter("uploadFile");
        //File file=new File("C://Users//wangdaren//Desktop//1.jpg");
        //String l=uploadFile.getOriginalFilename();
      /*  byte[] bytes = null;
        try {
            bytes = uploadFile.getBytes(); //将文件转换成字节流形式
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*FileInputStream inputStream=new FileInputStream(file);*/
        try
        {
         //   String fileName=file.getName();
            String filename=uploadFile.getOriginalFilename();
            String strs= filename.substring(filename.lastIndexOf(".") + 1);  ;
            if(!StringUtils.hasText(strs)){
                return "fail";
            }
            StorePath storePath = fastFileStorageClient.uploadFile((InputStream)uploadFile.getInputStream(),uploadFile.getSize(), FilenameUtils.getExtension(uploadFile.getOriginalFilename()),null);
        //    StorePath storePath= fastFileStorageClient.uploadImageAndCrtThumbImage(inputStream,file.length(),strs,null);
            Fileinfo fileinfo=new Fileinfo();
            fileinfo.setUrl(storePath.getFullPath());
            Integer flag= fileService.insert(fileinfo);
            if(flag!=0){
                logger.info("上传文件成功");
                fileinfo.setCode(1);
                fileinfo.setId(fileinfo.getId());
                fileinfo.setUrl(ActionUtil.Rooturl+storePath.getFullPath());
                System.out.println("path------"+storePath.getFullPath());
            }
            else {
                logger.warning("上传文件失败");
                fileinfo.setCode(0);
            }
            logger.info("上传文件成功");
            return JsonUtil.toJSON(fileinfo).toString();
        }
        catch (Exception e){
            logger.warning("上传文件失败"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("delete.do")
    @ResponseBody
    public String deleteImg(){
        fastFileStorageClient.deleteFile("group1/M00/00/00/wKiJylksOiiAUWV3AABNeUASSJQ019_150x150.png");
        return "success";
    }
    @RequestMapping("open.do")
    public String open(){
       return "uploadfile";
    }

    /**
     * 上传文件（图片）
     * @param
     * @param req
     * @param resp
     * @return
     * @throws IOException
     */
    @RequestMapping("uploadImg.do")
    @ResponseBody
    public String upload(@RequestParam("filedata") MultipartFile filedata,HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", ActionUtil.CrossDomain);
        //String fileurl=req.getParameter("uploadFile");
        //File file=new File("C://Users//wangdaren//Desktop//1.jpg");
        //String l=uploadFile.getOriginalFilename();
      /*  byte[] bytes = null;
        try {
            bytes = uploadFile.getBytes(); //将文件转换成字节流形式
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*FileInputStream inputStream=new FileInputStream(file);*/
        Imginfo imginfo=new Imginfo();
        try
        {
            //   String fileName=file.getName();
            String filename=filedata.getOriginalFilename();
            String strs= filename.substring(filename.lastIndexOf(".") + 1);  ;
            if(!StringUtils.hasText(strs)){
                return "fail";
            }
            StorePath storePath = fastFileStorageClient.uploadFile((InputStream)filedata.getInputStream(),filedata.getSize(), FilenameUtils.getExtension(filedata.getOriginalFilename()),null);
            //    StorePath storePath= fastFileStorageClient.uploadImageAndCrtThumbImage(inputStream,file.length(),strs,null);
            Fileinfo fileinfo=new Fileinfo();
            fileinfo.setUrl(storePath.getFullPath());
            Integer flag= fileService.insert(fileinfo);
            if(flag!=0){
                logger.info("上传文件成功");
                fileinfo.setCode(1);
                fileinfo.setId(fileinfo.getId());
                fileinfo.setUrl(ActionUtil.Rooturl+storePath.getFullPath());
                System.out.println("path------"+storePath.getFullPath());
            }
            else {
                logger.warning("上传文件失败");
                fileinfo.setCode(0);
            }
            logger.info("上传文件成功");
            imginfo.setMsg(ActionUtil.Rooturl+storePath.getFullPath());
            return JsonUtil.toJSON(imginfo).toString();
        }
        catch (Exception e){
            logger.warning("上传文件失败"+e.getMessage());
            e.printStackTrace();
        }
        imginfo.setErr("上传失败");
        return JsonUtil.toJSON(imginfo).toString();
    }

}
