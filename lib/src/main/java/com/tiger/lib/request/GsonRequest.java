package com.tiger.lib.request;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;

public class GsonRequest<T> implements RequestAdapter.RequestBodys<T> {
    public Gson gson;
    public TypeAdapter typeAdapter;

    public GsonRequest(Gson gson, TypeAdapter typeAdapter) {
        this.gson = gson;
        this.typeAdapter = typeAdapter;
    }
    @Override
    public  T toBody(ResponseBody body) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(body.charStream());
        try {
            return (T) typeAdapter.read(jsonReader);
        } finally {
            body.close();
        }
    }


}
