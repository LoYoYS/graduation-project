package com.yuan.Excepetion;

import com.yuan.domain.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// 实体校验异常捕获
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResultData<String> handler(MethodArgumentNotValidException e) {
		BindingResult result = e.getBindingResult();
		ObjectError objectError = result.getAllErrors().stream().findFirst().get();
		log.error("实体校验异常：----------------{}", objectError.getDefaultMessage());
		return ResultData.fail(5000,"error",objectError.getDefaultMessage());
	}

	// 参数异常
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResultData<String> handler(IllegalArgumentException e) {
		log.error("Assert异常：----------------{}", e.getMessage());
		return ResultData.fail(5001,"error",e.getMessage());
	}

	//	运行时异常
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ExceptionHandler(value = RuntimeException.class)
	public ResultData<String> handler(RuntimeException e) {
		log.error("运行时异常：----------------{}", e.getMessage());
		return ResultData.fail(5005,"error","系统异常，请联系技术人员。");
	}

	//自定义异常
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ExceptionHandler(BusinessException.class)
	public  ResultData<String> doBusinessException(Exception e) {
		String str = e.toString().split(":")[0];
		log.error("业务异常消息------------", e.getMessage());
		return ResultData.fail(Integer.parseInt(str),"error",e.getMessage());
	}
}
