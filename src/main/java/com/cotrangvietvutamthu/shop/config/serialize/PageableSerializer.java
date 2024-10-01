package com.dirty.shop.config.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public class PageableSerializer extends JsonSerializer<Pageable> {
    @Override
    public void serialize(Pageable value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value.isPaged()) {
            gen.writeStartObject();
            gen.writeNumberField("page", value.getPageNumber());
            gen.writeNumberField("size", value.getPageSize());
            gen.writeNumberField("offset", value.getOffset());
            gen.writeEndObject();
        } else {
            gen.writeString("unpaged");
        }
    }
}