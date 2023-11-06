package project.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ParseJson {
    private ParseJson() {}

    public static Object parse(String parse) {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser;
        try {
            parser = factory.createParser(parse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonNode actualObj;
        try {
            actualObj = mapper.readTree(parser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return actualObj;
    }
}
