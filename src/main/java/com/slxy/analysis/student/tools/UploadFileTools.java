package com.slxy.analysis.student.tools;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/*
上传文件类
 */
public class UploadFileTools {

    /*
    path：上传文件路径
     */

    public static String uploadfile(HttpServletRequest req,String path)throws IOException {

        //获得解析器
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
       //检查form是否有entype
        if(commonsMultipartResolver.isMultipart(req)){
            MultipartHttpServletRequest mreq= (MultipartHttpServletRequest) req;
            Iterator<String> fileNames = mreq.getFileNames();
            while(fileNames.hasNext()){
                MultipartFile file = mreq.getFile(fileNames.next().toString());
                if(file!=null){
                    path=path+"a.xls";
                    file.transferTo(new File(path));
                }
            }
        }
        return path;
    }
}
