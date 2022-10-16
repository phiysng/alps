import io.github.phiysng.proto.Basic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProtobufReflectionUtilTest {

    @Test
    void getDefaultInstance() {
        assertEquals(ProtobufReflectionUtil.getDefaultInstance(Basic.Rpc.class), Basic.Rpc.getDefaultInstance());
    }
}