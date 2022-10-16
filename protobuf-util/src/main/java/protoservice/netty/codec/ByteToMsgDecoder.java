package protoservice.netty.codec;

import io.github.phiysng.proto.Basic;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import protoservice.netty.ValteUtil;

import java.util.List;

public class ByteToMsgDecoder extends ByteToMessageDecoder {

    SERVER_DECODE_STATUS status = SERVER_DECODE_STATUS.INIT;

    int headerSize = 0;
    int bodySize = 0;

    // header
    Basic.MetaMsg header = null;
    /**
     *
     *  magic num : 2 bytes
     *  length:  2 bytes
     *  header : meta info : variable
     *  body : variable
     *
     * @param ctx           the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param in            the {@link ByteBuf} from which to read data
     * @param out           the {@link List} to which decoded messages should be added
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (status) {
            case INIT: {
                // magic number 和 length field 共4字节
                if(in.readableBytes() < 4)
                    return;
                short magicNumber =  in.readShort();
                if(magicNumber != ValteUtil.MAGIC_NUM){
                    // fatal ERROR handle
                    // maybe just close the connection.
                    ctx.channel().closeFuture().addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            System.out.println("CLOSE ILLEGAL CONNECTION");
                        }
                    });
                    ctx.channel().close();
                    return;
                }
                headerSize = in.readShort();
                System.out.format("decode msg, header size:%d",headerSize);
                status = SERVER_DECODE_STATUS.HEADER;
                return;
            }
            case HEADER: {

                if(headerSize > in.readableBytes())
                    return;
                var headerBytes = in.readBytes(headerSize);

                byte[] bytes = new byte[headerSize];
                headerBytes.readBytes(bytes);
                var metaMsg = Basic.MetaMsg.getDefaultInstance().getParserForType().parseFrom(bytes);
                out.add(metaMsg);
                status = SERVER_DECODE_STATUS.FINISH;
                return;
            }
            case BODY: {

                return;
            }

        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);

        if(status == SERVER_DECODE_STATUS.FINISH){
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
