package br.com.angeloni.precos.server.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JSONDateSerializer extends StdSerializer<LocalDate> {

  protected JSONDateSerializer() {
    this(null);
  }

  protected JSONDateSerializer(Class<LocalDate> t) {
    super(t);
  }

  @Override
  public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
  }
}
