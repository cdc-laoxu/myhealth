package com.itxiaoxu.utils;

import com.alibaba.druid.sql.visitor.functions.If;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

public class UploadUtil {
    /**
     * 上传文件
     * @param imgFile
     */
    public static void upload(MultipartFile imgFile,String fileName) throws IOException {
        //获取输入流
        InputStream inputStream = imgFile.getInputStream();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream
                (new File("F:\\resources\\img\\" + fileName)));
        BufferedInputStream bis = new BufferedInputStream(inputStream);

       byte [] bys = new byte[1024];
       int len;
       while ((len=bis.read(bys))!=-1){
           bos.write(bys,0,len);
       }

        bis.close();
        bos.close();
    }
   /* public static void approvalFile( MultipartFile filecontent){
        OutputStream os = null;
        InputStream inputStream = null;
        String fileName = null;
        try {
            inputStream = filecontent.getInputStream();
            fileName = filecontent.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String path = "F:\\resources\\img\\";
            // 二、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭全部连接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}
