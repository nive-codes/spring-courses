package hello.hello_artifact.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*AOP 선언*/
@Aspect
@Component        //Component로 bean 등록하기보단 SpringConfig에 직접 등록해서 개발자가 직접 관리하는 걸로 처리(AOP 명시 내용 확인하기 쉬움)
public class TimeTraceAop {

    @Around("execution(* hello.hello_artifact..*(..))")         /*하위 패키지는 전체 검사*/
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        System.out.println("Start : " + joinPoint.toString());

        try{
            return joinPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            long timeMs = end - start;

            System.out.println("End : " + joinPoint.toString() + " "+timeMs + "ms");
        }
    }

}

/*각 메서드별 시간 측정하는 공통 로직을 추가해서 핵심관심사항과는 별개로 구분지어 처리*/