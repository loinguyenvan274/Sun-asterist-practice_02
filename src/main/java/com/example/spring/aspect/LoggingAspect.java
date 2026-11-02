package com.example.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.spring.service.ProductService.*(..))")
    public void productServiceMethods() {
    }

    @Before("productServiceMethods()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("[AOP - @Before] Dang goi ham: " + methodName + " | Tham so truyen vao: " + Arrays.toString(args));
    }

    @AfterReturning(pointcut = "productServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[AOP - @AfterReturning] Ham " + methodName + " hoan thanh | Ket qua tra ve: " + result);
    }

    @Around("productServiceMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        Object returnValue = joinPoint.proceed();

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("[AOP - @Around] Thoi gian thuc thi cua ham " + methodName + ": " + elapsedTime + " ms");

        return returnValue;
    }
}

/*
========================================================================================
GIẢI THÍCH FILE LoggingAspect.java:
1. AOP APIs (Aspect-Oriented Programming - Mục 3 trong hình ảnh):
   - AOP (Lập trình hướng khía cạnh) giúp tách biệt các mối quan tâm chung (cross-cutting concerns)
     như ghi log, đo lường hiệu năng, bảo mật, quản lý giao dịch ra khỏi logic nghiệp vụ chính (Business Logic).
   - Nhờ AOP, mã nguồn trong ProductService hoàn toàn trong sạch, không bị dính bất kỳ dòng code log hay đo thời gian nào.

2. Các khái niệm cốt lõi trong Spring AOP:
   - @Aspect: Khai báo class này là một Khía cạnh (Aspect), chứa các lời khuyên (Advice) và điểm cắt (Pointcut).
   - @Component: Đăng ký Aspect thành một Spring Bean để IoC Container quản lý.
   - Pointcut (@Pointcut): Biểu thức định vị vị trí các hàm sẽ bị can thiệp.
     Ví dụ: execution(* com.example.spring.service.ProductService.*(..)) nghĩa là áp dụng cho mọi phương thức
     có bất kỳ kiểu trả về nào, bất kỳ tham số nào trong class ProductService.
   - JoinPoint: Điểm cụ thể trong chương trình đang được thực thi (phương thức được gọi, tham số truyền vào...).
   - Advice: Hành động can thiệp vào các thời điểm khác nhau:
     + @Before: Chạy NGAY TRƯỚC khi phương thức mục tiêu được gọi.
     + @AfterReturning: Chạy SAU KHI phương thức mục tiêu hoàn thành thành công và nhận được giá trị trả về (result).
     + @Around: Can thiệp toàn diện nhất, bao bọc cả trước và sau khi gọi phương thức. Cần gọi "joinPoint.proceed()"
       để cho phép phương thức gốc tiếp tục chạy và có thể tính được thời gian thực thi (elapsed time).

3. Cơ chế hoạt động của Spring AOP:
   - Khi kích hoạt @EnableAspectJAutoProxy, Spring tạo ra một Proxy Object (bọc quanh Bean thật).
   - Khi gọi một phương thức trên Bean, lời gọi sẽ đi qua Proxy trước để thực thi các Advice của Aspect,
     sau đó mới đi vào hàm thực tế.
========================================================================================
*/
