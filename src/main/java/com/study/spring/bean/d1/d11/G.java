package com.study.spring.bean.d1.d11;

import com.study.spring.annotations.Component;
import com.study.spring.bean.Base;
import com.study.spring.bean.d1.d12.d121.A;
import com.study.spring.bean.d2.N;

import javax.annotation.Resource;

@Component(value = "g")
public class G extends Base {
    @Resource
    private A a;

    @Resource
    private N n;
}
