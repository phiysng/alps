package protoservice.buddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;


//@interface

interface Foo{
    String Foo(String s);
}

public class Buddy {
    public static void main(String[] args) {
        ByteBuddy buddy = new ByteBuddy(ClassFileVersion.JAVA_V8);
        Class<?> clz = buddy.subclass(Foo.class)
                // 可以直接传入接口的class
                // 不能传入非public 的interface?
//                .implement(protoservice.buddy.IBubbleInterface.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(SimpleInterceptor.class))
                .make()
                // TODO: class loader相关知识
                // 不能用Object的class loader
                .load(Buddy.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        try {
            Object object = clz.getDeclaredConstructor().newInstance();
            IBubbleInterface in = (IBubbleInterface) object;
            System.out.println(in.Foo("S"));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }
}
