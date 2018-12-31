package com.study.spring.bean.d1.d11;

import com.study.spring.bean.Base;
import com.study.spring.bean.d1.d12.O;
import com.study.spring.bean.d1.d12.d121.A;
import com.study.spring.bean.d2.N;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component(value = "c")
public class C extends Base {
    @Resource
    private A a;

    @Resource
    private N n;

    private O o;
}
