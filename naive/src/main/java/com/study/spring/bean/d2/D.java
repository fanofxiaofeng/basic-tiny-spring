package com.study.spring.bean.d2;

import com.study.spring.annotations.Component;
import com.study.spring.bean.Base;
import com.study.spring.bean.d1.d11.G;
import com.study.spring.bean.d1.d12.O;

import javax.annotation.Resource;

@Component(value = "d")
public class D extends Base {

    @Resource
    private O o;

    private G g;
}