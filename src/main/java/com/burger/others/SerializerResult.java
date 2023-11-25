package com.burger.others;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializerResult {
    public static String serialize(Object object) throws JsonProcessingException {
        ObjectMapper OM = new ObjectMapper();
        return OM.writeValueAsString(object);
    }
}
