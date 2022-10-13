package protoservice.buddy;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;
import java.util.Arrays;

public class SimpleInterceptor {
    // todo:thread local
    @RuntimeType
    public static Object intercept(@Origin Method method, @AllArguments Object[] args) {
        System.out.println(method.getName());
        System.out.println(Arrays.toString(args));
        return "null";
    }
}
