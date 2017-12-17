package com.sunshine.rpc.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
@Component
public class BeanUtils implements ApplicationContextAware {
    private static ApplicationContext context;
    private static ConcurrentHashMap<String, Class<?>> serviceBeanRegister = new ConcurrentHashMap<String, Class<?>>();


    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        return context.getBeansOfType(clazz);
    }
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
