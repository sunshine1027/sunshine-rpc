import com.sunshine.rpc.core.serializer.KryoSerializer;
import org.junit.Test;


import java.io.IOException;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class Test1 {
    @Test
    public void kryoTest() throws IOException {
        KryoSerializer serializer = new KryoSerializer();
        BB bb = new BB(10);
        AA aa = new AA(5, bb);
        byte[] bytes = serializer.serialize(aa);
        KryoSerializer serializer1 = new KryoSerializer();
        AA cc = (AA) serializer1.deserialize(bytes, AA.class);
        System.out.println(cc);
    }
}
