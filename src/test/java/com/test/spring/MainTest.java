package com.test.spring;

import com.study.spring.bean.d1.d11.C;
import com.study.spring.bean.d1.d11.G;
import com.study.spring.bean.d1.d11.d111.Z;
import com.study.spring.bean.d1.d12.O;
import com.study.spring.bean.d1.d12.X;
import com.study.spring.bean.d1.d12.d121.A;
import com.study.spring.bean.d2.D;
import com.study.spring.bean.d2.N;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Arrays;

public class MainTest {
    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/simple-config.xml");

    {
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
    }

    @Test
    public void checkA() {
        A a = (A) context.getBean("a");
        Assert.notNull(a);
    }

    @Test
    public void checkC() throws Exception {
        C c = (C) context.getBean("c");
        System.out.println(c);
        Assert.notNull(parseField(c, "a"));
        Assert.notNull(parseField(c, "n"));
        Assert.isNull(parseField(c, "o"));
    }

    @Test
    public void checkD() throws Exception {
        D d = (D) context.getBean("d");
        Assert.notNull(parseField(d, "o"));
        Assert.isNull(parseField(d, "g"));
    }

    @Test
    public void checkG() throws Exception {
        G g = (G) context.getBean("g");
        Assert.notNull(parseField(g, "a"));
        Assert.notNull(parseField(g, "n"));
    }

    @Test
    public void checkN() {
        N n = (N) context.getBean("n");
        Assert.notNull(n);
    }

    @Test
    public void checkO() throws Exception {
        O o = (O) context.getBean("o");
        Assert.isNull(parseField(o, "z"));
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void checkX() {
        X x = (X) context.getBean("x");
        Assert.isNull(x);
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void checkZ() {
        Z z = (Z) context.getBean("z");
        Assert.isNull(z);
    }

    private Object parseField(Object o, String name) throws Exception {
        Field field = o.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(o);
    }
}
