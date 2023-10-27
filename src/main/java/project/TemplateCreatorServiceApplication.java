package project;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//
import java.time.LocalDateTime;


@SpringBootApplication
public class TemplateCreatorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateCreatorServiceApplication.class, args);
        String jsonString = "{\"enableMultiple\":true,\"objectNameTemplate\":\"%b%\",\"objectSmTypeId\":null,\"objectElementId\":null,\"parentObject\":[{\"id\":2846432,\"name\":\"\"}],\"enableTemplate\":true,\"createDashboardWithTheNameIfDoesntExist\":null}";

        //Creating a JSON Object
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "studytonight");
        jsonObject.put("address", "Noida");
        //Converting JSON Object using toString() method
        String myJSONString = jsonObject.toString();
        System.out.println("JSON String: "+myJSONString);


        String myJSON ="{\"name\":\"studytonight\",\"address\":\"Noida\"}";
        //JSON Parser from Gson Library
        JsonParser parser = new JsonParser();
        //Creating JSONObject from String using parser
        JsonObject JSONObject = parser.parse(myJSON).getAsJsonObject();
        System.out.println("Object: "+JSONObject);





    }

}
