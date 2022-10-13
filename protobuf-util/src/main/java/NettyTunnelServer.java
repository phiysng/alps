import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import protoservice.service.SearchService;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NettyTunnelServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup());
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new HttpServerCodec());
                ch.pipeline().addLast(new HttpObjectAggregator(1048576));
                ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                        DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1 , HttpResponseStatus.OK);
                        ctx.writeAndFlush(response);

                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.group(clientGroup)
                                .channel(NioSocketChannel.class)
                                .remoteAddress(msg.uri().substring(0,msg.uri().length()-4),443)
                                .handler(new ChannelInitializer<SocketChannel>() {
                                    @Override
                                    protected void initChannel(SocketChannel ch) throws Exception {
                                        ch.pipeline().addLast(new SimpleChannelInboundHandler<SocketChannel>() {

                                            @Override
                                            protected void channelRead0(ChannelHandlerContext ctx, SocketChannel msg) throws Exception {
                                                DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1 , HttpResponseStatus.OK);
                                                ctx.writeAndFlush(response);
                                            }
                                        });
                                    }
                                });
                    }
                });
            }
        });
        serverBootstrap.bind(new InetSocketAddress(8443));


    }
}
