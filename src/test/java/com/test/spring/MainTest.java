package com.test.spring;

import com.study.spring.Container;
import com.study.spring.bean.d1.d11.C;
import com.study.spring.bean.d1.d11.G;
import com.study.spring.bean.d1.d11.d111.Z;
import com.study.spring.bean.d1.d12.O;
import com.study.spring.bean.d1.d12.X;
import com.study.spring.bean.d1.d12.d121.A;
import com.study.spring.bean.d2.D;
import com.study.spring.bean.d2.N;
import com.study.spring.exception.NoSuchBeanDefinitionException;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

public class MainTest {
    private Container container = new Container();

    {
        System.out.println(Arrays.toString(container.getBeanDefinitionNames()));
    }

    @Test
    public void checkA() {
        A a = (A) container.getBean("a");
        Assert.assertNotNull(a);
    }

    @Test
    public void checkC() throws Exception {
        C c = (C) container.getBean("c");
        System.out.println(c);
        Assert.assertNotNull(parseField(c, "a"));
        Assert.assertNotNull(parseField(c, "n"));
        Assert.assertNull(parseField(c, "o"));
    }

    @Test
    public void checkD() throws Exception {
        D d = (D) container.getBean("d");
        Assert.assertNotNull(parseField(d, "o"));
        Assert.assertNull(parseField(d, "g"));
    }

    @Test
    public void checkG() throws Exception {
        G g = (G) container.getBean("g");
        Assert.assertNotNull(parseField(g, "a"));
        Assert.assertNotNull(parseField(g, "n"));
    }

    @Test
    public void checkN() {
        N n = (N) container.getBean("n");
        Assert.assertNotNull(n);
    }

    @Test
    public void checkO() throws Exception {
        O o = (O) container.getBean("o");
        Assert.assertNull(parseField(o, "z"));
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void checkX() {
        X x = (X) container.getBean("x");
        Assert.assertNull(x);
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void checkZ() {
        Z z = (Z) container.getBean("z");
        Assert.assertNull(z);
    }

    private Object parseField(Object o, String name) throws Exception {
        Field field = o.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(o);
    }
}
