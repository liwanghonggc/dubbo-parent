/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.rpc.cluster;

import com.alibaba.dubbo.common.URL;

/**
 * 该接口是配置规则的接口,定义了两个方法,第一个是配置规则,并且生成url,
 * 第二个是把配置配置到旧的url中,其实都是在url上应用规则
 *
 */
public interface Configurator extends Comparable<Configurator> {

    /**
     * 配置规则,生成url
     */
    URL getUrl();

    /**
     * 把规则配置到URL中
     */
    URL configure(URL url);

}
