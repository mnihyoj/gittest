package hy.plugin.factory;

import java.lang.reflect.Constructor;

/**
 * @description: Bean生成工厂
 * @author: Yuan Hong
 * @create: 2018-11-02 13:01
 */
public class BeanFactory {


    /**
     * @Description:无参数初始化类T
     * @Param: [clzss]
     * @return: T
     * @Author: Yuan Hong
     * @Date: 2018/11/2
     */
    public static <T>  T create(Class<T> tClass){



        if(tClass==null){
            return null;
        }
        try {
            T t = tClass.newInstance();
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @Description: 带参初始化类T
     * @Param: [tClass, object, parameterTypes]
     * @return: T
     * @Author: Yuan Hong
     * @Date: 2018/11/2
     */
    public static <T>  T create(Class<T> tClass, Object[]object, Class<?>[] parameterTypes){

        if(tClass==null){
            return null;
        }
        try {

            Constructor<T> constructor=tClass.getConstructor(parameterTypes);//获取有参构造

            T t = constructor.newInstance(object);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
