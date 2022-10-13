package protoservice.channel;

import com.google.protobuf.*;
import protoservice.service.SearchService;
import protoservice.service.SearchServiceSync;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class NettyRpcChannel implements RpcChannel {

    SearchService searchService = new SearchService();
    SearchServiceSync searchServiceSync = new SearchServiceSync();

    //
    private final Map<String, Map<Long,RpcCallback<Message>>> callbackMap = new ConcurrentHashMap<>();

    private final AtomicLong counter = new AtomicLong(0);
    // shall be mapped
    @Override
    public void callMethod(Descriptors.MethodDescriptor method, RpcController controller, Message request, Message responsePrototype, RpcCallback<Message> done) {
        // 序列化 发送给对端
        System.out.println("callMethod");

        // async rpc call
        searchService.search(controller, (Empty) (request), new RpcCallback<Empty>() {
            @Override
            public void run(Empty parameter) {
                done.run(parameter);
            }
        });

       if(!callbackMap.containsKey(method.getFullName())){
           callbackMap.put(method.getFullName(),new ConcurrentHashMap<>());
       }
       long id = counter.incrementAndGet();
       callbackMap.get(method.getFullName()).put(id,done);

        // sync rpc call
        try {
            searchServiceSync.search(controller,Empty.getDefaultInstance());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        callbackMap.get(method.getFullName()).get(id).run(Empty.getDefaultInstance());
    }
}
