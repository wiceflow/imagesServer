package cn.sibat.iceflow.image.server.controller;

import cn.sibat.iceflow.image.server.service.FileUploadService;
import cn.sibat.iceflow.image.server.util.AjaxResult;
import cn.sibat.iceflow.image.server.util.Constants;
import cn.sibat.iceflow.image.server.util.FileCheck;
import cn.sibat.iceflow.image.server.util.Status;
import cn.sibat.iceflow.image.server.vo.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author BF
 * @date 2019/11/5 14:42
 * <p>
 * video 上传控制层
 */
@RestController
@RequestMapping("video")
public class VideoUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 保存路径
     */
    @Value("#{system['video.path']}")
    private String videoPath;

    @PostMapping("uploads/{projectName}")
    public final AjaxResult uploadVideo(@RequestParam MultipartFile[] videoFiles,
                                        @PathVariable(value = "projectName", required = false) String projectName,
                                        @RequestParam(value = "otherPath", required = false) List<String> structure) {
        if (videoFiles == null || videoFiles.length <= 0){
            return AjaxResult.customResponse(Status.PARA_ERROR,"上传的视屏为空",null);
        }else if (projectName == null){
            return AjaxResult.customResponse(Status.PARA_ERROR,"请输入项目名",null);
        }
        FileCheck.CHECK.checkFile(videoFiles, Constants.VIDEO_PATH);
        List<FileVO> fileVOS = fileUploadService.uploadImages(videoFiles,projectName,structure, videoPath);
        return AjaxResult.okResponse(fileVOS);
    }
}
