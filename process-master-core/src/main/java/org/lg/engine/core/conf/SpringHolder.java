package org.lg.engine.core.conf;

import org.lg.engine.core.utils.Logs;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author liugui
 * @Date 2021/11/25
 */
@Component
public class SpringHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext ctx)
            throws BeansException {
        context = ctx;

    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static String[] getBeans() {
        return context.getBeanDefinitionNames();
    }

    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return context.getBean(beanName, beanClass);
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static <T> T getBeanIgnoreException(String beanName, Class<T> beanClass) {
        try {
            return context.getBean(beanName, beanClass);
        } catch (Exception var3) {
            Logs.error("getBeanIgnoreException error, beanName:{}, beanClass:{}, errorMsg:{}",
                    beanName, beanClass.getName(), var3.getMessage());
            return null;
        }
    }

//    public static void registerAaceConsumerBean(String beanName, String proxy, String registerInterface,
//            Class<?> clazz) {
//        Object obj = getBeanIgnoreException(beanName, clazz);
//        if (obj == null) {
//            DefaultListableBeanFactory defaultListableBeanFactory = ((GenericWebApplicationContext) context)
//                    .getDefaultListableBeanFactory();
//            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AaceConsumerBean.class)
//                    .setInitMethodName("init").addPropertyValue("proxy", proxy)
//                    .addPropertyValue("interfaceName", registerInterface).addPropertyValue("interfaceClass", clazz);
//            defaultListableBeanFactory.registerBeanDefinition(beanName, builder.getBeanDefinition());
//        }
//    }


    public static void removeBean(String beanName) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext
                .getBeanFactory();
        defaultListableBeanFactory.removeBeanDefinition(beanName);
    }

    public static <T> T getBeanIgnoreException(Class<T> beanClass) {
        try {
            return context.getBean(beanClass);
        } catch (Exception var3) {
            Logs.error("getBeanIgnoreException error, beanName:{}, beanClass:{}, errorMsg:{}",
                    beanClass.getName(), var3.getMessage());
            return null;
        }
    }

//    public static void registerAaceConsumerBean(String proxy, String registerInterface, Class<?> clazz) {
//        Object obj = getBeanIgnoreException(clazz);
//        if (obj == null) {
//            DefaultListableBeanFactory defaultListableBeanFactory = ((GenericWebApplicationContext) context)
//                    .getDefaultListableBeanFactory();
//            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AaceConsumerBean.class)
//                    .setInitMethodName("init").addPropertyValue("proxy", proxy)
//                    .addPropertyValue("interfaceName", registerInterface).addPropertyValue("interfaceClass", clazz);
//            defaultListableBeanFactory.registerBeanDefinition(getDefaultBeanName(clazz), builder.getBeanDefinition());
//        }
//    }


    private static String getDefaultBeanName(Class<?> clazz) {
        String name = clazz.getSimpleName();
        String str = name.substring(0, 1);
        String lowerStr = str.toLowerCase();
        return name.replaceFirst(str, lowerStr);
    }

}

