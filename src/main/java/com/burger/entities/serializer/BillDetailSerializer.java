package com.burger.entities.serializer;

import com.burger.entities.BillDetail;
import com.burger.entities.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class BillDetailSerializer extends JsonSerializer<BillDetail> {
    @Override
    public void serialize(BillDetail billDetail, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", billDetail.getId());
        jsonGenerator.writeEndObject();
    }
}