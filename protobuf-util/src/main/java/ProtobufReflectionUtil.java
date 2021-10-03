import com.google.protobuf.Internal;
import com.google.protobuf.Message;


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
}
