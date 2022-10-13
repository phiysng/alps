package protoservice;

import com.google.protobuf.Empty;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;
import io.github.phiysng.proto.Basic;
import protoservice.channel.NettyRpcChannel;
import protoservice.controller.NettyRpcController;
import protoservice.service.SearchService;

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
        System.out.println("end");

        SearchService searchService = new SearchService();
        for (Method method : SearchService.class.getMethods()) {
            if(!"search".equals(method.getName()))
                continue;
            System.out.printf("%s %d%n", method.getName(), method.getParameterTypes().length);
            for (Class<?> parameterType : method.getParameterTypes()) {
                System.out.println(parameterType);
            }

            try {
//                使用ByteBuddy生成动态代理对象
                // 动态代理对象,序列化发给对端
                Object instance = method.getParameterTypes()[2].newInstance();

            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
