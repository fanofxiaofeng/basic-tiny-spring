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
import java.util.LinkedList;
import java.util.List;

public class MainTest {
    private Container container = new Container();

    {
        System.out.println(Arrays.toString(container.getBeanDefinitionNames()));
    }

    @Test
    public void checkA() {
        List<A> aList = getTwoBeans("a", A.class);
        for (A a : aList) {
            Assert.assertNotNull(a);
        }
    }

    @Test
    public void checkC() throws Exception {
        List<C> cList = getTwoBeans("c", C.class);
        for (C c : cList) {
            Assert.assertNotNull(parseField(c, "a"));
            Assert.assertNotNull(parseField(c, "n"));
            Assert.assertNull(parseField(c, "o"));
        }
    }

    @Test
    public void checkD() throws Exception {
        List<D> dList = getTwoBeans("d", D.class);
        for (D d : dList) {
            Assert.assertNotNull(parseField(d, "o"));
            Assert.assertNull(parseField(d, "g"));
        }
    }

    @Test
    public void checkG() throws Exception {
        List<G> gList = getTwoBeans("g", G.class);
        for (G g : gList) {
            Assert.assertNotNull(parseField(g, "a"));
            Assert.assertNotNull(parseField(g, "n"));
        }
    }

    @Test
    public void checkN() {
        List<N> nList = getTwoBeans("n", N.class);
        for (N n : nList) {
            Assert.assertNotNull(n);
        }
    }

    @Test
    public void checkO() throws Exception {
        List<O> oList = getTwoBeans("o", O.class);
        for (O o : oList) {
            Assert.assertNull(parseField(o, "z"));
        }
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

    private <T> List<T> getTwoBeans(String name, Class<T> type) {
        List<T> list = new LinkedList<>();

        @SuppressWarnings("unchecked")
        T t1 = (T) container.getBean(name);
        list.add(t1);

        T t2 = container.getBean(type);
        list.add(t2);

        Assert.assertEquals(list.size(), 2);

        return list;
    }

    private Object parseField(Object o, String name) throws Exception {
        Field field = o.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(o);
    }
}
