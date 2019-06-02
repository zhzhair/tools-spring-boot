package com.example.demo.common.exception;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice // 加强Controller
public class GlobalExceptionHandler {

	// https://jira.spring.io/browse/SPR-14651
	// 4.3.5 supports RedirectAttributes redirectAttributes	注: Model/ModelMap亲求转发带参数
	@ExceptionHandler(Exception.class) // 全局捕获异常
	public ModelAndView handleError(Exception e) {
		ModelAndView redirectAttributes = new ModelAndView();
		redirectAttributes.setViewName("error");
		if(e instanceof MultipartException){
			redirectAttributes.addObject("message", e.getMessage());
		}else if(e instanceof BusinessException){
			redirectAttributes.addObject("message", "错误信息：" + e.getMessage());
		}else{
			redirectAttributes.addObject("message", e.getMessage());
		}
		return redirectAttributes;
	}

}
