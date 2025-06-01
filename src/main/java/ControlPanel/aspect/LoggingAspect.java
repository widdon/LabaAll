package ControlPanel.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* ControlPanel.service.ControlPanelProxy.*(..))")
    public void logMethodCall(JoinPoint jp) {
        System.out.println("[AOP] Вызов: " + jp.getSignature().getName());
    }
}