package com.dirty.shop.config.serialize;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public abstract class ToStringSerializedTypeAdapter<T> extends TypeAdapter<T> {
    @Override
    public void write(JsonWriter out, T value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toString());
        }
    }
}
