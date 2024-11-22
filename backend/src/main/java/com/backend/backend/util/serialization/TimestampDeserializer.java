package com.backend.backend.util.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.cloud.Timestamp;

public class TimestampDeserializer extends JsonDeserializer<Timestamp>{
    @Override
    public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException{
        return Timestamp.parseTimestamp(p.getText());
    }
}
