package project;

//import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;


@SpringBootApplication
public class TemplateCreatorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateCreatorServiceApplication.class, args);
        String jsonString = "{\"enableMultiple\":true,\"objectNameTemplate\":\"%b%\",\"objectSmTypeId\":null,\"objectElementId\":null,\"parentObject\":[{\"id\":2846432,\"name\":\"\"}],\"enableTemplate\":true,\"createDashboardWithTheNameIfDoesntExist\":null}";

//        JSONObject codeRollsJSONObject = new JSONObject(jsonString);
//
//        System.out.println(codeRollsJSONObject);
//        System.out.println(LocalDateTime.now());





    }

}
