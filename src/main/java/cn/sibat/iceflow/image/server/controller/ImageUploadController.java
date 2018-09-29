package cn.sibat.iceflow.image.server.controller;

import cn.sibat.iceflow.image.server.service.ImageUploadService;
import cn.sibat.iceflow.image.server.util.AjaxResult;
import cn.sibat.iceflow.image.server.util.Status;
import cn.sibat.iceflow.image.server.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ImageUploadService imageUploadService;


    @PostMapping("uploads/{projectName}")
    public final AjaxResult uploadImages(@RequestParam MultipartFile[] imageFiles,
                                         @PathVariable(value = "projectName",required = false)String projectName,
                                         @RequestParam(value = "otherPath",required = false)List<String>structure ){

        if (imageFiles == null || imageFiles.length <= 0){
            return AjaxResult.customResponse(Status.PARA_ERROR,"上传的图片为空",null);
        }else if (projectName == null){
            return AjaxResult.customResponse(Status.PARA_ERROR,"请输入项目名",null);
        }
        List<ImageVO> imageVOS = imageUploadService.uploadImages(imageFiles,projectName,structure);
        return AjaxResult.okResponse(imageVOS);
    }
}
