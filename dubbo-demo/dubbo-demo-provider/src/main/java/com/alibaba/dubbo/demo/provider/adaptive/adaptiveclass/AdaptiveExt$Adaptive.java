package com.alibaba.dubbo.demo.provider.adaptive.adaptiveclass;

/**
 * @author lwh
 * @date 2019-09-14
 * @desp
 */
//package com.alibaba.dubbo.demo.provider.adaptive;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class AdaptiveExt$Adaptive implements com.alibaba.dubbo.demo.provider.adaptive.AdaptiveExt {

    public java.lang.String echo(java.lang.String arg0, com.alibaba.dubbo.common.URL arg1) {
        if (arg1 == null)
            throw new IllegalArgumentException("url == null");

        com.alibaba.dubbo.common.URL url = arg1;
        String extName = url.getParameter("t", "dubbo");

        if (extName == null)
            throw new IllegalStateException("Fail to get extension(com.alibaba.dubbo.demo.provider.adaptive.AdaptiveExt) name from url(" + url.toString() + ") use keys([t])");

        com.alibaba.dubbo.demo.provider.adaptive.AdaptiveExt extension = (com.alibaba.dubbo.demo.provider.adaptive.AdaptiveExt) ExtensionLoader.getExtensionLoader(com.alibaba.dubbo.demo.provider.adaptive.AdaptiveExt.class).getExtension(extName);
        return extension.echo(arg0, arg1);
    }
}
