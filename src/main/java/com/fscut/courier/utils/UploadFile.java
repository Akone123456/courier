package com.fscut.courier.utils;

import com.fscut.courier.system.Sys;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;


/**
 *  @author jf3q.com
 *
 */
public class UploadFile {


	 public static String upimg(MultipartFile file, HttpServletRequest request, String package1) {

    	 String format=file.getContentType().split("/")[1];
         String path = request.getSession().getServletContext().getRealPath(Sys.Upimg.parentPack+package1);

         File pf=new File(path);
         if(!pf.exists()){
        	 pf.mkdirs();
         }

         String fileName = UUID.randomUUID().toString().replace("-", "").substring(1, 11)+"."+format;

         File targetFile = new File(path, fileName);
         try {
             file.transferTo(targetFile);

         } catch (Exception e) {
             e.printStackTrace();
         }
        return   Sys.Upimg.parentPack+package1+fileName  ;
     }

     public static void deleteFile(HttpServletRequest request, String path){
		 String abPath= request.getSession().getServletContext().getRealPath("")+"/"+path;
		 File f=new File(abPath);
		 if(f.exists())f.delete();

     }

    /**
     * 上传汽车多文件
     * @param pictures
     * @return
     */
    public static String save(List<MultipartFile> pictures, HttpServletRequest request, String uppath){
        String path = request.getSession().getServletContext().getRealPath(uppath);
        StringBuffer sb = new StringBuffer();

        try{

            for (MultipartFile picture : pictures) {
                String fileName=picture.getOriginalFilename();
                String format=fileName.substring(fileName.lastIndexOf("."),fileName.length());
                // 截取文件的扩展名(如.jpg)
                String oriName = UUID.randomUUID().toString().replace("-", "").substring(1, 11)+format;
                sb.append(oriName+"#");
                File file = new File(path,oriName);

                if(!file.exists()){ file.createNewFile();}
                // 保存文件
                picture.transferTo(file);
//                System.out.println(file.getAbsolutePath()+"-------->");
            }
            if (sb.length()>0){
                sb.deleteCharAt(sb.length()-1);
            }
        }
        catch (Exception e) {
            return "-1";
        }


        return sb.toString();
    }





}

