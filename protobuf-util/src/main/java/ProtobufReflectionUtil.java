import com.google.protobuf.*;
import com.google.protobuf.util.JsonFormat;
import io.github.phiysng.proto.Basic;

import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class ProtobufReflectionUtil {

    /**
     * 通过class获取protobuf的defaultInstance 使用了protobuf-java的内部api
     *
     * @param clazz protobuf类名
     * @return protobuf默认构造的实例
     */
    public static <T extends Message> T getDefaultInstance(Class<T> clazz) {
        // 通过方法名获得方法,然后通过反射的方式获取`getDefaultInstance`的值
        return Internal.getDefaultInstance(clazz);
    }

//    public static void main(String[] args) throws InvalidProtocolBufferException {
//        Basic.Owl.Builder builder = Basic.Owl.newBuilder();
//        builder.putData(1, Any.pack(StringValue.newBuilder().setValue("100").build()));
////        dataMap.put(1, Any.pack(Datas.Rpc.newBuilder().setFunctionName("Be Cool").build()));
////        JsonFormat.printer().print(Datas.Owl.getDefaultInstance());
//        JsonFormat.printer().print(builder.build());
//    }

    static void Foo(String s){
        System.out.println(s);
    }

    static void Boo(ProtobufReflectionUtil util, String s) {
        util.Bar(s);
        System.out.println(s);
    }

    void Bar(String s){
        System.out.println(s);
    }

    public static void main(String[] args) {
        Consumer<String> consumer = ProtobufReflectionUtil::Foo;

        consumer.accept("Hello World");

        ProtobufReflectionUtil util = new ProtobufReflectionUtil();
        BiConsumer<ProtobufReflectionUtil,String> consumer1 = ProtobufReflectionUtil::Bar;
        consumer1.accept(util,"Hello World");


        // BiConsumer的本质是一个实现了accpet接口的子类
        // 你可以传入一个静态函数,然后accept的参数就全部给方法了
        // 如果是成员函数，则第一个作为对象，其他的作为对象的引用
//        BiConsumer<ProtobufReflectionUtil,String> consumer2 = new BiConsumer<ProtobufReflectionUtil, String>() {
//            @Override
//            public void accept(ProtobufReflectionUtil util1, String s) {
//                Boo(util1, s);
//            }
//        };
        BiConsumer<ProtobufReflectionUtil,String> consumer2 = ProtobufReflectionUtil::Boo;
        consumer2.accept(util,"Hello World");

        Consumer<String> consumer3 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                util.Bar(s);
            }
        };
//        var x = String::concat("");
    }
}
