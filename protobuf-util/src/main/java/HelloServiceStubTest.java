import com.google.protobuf.*;
import io.github.phiysng.proto.PingPong;

public class HelloServiceStubTest {
    public static void main(String[] args) {
        RpcChannel channel = new RpcChannel() {
            @Override
            public void callMethod(Descriptors.MethodDescriptor method, RpcController controller, Message request, Message responsePrototype, RpcCallback<Message> done) {
                System.out.println("callmethod0");
                // method : method name
                // controller : not used now
                // MARK : responsePrototype 用于反序列化
                // done : callback when response arrived
                done.run(PingPong.Response.newBuilder().setCode(400).setMsg("Success").build());
            }
        };
        PingPong.HelloService service = PingPong.HelloService.newStub(channel);
        service.handshake(null, Int64Value.newBuilder().setValue(3219).build(),
                new RpcCallback<PingPong.Response>() {
                    @Override
                    public void run(PingPong.Response parameter) {
                        // async callback called
                        System.out.println("param received");
                    }
                });
    }
}
