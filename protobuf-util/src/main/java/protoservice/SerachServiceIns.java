package protoservice;

import com.google.protobuf.*;
import io.github.phiysng.proto.Basic;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import protoservice.buddy.Buddy;
import protoservice.buddy.SimpleInterceptor;
import protoservice.channel.NettyRpcChannel;
import protoservice.controller.NettyRpcController;
import protoservice.service.SearchService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SerachServiceIns {
    public static void main(String[] args) {
        System.out.println("start");

        NettyRpcChannel channel = new NettyRpcChannel();
//        channel.setSearchService(new SearchService());

        // client code.
        RpcController controller = new NettyRpcController();
        Basic.SearchService.Stub service = Basic.SearchService.newStub(channel);
        // register callback
        service.search(controller, Empty.getDefaultInstance(), new RpcCallback<Empty>() {
            @Override
            public void run(Empty parameter) {
                System.out.println("DONE");
            }
        });

        class RpcCallbackBase implements RpcCallback<Object> {
            @Override
            public void run(Object parameter) {
                // 这里将Object cast成IMessage
                System.out.println("DONE");
                if(parameter instanceof MessageLite){
                    System.out.println("转换成MessageLite");
                    MessageLite msg = (MessageLite)parameter;
                    System.out.println(msg.getClass());
                }
            }
        }
        service.search(controller, Empty.getDefaultInstance(), (RpcCallback<com.google.protobuf.Empty>)(Object)new RpcCallbackBase());

        System.out.println("end");

        // InvocationHandler
        // ByteBuddy
        // 反射也可以做到
        for(Method method : service.getClass().getMethods()){
            if(!"search".equals(method.getName()))
                continue;
            try {
                method.invoke(service,controller,Empty.getDefaultInstance(),new RpcCallbackBase());
                System.out.println("反射结束");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        SearchService searchService = new SearchService();
        for (Method method : SearchService.class.getMethods()) {
            if(!"search".equals(method.getName()))
                continue;

            System.out.printf("接口 %s %d%n", method.getName(), method.getParameterTypes().length);
            for (Class<?> parameterType : method.getParameterTypes()) {
                System.out.println(parameterType.toGenericString());
            }

            ByteBuddy buddy = new ByteBuddy(ClassFileVersion.JAVA_V8);
            Class<?> clz = buddy.subclass(method.getParameterTypes()[2])
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
                RpcCallback<Message> in = (RpcCallback<Message>) object;
                in.run(Empty.getDefaultInstance());


            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
//            try {
////                使用ByteBuddy生成动态代理对象
//                // 动态代理对象,序列化发给对端
////                Object instance = method.getParameterTypes()[2].newInstance();
//
//            } catch (InstantiationException e) {
//                throw new RuntimeException(e);
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
        }

    }
}
