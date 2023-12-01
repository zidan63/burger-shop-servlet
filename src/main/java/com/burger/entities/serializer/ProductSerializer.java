package com.burger.entities.serializer;

import com.burger.entities.Bill;
import com.burger.entities.Product;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ProductSerializer extends JsonSerializer<Product> {
    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", product.getId());
        jsonGenerator.writeEndObject();
    }
}
