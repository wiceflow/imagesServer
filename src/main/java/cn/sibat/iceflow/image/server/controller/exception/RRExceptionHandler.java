package cn.sibat.iceflow.image.server.controller.exception;

import cn.sibat.iceflow.image.server.util.AjaxResult;
import cn.sibat.iceflow.image.server.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * @author BF
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public AjaxResult handleRRException(RRException e){
		logger.error(e.getMsg());
		if (e.getCode() != Status.SYS_ERROR){
		    return AjaxResult.customResponse(e.getCode(),e.getMessage(),null);
        }
		return AjaxResult.errorResponse(e.getMsg());
	}


	@ExceptionHandler(DuplicateKeyException.class)
	public AjaxResult handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return AjaxResult.errorResponse("数据库中已存在该记录");
	}

	@ExceptionHandler(Exception.class)
	public AjaxResult handleException(Exception e){
		logger.error(e.getMessage(), e);
		return AjaxResult.errorResponse(e.getMessage());
	}
}
