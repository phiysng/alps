package protoservice.netty;

import io.github.phiysng.proto.Basic;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import protoservice.netty.codec.ByteToMsgDecoder;

import java.net.InetSocketAddress;

import static protoservice.netty.ValteUtil.SERVER_PORT;

/**
 * RPC Server
 * decode msg and write response if any
 * -----------------------------------------------
 * | magic number( | | | | | | |
 * -----------------------------------------------
 */
@Slf4j
public class PRCServer {


    public static void main(String[] args) {
        log.info("---------------------");
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup());
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ByteToMsgDecoder());
                ch.pipeline().addLast(new SimpleChannelInboundHandler<Basic.MetaMsg>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, Basic.MetaMsg msg) throws Exception {
                        ctx.channel().close();
                    }
                });
            }
        });
        serverBootstrap.bind(new InetSocketAddress(SERVER_PORT));
    }
}
