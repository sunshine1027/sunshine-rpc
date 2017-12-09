package com.sunshine.rpc.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
@Component
public class BeanUtils implements BeanFactoryAware {
    private static BeanFactory beanFactory;
    private static ConcurrentHashMap<String, Class<?>> serviceBeanRegister = new ConcurrentHashMap<String, Class<?>>();

    public static void registerServiceBean(String className, Class<?> clazz) {
        serviceBeanRegister.put(className, clazz);
    }

    public static Class<?> getServiceBean(String className) {
        return serviceBeanRegister.get(className);
    }


    public static <T> T getBean(String beanName) {
        return (T) beanFactory.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return beanFactory.getBean(beanName, requiredType);
    }

    public static <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
