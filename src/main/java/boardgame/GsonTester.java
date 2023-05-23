package boardgame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTester {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }
}
