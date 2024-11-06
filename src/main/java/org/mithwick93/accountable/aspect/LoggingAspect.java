package org.mithwick93.accountable.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mithwick93.accountable.util.JsonUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(org.mithwick93.accountable..*)" + " && within(@org.springframework.web.bind.annotation.RestController *)")
    @lombok.Generated
    public void controllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut("within(org.mithwick93.accountable..*)" + " && within(@org.springframework.web.bind.annotation.RestControllerAdvice *)")
    @lombok.Generated
    public void controllerAdvisorPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs when a controller method is entered.
     *
     * @param joinPoint Join point for advice
     */
    @Before("controllerPointcut()")
    public void beforeEndpoint(JoinPoint joinPoint) throws JsonProcessingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info(
                "[Request] Endpoint: [{}] {}, Params: {}, Args: {}",
                request.getMethod(),
                request.getRequestURI(),
                JsonUtil.getJsonString(request.getParameterMap()),
                Arrays.toString(joinPoint.getArgs())
        );
    }

    /**
     * Advice that logs when a controller method is exited.
     */
    @AfterReturning(pointcut = "controllerPointcut()", returning = "returnValue")
    public void afterEndpoint(Object returnValue) throws JsonProcessingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info(
                "[Response] Endpoint: [{}] {}, Returned: {}",
                request.getMethod(),
                request.getRequestURI(),
                JsonUtil.getJsonString(returnValue)
        );
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param e Exception
     */
    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "e")
    public void afterThrow(Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.error(
                "[Response Exception] Endpoint: [{}] {}, Exception: {}, Message: {}",
                request.getMethod(),
                request.getRequestURI(),
                e.getClass().getSimpleName(),
                e.getMessage(),
                e
        );
    }

    /**
     * Advice that logs when a controller advice method is exited.
     */
    @AfterReturning(pointcut = "controllerAdvisorPointcut()", returning = "returnValue")
    public void afterExceptionHandle(Object returnValue) throws JsonProcessingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.error(
                "[Exception Handler] Endpoint: [{}] {}, Returned: {}",
                request.getMethod(),
                request.getRequestURI(),
                JsonUtil.getJsonString(returnValue)
        );
    }
}
