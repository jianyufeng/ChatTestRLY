package com.fule.myapplication.common.activity.until;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Jorstin on 2015/6/26.
 * 1.@Target
 *
 * @Target用来指明注解所修饰的目标，包括packages、types（类、接口、枚举、Annotation类型）、 类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）。
 * 取值(ElementType)有：
 * CONSTRUCTOR:用于描述构造器
 * FIELD:用于描述域
 * LOCAL_VARIABLE:用于描述局部变量
 * METHOD:用于描述方法
 * PACKAGE:用于描述包
 * PARAMETER:用于描述参数
 * TYPE:用于描述类、接口(包括注解类型) 或enum声明
 * <p>
 * 2@Retention
 * @Retention定义了该Annotation的生命周期，某些Annotation仅出现在源代码中，而被编译器丢弃； 而另一些却被编译在class文件中；
 * 编译在class文件中的Annotation可能会被虚拟机忽略，
 * 而另一些在class被装载时将被读取。
 * 取值（RetentionPoicy）有：
 * SOURCE:在源文件中有效（即源文件保留）
 * CLASS:在class文件中有效（即class保留）
 * RUNTIME:在运行时有效（即运行时保留）
 * <p>
 * 3 @Documented
 * 的作用是在生成javadoc文档的时候将该Annotation也写入到文档中。
 * <p>
 * 4.@Inherited
 * 我们自定义注解(Annotation)时，把自定义的注解标注在父类上，但是它不会被子类所继承，
 * 我们可以在定义注解时给我们自定义的注解标注一个@Inherited注解来实现注解继承。
 * 如果父类的注解是定义在类上面，那么子类是可以继承过来的
如果父类的注解定义在方法上面，那么子类仍然可以继承过来
如果子类重写了父类中定义了注解的方法，那么子类将无法继承该方法的注解，
即子类在重写父类中被@Inherited标注的方法时，会将该方法连带它上面的注解一并覆盖掉
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface ActivityTransition {//这个注解用来注解类、接口(包括注解类型) 或enum声明。
    int value();
}
