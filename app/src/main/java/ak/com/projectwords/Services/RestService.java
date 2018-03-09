package ak.com.projectwords.Services;

import ak.com.projectwords.POJOs.Antonyms;
import ak.com.projectwords.POJOs.Synonyms;
import ak.com.projectwords.POJOs.WordFullData.WordCharacterization;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by artyomkuznetsov on 2/19/18.
 */

public interface RestService {
    @GET("{keyword}/antonyms")
    @Headers("X-Mashape-Key: 3doF2PJGahmsha4q4RbS3sETcEj2p1jpnzqjsnmPU7A9rB1xXP")
    Call<Antonyms> getAntonyms(@Path(value = "keyword", encoded = true) String keyword);

    @GET("{keyword}/synonyms")
    @Headers("X-Mashape-Key: 3doF2PJGahmsha4q4RbS3sETcEj2p1jpnzqjsnmPU7A9rB1xXP")
    Call<Synonyms> getSynonyms(@Path(value = "keyword", encoded = true) String keyword);

    @GET("{keyword}")
    @Headers("X-Mashape-Key: 3doF2PJGahmsha4q4RbS3sETcEj2p1jpnzqjsnmPU7A9rB1xXP")
    Call<WordCharacterization> getDetailInfoAboutWord(@Path(value = "keyword", encoded = true) String keyword);

}
