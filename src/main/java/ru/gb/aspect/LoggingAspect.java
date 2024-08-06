package ru.gb.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // Before
    // After
    // AfterThrowing
    // AfterReturning
    // Around

    @Pointcut("execution(* ru.gb.service.TimesheetService.*(..))")
    public void timesheetServiceMethodsPointcut() {}

    @Before(value = "timesheetServiceMethodsPointcut()")
    public void beforeTimesheetServiceFindById(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        if (jp.getArgs().length != 0) {
            Object arg = jp.getArgs()[0];
            log.info("Before -> TimesheetService#{}({} = {})", methodName, arg.getClass().getSimpleName(), arg);
        }
        log.info("Before -> TimesheetService#{}", methodName);
    }

    @After(value = "timesheetServiceMethodsPointcut()")
    public void afterTimesheetServiceFindById(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        if (jp.getArgs().length != 0) {
            Object arg = jp.getArgs()[0];
            log.info("After -> TimesheetService#{}({} = {})", methodName, arg.getClass().getSimpleName(), arg);
        }
        log.info("After -> TimesheetService#{}", methodName);
    }
}
