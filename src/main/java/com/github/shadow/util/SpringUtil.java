package com.github.shadow.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {

    public static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context == null ? null : context;
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext context) throws BeansException {
        SpringUtil.context = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return clazz == null ? null : context.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        return beanId == null ? null : (T)context.getBean(beanId);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (beanName == null || "".equals(beanName.trim())) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        return context.getBean(beanName, clazz);
    }

}
