package com.duoc.LearningPlatform.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Aspecto transversal de logging.
 * Captura entradas/salidas de todos los métodos en las capas de servicio y
 * controlador, y registra cualquier excepción que se produzca.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = Logger.getLogger(LoggingAspect.class.getName());

    /** Pointcut para toda la capa de servicios */
    @Pointcut("execution(* com.duoc.LearningPlatform.service.impl.*.*(..))")
    public void serviceLayer() {}

    /** Pointcut para toda la capa de controladores */
    @Pointcut("execution(* com.duoc.LearningPlatform.controller.*.*(..))")
    public void controllerLayer() {}

    /**
     * Around advice: registra el método invocado, sus argumentos y el tiempo
     * de ejecución. Aplica a servicios y controladores.
     */
    @Around("serviceLayer() || controllerLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className  = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args     = joinPoint.getArgs();

        log.info(String.format("[START] %s.%s | args: %s", className, methodName, Arrays.toString(args)));

        long start = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            log.severe(String.format("[ERROR] %s.%s | exception: %s", className, methodName, ex.getMessage()));
            throw ex;
        }
        long elapsed = System.currentTimeMillis() - start;

        log.info(String.format("[END]   %s.%s | tiempo: %d ms", className, methodName, elapsed));
        return result;
    }

    /**
     * AfterThrowing advice: registra excepciones no capturadas en la capa de
     * servicios para facilitar el diagnóstico.
     */
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logServiceException(JoinPoint joinPoint, Throwable ex) {
        String method = joinPoint.getSignature().toShortString();
        log.warning(String.format("[EXCEPTION] %s | %s: %s",
                method, ex.getClass().getSimpleName(), ex.getMessage()));
    }
}
