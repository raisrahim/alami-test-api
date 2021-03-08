package alami.test.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 *
 * @author Muhammad Rais Rahim
 */
public abstract class AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected ResultActions postObjectAsJson(String path, Object obj) throws Exception {

        String content;
        if (obj instanceof String) {
            content = (String) obj;
        } else {
            content = objectMapper.writeValueAsString(obj);
        }
        return mockMvc.perform(
                post(path).content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

//    protected <T> T copyObject(T source) {

//        Object target = BeanUtils.instantiateClass(source.getClass());
//
//        BeanUtils.copyProperties(source, target);
//
//        return (T) target; 
//    }
}
