package protoservice.netty;

import io.github.phiysng.proto.Basic;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import protoservice.netty.codec.ClientInboundHandler;

import static protoservice.netty.ValteUtil.MAGIC_NUM;
import static protoservice.netty.ValteUtil.SERVER_PORT;

public class RPCClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(workGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ClientInboundHandler());
                }
            });
            ChannelFuture channelFuture = b.connect("127.0.0.1", SERVER_PORT).sync();

            if(channelFuture.isSuccess()){
                var channel = channelFuture.channel();
                var buffer = Unpooled.buffer();
                buffer.writeShort(MAGIC_NUM);

                Basic.MetaMsg msg = Basic.MetaMsg.newBuilder().setType(3).setFunctionName("Foo").setBodySize(0).build();
                var length= msg.getSerializedSize();
                var bytes= msg.toByteArray();
                System.out.format("getSerializedSize：%d getSerializedSizeP:%d",length,bytes.length);
                buffer.writeShort(length);
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            workGroup.shutdownGracefully().sync();
        }
    }
}
