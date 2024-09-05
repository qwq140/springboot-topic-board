package com.pjb.topicboard.global.aop;

import com.pjb.topicboard.global.exception.CustomValidationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class ValidationAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping(){}

    @Around("postMapping() || putMapping()")
    public Object validate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.debug("ValidationAspect.validate 동작");
        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg : args) {
            if(arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if(bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패",errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }
}
