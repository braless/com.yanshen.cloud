//package com.yanshen.java.bean;
//
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.support.RootBeanDefinition;
//import org.springframework.context.ApplicationContext;
//
//import java.security.AccessController;
//import java.security.PrivilegedAction;
//
///**
// * <h3>spring-cloud</h3>
// * <p></p>
// *
// * @author : YanChao
// * @date : 2022-03-17 10:37
// **/
//public class initializeBean {
//    protected Object initializeBean(final String beanName, final Object bean, RootBeanDefinition mbd) {
//
//
//        BeanFactory beanFactory;
//        ApplicationContext a;
//        if (System.getSecurityManager() != null) {
//            AccessController.doPrivileged(new PrivilegedAction<Object>() {
//                @Override
//                public Object run() {
//                    invokeAwareMethods(beanName, bean);
//                    return null;
//                }
//            }, getAccessControlContext());
//        } else {
//            // 涉及到的回调接口点进去一目了然，代码都是自解释的
//            // BeanNameAware、BeanClassLoaderAware或BeanFactoryAware
//            invokeAwareMethods(beanName, bean);
//        }
//
//        Object wrappedBean = bean;
//        if (mbd == null || !mbd.isSynthetic()) {
//            // BeanPostProcessor 的 postProcessBeforeInitialization 回调
//            wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
//        }
//
//        try {
//            // init-methods
//            // 或者是实现了InitializingBean接口，会调用afterPropertiesSet() 方法
//            invokeInitMethods(beanName, wrappedBean, mbd);
//        } catch (Throwable ex) {
//            throw new BeanCreationException(
//                    (mbd != null ? mbd.getResourceDescription() : null),
//                    beanName, "Invocation of init method failed", ex);
//        }
//        if (mbd == null || !mbd.isSynthetic()) {
//            // BeanPostProcessor 的 postProcessAfterInitialization 回调
//            wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
//        }
//        return wrappedBean;
//    }
//
//}
