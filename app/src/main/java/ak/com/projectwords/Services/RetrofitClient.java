package ak.com.projectwords.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by artyomkuznetsov on 2/19/18.
 */

public class RetrofitClient {
    public static Retrofit client = null;
    private final String base_url = "https://wordsapiv1.p.mashape.com/words/";

    public Retrofit getClient() {
        if (client==null) {
            client = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return client;
    }
}
