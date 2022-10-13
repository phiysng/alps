package protoservice.service;

import com.google.protobuf.Empty;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import io.github.phiysng.proto.Basic;

public class SearchServiceSync implements Basic.SearchService.BlockingInterface {
    @Override
    public Empty search(RpcController controller, Empty request) throws ServiceException {
        System.out.println(getClass().getName());
        return Empty.newBuilder().getDefaultInstanceForType();
    }
}
