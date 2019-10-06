package com.alibaba.dubbo.demo.provider.activate;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

import java.util.List;

/**
 * @author lwh
 * @date 2019-10-05
 * @desp
 */
public class ActivateTest {

    /**
     * 1
     * class com.alibaba.dubbo.demo.provider.activate.impl.ActivateExtImpl1
     */
    @Test
    public void test1(){
        ExtensionLoader<ActivateExt> loader = ExtensionLoader.getExtensionLoader(ActivateExt.class);
        URL url = URL.valueOf("test://localhost/test");
        List<ActivateExt> list = loader.getActivateExtension(url, new String[]{}, "");
        System.out.println(list.size());
        list.forEach(item -> System.out.println(item.getClass()));
    }

    /**
     * 1
     * class com.alibaba.dubbo.demo.provider.activate.impl.GroupActivateExtImpl
     */
    @Test
    public void test2(){
        ExtensionLoader<ActivateExt> loader = ExtensionLoader.getExtensionLoader(ActivateExt.class);
        URL url = URL.valueOf("test://localhost/test");
        List<ActivateExt> list = loader.getActivateExtension(url, new String[]{}, "group1");
        System.out.println(list.size());
        list.forEach(item -> System.out.println(item.getClass()));
    }

    /**
     * 2
     * class com.alibaba.dubbo.demo.provider.activate.impl.OrderActivateExtImpl1
     * class com.alibaba.dubbo.demo.provider.activate.impl.ValueActivateExtImpl
     */
    @Test
    public void test3(){
        ExtensionLoader<ActivateExt> loader = ExtensionLoader.getExtensionLoader(ActivateExt.class);
        URL url = URL.valueOf("test://localhost/test");
        // 注意这里要使用url接收,不能直接url.addParameter()
        url = url.addParameter("value", "test");
        List<ActivateExt> list = loader.getActivateExtension(url, new String[]{"order1", "-default"}, "group");
        System.out.println(list.size());
        list.forEach(item -> System.out.println(item.getClass()));
    }

    /**
     * 2
     * class com.alibaba.dubbo.demo.provider.activate.impl.OrderActivateExtImpl2
     * class com.alibaba.dubbo.demo.provider.activate.impl.OrderActivateExtImpl1
     */
    @Test
    public void test4(){
        ExtensionLoader<ActivateExt> loader = ExtensionLoader.getExtensionLoader(ActivateExt.class);
        URL url = URL.valueOf("test://localhost/test");
        List<ActivateExt> list = loader.getActivateExtension(url, new String[]{}, "order");
        System.out.println(list.size());
        list.forEach(item -> System.out.println(item.getClass()));
    }
}
