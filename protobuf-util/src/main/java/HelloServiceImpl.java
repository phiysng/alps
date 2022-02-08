import com.google.protobuf.Int64Value;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import io.github.phiysng.proto.PingPong;

public class HelloServiceImpl extends PingPong.HelloService {
    @Override
    public void handshake(RpcController controller, Int64Value request, RpcCallback<PingPong.Response> done) {
        // TODO: 实现业务逻辑
    }
}
