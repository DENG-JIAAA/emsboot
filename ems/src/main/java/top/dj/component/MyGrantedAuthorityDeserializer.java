package top.dj.component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import top.dj.POJO.DO.Role;

import java.io.IOException;

/**
 * 反序列化器
 *
 * @author dj
 * @date 2021/2/26
 */
@Component(value = "myGrantedAuthorityDeserializer")
public class MyGrantedAuthorityDeserializer extends StdDeserializer<SimpleGrantedAuthority> {

    public MyGrantedAuthorityDeserializer() {
        super(Role.class);
    }

    /**
     * @param jsonParser             Json解析器
     * @param deserializationContext 反序列化上下文
     * @return 授权
     * @throws IOException             IO异常
     * @throws JsonProcessingException Json处理异常
     */
    @Override
    public SimpleGrantedAuthority deserialize(JsonParser jsonParser,
                                              DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode tree = jsonParser.getCodec().readTree(jsonParser);
        return new SimpleGrantedAuthority(tree.get("authority").textValue());
    }


}
