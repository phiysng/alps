import com.google.protobuf.*;
import com.google.protobuf.util.JsonFormat;
import io.github.phiysng.proto.Basic;


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

    public static void main(String[] args) throws InvalidProtocolBufferException {
        Basic.Owl.Builder builder = Basic.Owl.newBuilder();
        builder.putData(1, Any.pack(StringValue.newBuilder().setValue("100").build()));
//        dataMap.put(1, Any.pack(Datas.Rpc.newBuilder().setFunctionName("Be Cool").build()));
//        JsonFormat.printer().print(Datas.Owl.getDefaultInstance());
        JsonFormat.printer().print(builder.build());
    }
}
