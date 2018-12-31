# basic-tiny-spring
a very basic naive tiny implementation of part of *Spring*

# 整个项目的简述
*Spring* 框架提供的容器支持通过注解的方式来生成所需要的 `bean`
在 [real](real) 这个 module 中, 我们通过单元测试来验证 *Spring* 生成的 `bean` 是正确的.
在 [naive](naive) 这个 module 中, 我们通过 反射(reflection) + 拓扑排序(topological sort) 来模拟 *Spring* 的行为, 并会通过单元测试来进行验证.

# [real](real) module 的简述




# [naive](naive) module 的简述
[naive/src/main/java/com/study/spring/Container.java](naive/src/main/java/com/study/spring/Container.java) 中的主要逻辑如下

## 获取 `com.study.spring` 这个 `package` 中的所有的类
```java
Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);
```
这样就得到了 `allClasses`

## 从 `allClasses` 中找到有 `@Component` 注解的类
```java
List<Class> classList = findAllClassWithSpecifiedAnnotation(allClasses, Component.class);
```

而 `findAllClassWithSpecifiedAnnotation` 方法的内容如下
```java
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
```
其实就是遍历 `allClasses`, 通过反射查看其中有哪些类带有 `@Component` 注解


# 其他
为了便于观察效果, 本项目中的 bean 都会继承 `Base` 这个类.
在 [real/src/main/java/com/study/spring/bean/Base.java](real/src/main/java/com/study/spring/bean/Base.java) 和
[naive/src/main/java/com/study/spring/bean/Base.java](naive/src/main/java/com/study/spring/bean/Base.java) 中都可以看到这个类(内容是相同的)

# 参考资料
1. [(github)理解并实现一个IOC容器](https://github.com/biezhi/java-bible/blob/master/ioc/index.md)
2. [(豆瓣)算法概论](https://book.douban.com/subject/3155710/) 第三章有 深度优先搜索(Depth First Search) 和 拓扑排序(Topological Sort) 的相关内容
3. [(stackoverflow)如何通过反射找到一个 package 下的所有 class](https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection)
