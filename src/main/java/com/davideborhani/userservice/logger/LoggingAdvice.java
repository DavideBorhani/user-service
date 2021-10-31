package com.davideborhani.userservice.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

    Logger log = LoggerFactory.getLogger(LoggingAdvice.class);

    @Pointcut(value="execution(* com.davideborhani.userservice.*.*.*(..) ) && !execution(* com.davideborhani.userservice.exception.RestResponseEntityExceptionHandler.*(..) )")
    public void pointcut(){
    }

    @Around("pointcut()")
    public Object applicationLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        String className = proceedingJoinPoint.getTarget().getClass().toString();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] data = proceedingJoinPoint.getArgs();
        log.info("requested " + className + "." + methodName + "()" + " request : " + objectMapper.writeValueAsString(data));
        Object object = proceedingJoinPoint.proceed();
        log.info("responded " + className + "." + methodName + "()" + " response : " + objectMapper.writeValueAsString(object));
        return object;
    }
}
