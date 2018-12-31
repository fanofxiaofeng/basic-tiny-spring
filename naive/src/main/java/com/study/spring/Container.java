package com.study.spring;

import com.study.spring.annotations.Component;
import com.study.spring.exception.NoSuchBeanDefinitionException;
import com.study.spring.util.TopologicalSort;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class Container {

    private Map<Class, Object> classMap = new HashMap<>();

    private Map<String, Object> nameMap = new HashMap<>();

    public Container() {
        this("com.study.spring");
    }

    private Container(String packageName) {
        // 获取一个 package 下的所有类的这2行代码的来源: https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);

        System.out.println(allClasses.size());

        List<Class> classList = findAllClassWithSpecifiedAnnotation(allClasses, Component.class);
        System.out.println(classList);

        Map<Class, List<Class>> adjacentMap = calculateAdjacentMap(classList, Resource.class);

        System.out.println("adjacentMap");
        System.out.println(adjacentMap);
        List<Class> post = new TopologicalSort(adjacentMap).run();
        System.out.println(post);
        for (Class clazz : post) {
            try {
                Object instance = clazz.newInstance();

                Component component = (Component) clazz.getAnnotation(Component.class);
                String name = component.value();

                for (Field field : clazz.getDeclaredFields()) {
                    Resource resource = field.getAnnotation(Resource.class);
                    if (resource == null) {
                        continue;
                    }

                    field.setAccessible(true);
                    field.set(instance, classMap.get(field.getType()));
                }

                nameMap.put(name, instance);
                classMap.put(clazz, instance);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 计算邻接表
     */
    private Map<Class, List<Class>> calculateAdjacentMap(List<Class> classList, Class targetAnnotation) {
        Map<Class, List<Class>> result = new HashMap<>();

        for (Class clazz : classList) {
            for (Field field : clazz.getDeclaredFields()) {
                Annotation a = field.getAnnotation(targetAnnotation);
                if (a == null) {
                    continue;
                }
                if (!result.containsKey(clazz)) {
                    result.put(clazz, new LinkedList<Class>());
                }

                result.get(clazz).add(field.getType());
            }
        }
        return result;
    }

    private List<Class> findAllClassWithSpecifiedAnnotation(Set<Class<?>> allClasses, Class targetAnnotation) {
        List<Class> result = new LinkedList<>();
        for (Class clazz : allClasses) {
            Annotation annotation = clazz.getAnnotation(targetAnnotation);
            if (annotation == null) {
                continue;
            }
            result.add(clazz);
        }
        return result;
    }

    public Object getBean(String name) {
        if (!nameMap.containsKey(name)) {
            throw new NoSuchBeanDefinitionException();
        }
        return nameMap.get(name);
    }

    public <T> T getBean(Class<T> requiredType) {
        if (!classMap.containsKey(requiredType)) {
            throw new NoSuchBeanDefinitionException();
        }
        return (T) classMap.get(requiredType);
    }

    public String[] getBeanDefinitionNames() {
        String[] result = new String[nameMap.size()];
        int i = 0;
        for (String name : nameMap.keySet()) {
            result[i] = name;
            i++;
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        Container container = new Container();
    }
}
