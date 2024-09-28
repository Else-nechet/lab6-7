package com.nechet.server.fileManipulation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class WriteJSONCollection<T> implements WriteToObject<T> {

    @Override
    public void write(String path,T obj) throws IOException  {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();;
        String json = gson.toJson(obj);
        FileOutputStream out = new FileOutputStream(path + "/Vehicles.json");
        Writer writer = new OutputStreamWriter(out);
        writer.write(json);
        writer.close();
    }
}
