package com.example.dbdemo.interceptors;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MyBatisSqlPerformance implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(MyBatisSqlPerformance.class);


    private boolean isMappedStatement(Object[] args) {
        return args.length > 0 && args[0] instanceof MappedStatement;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        String statement = null;
        Object[] args = invocation.getArgs();
        if (isMappedStatement(args)) {
            statement = ((MappedStatement) args[0]).getId();
        } else {
            statement = "";
        }

        try {
            return invocation.proceed();
        } catch (Exception e){
            logger.error("execute " + statement + " exception", e);
            throw e;
        }finally {
            long end = System.currentTimeMillis();
            long timing = end - start;
            
            if(timing > 50) {
                System.out.println("more than 50ms");
            }
            
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

}
