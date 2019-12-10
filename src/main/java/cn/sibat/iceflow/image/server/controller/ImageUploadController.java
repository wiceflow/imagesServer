package cn.sibat.iceflow.image.server.controller;

import cn.sibat.iceflow.image.server.controller.interceptor.GzipWriter;
import cn.sibat.iceflow.image.server.service.FileUploadService;
import cn.sibat.iceflow.image.server.util.AjaxResult;
import cn.sibat.iceflow.image.server.util.Status;
import cn.sibat.iceflow.image.server.vo.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author iceflow
 * @date 2018/8/1
 * 上传控制层
 */
@RestController
@RequestMapping("images")
public class ImageUploadController extends AbstractController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 保存路径
     */
    @Value("#{system['image.path']}")
    private String imagePath;

    @GzipWriter
    @PostMapping("uploads/{projectName}")
    public final AjaxResult uploadImages(@RequestParam MultipartFile[] imageFiles,
                                         @PathVariable(value = "projectName",required = false)String projectName,
                                         @RequestParam(value = "otherPath",required = false)List<String>structure ){

        if (imageFiles == null || imageFiles.length <= 0){
            return AjaxResult.customResponse(Status.PARA_ERROR,"上传的图片为空",null);
        }else if (projectName == null){
            return AjaxResult.customResponse(Status.PARA_ERROR,"请输入项目名",null);
        }
        List<FileVO> fileVOS = fileUploadService.uploadImages(imageFiles,projectName,structure,imagePath);
        return AjaxResult.okResponse(fileVOS);
    }


}
