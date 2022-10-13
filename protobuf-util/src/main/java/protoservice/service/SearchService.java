package protoservice.service;

import com.google.protobuf.Empty;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import io.github.phiysng.proto.Basic;

public class SearchService implements Basic.SearchService.Interface {
    @Override
    public void search(RpcController controller, Empty request, RpcCallback<Empty> done) {
        // call function done when task is finished, can be called outside this function.
        done.run(Empty.getDefaultInstance());
        System.out.println("Searching In Another Machine!");
    }
}
