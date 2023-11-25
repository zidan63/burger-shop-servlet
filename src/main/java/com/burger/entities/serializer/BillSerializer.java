package com.burger.entities.serializer;

import com.burger.entities.Bill;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class BillSerializer extends JsonSerializer<Bill> {
    @Override
    public void serialize(Bill bill, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", bill.getId());
        jsonGenerator.writeEndObject();
    }
}
