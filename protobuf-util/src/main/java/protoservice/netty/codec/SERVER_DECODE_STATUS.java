package protoservice.netty.codec;

public enum SERVER_DECODE_STATUS {
    INIT,
    HEADER,
    BODY,
    FINISH,
}
