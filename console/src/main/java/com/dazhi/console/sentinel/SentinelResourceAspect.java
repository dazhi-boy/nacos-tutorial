package com.dazhi.console.sentinel;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component //交由Spring容器管理
public class SentinelResourceAspect {
    @Pointcut("@annotation(com.dazhi.console.sentinel.SentinelResource)")
    public void sentinelResourceAnnotationPointcut() {
    }

    @Around("sentinelResourceAnnotationPointcut()")
    public Object invokeResourceWithSentinel(ProceedingJoinPoint pjp) throws Throwable {
        Method originMethod = this.resolveMethod(pjp);
        SentinelResource annotation = (SentinelResource)originMethod.getAnnotation(SentinelResource.class);
        if (annotation == null) {
            throw new IllegalStateException("Wrong state for SentinelResource annotation");
        } else {
            return null;
        }
    }

    protected Method resolveMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Method method = this.getDeclaredMethodFor(targetClass, signature.getName(), signature.getMethod().getParameterTypes());
        if (method == null) {
            throw new IllegalStateException("Cannot resolve target method: " + signature.getMethod().getName());
        } else {
            return method;
        }
    }

    private Method getDeclaredMethodFor(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException var6) {
            Class<?> superClass = clazz.getSuperclass();
            return superClass != null ? this.getDeclaredMethodFor(superClass, name, parameterTypes) : null;
        }
    }
}
