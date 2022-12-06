package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo.common.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Value("${server.port}")
    private String port;

    private static  final  String ip = "http://localhost";

    /**
     * 上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Response<?> upload(MultipartFile file) throws IOException {
        String originalFilename=file.getOriginalFilename(); //获取源文件名称
        //定义文件的唯一标识（前缀）为flag，hutool里的工具类
        //防止相同的文件名，不同的文件
        String flag= IdUtil.fastSimpleUUID();
        String rootFilePath=System.getProperty("user.dir")+"/springboot/src/main/resources/files/"+ flag + "_" + originalFilename;  //获取上传的路径
        FileUtil.writeBytes(file.getBytes(),rootFilePath); //把文件写入上传的路径
        return Response.success(ip+":"+port+"/files/"+flag);  //返回结果 url，这个url打开后可以下载这个文件
    }

    /**
     * 下载接口
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")
    public void getfiles(@PathVariable String flag,HttpServletResponse response){
        OutputStream os; //新建一个输出流对象
        String basePath=System.getProperty("user.dir")+ "/springboot/src/main/resources/files/"; //定义文件下载根路径
        List<String> fileNames=FileUtil.listFileNames(basePath); // 获取根路径下所有文件的名称
        String fileName=fileNames.stream().filter(name->name.contains(flag)).findAny().orElse(""); //找到跟参数一致的文件
        try{
            if(StrUtil.isNotEmpty(fileName)){
                response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes=FileUtil.readBytes(basePath+fileName); //通过文件路径读取文件字节流
                os=response.getOutputStream();  //通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e){
            System.out.println("文件下载失败");
        }
    }
}
