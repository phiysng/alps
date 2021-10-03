import io.github.phiysng.proto.Datas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProtobufReflectionUtilTest {

    @Test
    void getDefaultInstance() {
        assertEquals(ProtobufReflectionUtil.getDefaultInstance(Datas.Rpc.class), Datas.Rpc.getDefaultInstance());
    }
}