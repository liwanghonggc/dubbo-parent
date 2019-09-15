package com.alibaba.dubbo.demo.provider.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwh
 * @date 2019-09-14
 * @desp
 */
public class AdaptiveTest {

    /**
     * SPI上有注解,@SPI("dubbo"),输出dubbo
     */
    @Test
    public void test1(){
        ExtensionLoader<AdaptiveExt> loader = ExtensionLoader.getExtensionLoader(AdaptiveExt.class);
        AdaptiveExt adaptiveExtension = loader.getExtension("dubbo");
        URL url = URL.valueOf("test://localhost/test");
        adaptiveExtension.echo("d", url);
    }

    /**
     * SPI上有注解,@SPI("dubbo"),URL中也有具体的值,输出spring cloud,注意这里对方法标注有@Adaptive注解,但是该注解没有值
     */
    @Test
    public void test2(){
        ExtensionLoader<AdaptiveExt> loader = ExtensionLoader.getExtensionLoader(AdaptiveExt.class);
        AdaptiveExt adaptiveExtension = loader.getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test?adaptive.ext=cloud");
        System.out.println(adaptiveExtension.echo("d", url));
    }

    /**
     * SPI上有注解,@SPI("dubbo"),URL中也有具体的值,ThriftAdaptiveExt实现类上面有@Adaptive注解,输出thrift
     */
    @Test
    public void test3(){
        ExtensionLoader<AdaptiveExt> loader = ExtensionLoader.getExtensionLoader(AdaptiveExt.class);
        AdaptiveExt adaptiveExtension = loader.getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test?adaptive.ext=cloud");
        System.out.println(adaptiveExtension.echo("d", url));
    }

    /**
     * SPI上有注解,@SPI("dubbo"),URL中也有具体的值,接口方法中加上注解@Adaptive({"t"}),各个实现类上面没有@Adaptive注解,输出spring cloud
     */
    @Test
    public void test4(){
        ExtensionLoader<AdaptiveExt> loader = ExtensionLoader.getExtensionLoader(AdaptiveExt.class);
        AdaptiveExt adaptiveExtension = loader.getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test?t=cloud");
        System.out.println(adaptiveExtension.echo("d", url));
    }

    /**
     * 测试通过URL依赖注入
     */
    @Test
    public void test5(){
        ExtensionLoader<AdaptiveExt> loader = ExtensionLoader.getExtensionLoader(AdaptiveExt.class);

        Map<String, String> map = new HashMap<>();
        map.put("t", "cloud");
        URL url = new URL("", "", 1, map);
        AdaptiveExt adaptiveExtension = loader.getExtension("dubbo");
        System.out.println(adaptiveExtension.echo(" ", url));
    }
}
