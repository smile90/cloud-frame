package com.frame.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAopConfig {
    private static Logger logger = LoggerFactory.getLogger(DataSourceAopConfig.class);

    @Before("execution(* com.frame..mapper*..*.find*(..)) "
            + " || execution(* com.frame..mapper*..*.get*(..)) "
            + " || execution(* com.frame..mapper*..*.select*(..)) "
            + " || execution(* com.frame..mapper*..*.query*(..))")
    public void setReadDataSourceType(JoinPoint joinPoint) {
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.READ);

        Signature signature = joinPoint.getSignature();
        logger.debug("dataSource:{}.{}:{}", signature.getDeclaringTypeName(), signature.getName(), DataSourceTypeEnum.READ.name());
    }

    @Before("execution(* com.frame..mapper*..*.insert*(..)) "
            + " || execution(* com.frame..mapper*..*.update*(..))"
            + " || execution(* com.frame..mapper*..*.remove*(..))")
    public void setWriteDataSourceType(JoinPoint joinPoint) {
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.PRIMARY);

        Signature signature = joinPoint.getSignature();
        logger.debug("dataSource:{}.{}:{}", signature.getDeclaringTypeName(), signature.getName(), DataSourceTypeEnum.PRIMARY.name());
    }
}
