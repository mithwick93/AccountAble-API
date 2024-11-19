package org.mithwick93.accountable.aspect;

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
public final class LoggingAspect {
    private static final String MASK = "***";

    private static boolean isSensitiveEndpoint(HttpServletRequest request) {
        return request.getRequestURI().contains("/login")
                || request.getRequestURI().contains("/register");
    }

    private static void executeWithExceptionHandling(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            log.error("An error occurred while executing the logger aspect: {}", e.getMessage(), e);
        }
    }

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
    public void beforeEndpoint(JoinPoint joinPoint) {
        executeWithExceptionHandling(() -> {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            boolean isSensitiveEndpoints = isSensitiveEndpoint(request);
            String params = isSensitiveEndpoints
                    ? MASK
                    : JsonUtil.getJsonString(request.getParameterMap());
            String args = isSensitiveEndpoints
                    ? MASK
                    : Arrays.toString(joinPoint.getArgs());

            log.info(
                    "[Request] Endpoint: [{}] {}, Params: {}, Args: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    params,
                    args
            );
        });
    }

    /**
     * Advice that logs when a controller method is exited.
     */
    @AfterReturning(pointcut = "controllerPointcut()", returning = "returnValue")
    public void afterEndpoint(Object returnValue) {
        executeWithExceptionHandling(() -> {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String responseBody = isSensitiveEndpoint(request)
                    ? MASK
                    : JsonUtil.getJsonString(returnValue);

            log.info(
                    "[Response] Endpoint: [{}] {}, Returned: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    responseBody
            );
        });
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param e Exception
     */
    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "e")
    public void afterThrow(Exception e) {
        executeWithExceptionHandling(() -> {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            log.error(
                    "[Response Exception] Endpoint: [{}] {}, Exception: {}, Message: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    e.getClass().getSimpleName(),
                    e.getMessage(),
                    e
            );
        });
    }

    /**
     * Advice that logs when a controller advice method is exited.
     */
    @AfterReturning(pointcut = "controllerAdvisorPointcut()", returning = "returnValue")
    public void afterExceptionHandle(Object returnValue) {
        executeWithExceptionHandling(() -> {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            log.error(
                    "[Exception Handler] Endpoint: [{}] {}, Returned: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    JsonUtil.getJsonString(returnValue)
            );
        });
    }
}
