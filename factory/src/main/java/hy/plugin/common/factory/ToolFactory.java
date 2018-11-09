package hy.plugin.common.factory;



import hy.plugin.common.Tool.Tool;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 工具类生成工厂
 * @author: Yuan Hong
 * @create: 2018-11-02 13:44
 */
public class ToolFactory extends BeanFactory {

    /**
     * @Description: 全局变量 一旦生成了一个tool实例，统一存放在map里面，无需再次生成
     * @Param:
     * @return:
     * @Author: Yuan Hong
     * @Date: 2018/11/2
     */
    private static Map<String, Tool> map ;


    /**
     * @Description:
     * @Param: [tClass]
     * @return: T
     * @Author: Yuan Hong
     * @Date: 2018/11/2
     */
    public static <T extends Tool> T getInstance(Class<T> tClass) {


        if (tClass == null) {
            return null;
        }

        String className = tClass.getName();
        T t = getClass(className);
        if (t == null) {
            t = BeanFactory.create(tClass);
            putClass(className, t);
        }
        return t;
    }

    /**
     * @Description:
     * @Param: [className, t]
     * @return: void
     * @Author: Yuan Hong
     * @Date: 2018/11/2
     */
    private static <T extends Tool> void putClass(String className, T t) {


        if (map == null) {
            map = new HashMap<String, Tool>();
        }
        map.put(className, t);

    }

    /**
     * @Description:
     * @Param: [className]
     * @return: T
     * @Author: Yuan Hong
     * @Date: 2018/11/2
     */
    private static <T extends Tool> T getClass(String className) {

        if (map != null && map.containsKey(className)) {
            return (T) map.get(className);
        }
        return null;

    }

}
