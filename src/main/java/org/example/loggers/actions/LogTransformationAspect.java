package org.example.loggers.actions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LogTransformationAspect {

    @Pointcut("within(@LogTransformation *)")
    public void logTransformation() {
    }

    @Around("logTransformation()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        Method method = methodSignature.getMethod();
        Class<?> declaringClass = method.getDeclaringClass();
        LogTransformation logTransformation = declaringClass.getAnnotation(LogTransformation.class);
        String filePath = logTransformation.value();

        LocalDateTime start = LocalDateTime.now();
        Object proceed = joinPoint.proceed();
        LocalDateTime finish = LocalDateTime.now();

        FileWriter fw = new FileWriter(filePath, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write("Operation name: " + className + "." + methodName + "; ");
        bw.write("execution started: " + start + "; ");
        bw.write("execution finished: " + finish + "; ");
        bw.write("input parameters: " + Arrays.toString(joinPoint.getArgs()) + "; ");
        bw.write("output result: " + proceed + "; ");
        bw.close();

        return proceed;
    }
}