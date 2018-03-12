package ak.com.projectwords.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import ak.com.projectwords.Adapters.WordDescriptionAdapter;
import ak.com.projectwords.Helper.Helpers;
import ak.com.projectwords.POJOs.WordFullData.WordCharacterization;
import ak.com.projectwords.R;
import ak.com.projectwords.Services.RestService;
import ak.com.projectwords.Services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar descriptionProgressBar;
    private final RestService restClient = new RetrofitClient().getClient().create(RestService.class);
    private Bundle bundle;
    private WordCharacterization characterization;
    private WordDescriptionAdapter adapter;

    private void init() {
        recyclerView = findViewById(R.id.descriptInfoRecycleView);
        descriptionProgressBar = findViewById(R.id.descriptionProgressBar);
        descriptionProgressBar.setVisibility(View.GONE);
    }

    private void initAdapter() {
        adapter = new WordDescriptionAdapter(null);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new Helpers.VerticalSpaceItemDecoration(25));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_details);
        bundle = getIntent().getExtras();
        getSupportActionBar().setTitle(bundle.getString("word"));

        init();

        initAdapter();

        getDataFromRest(bundle.getString("word"), this);


    }




    private void getDataFromRest(String keyword, Activity activity) {
        descriptionProgressBar.setVisibility(View.VISIBLE);

        Call<WordCharacterization> call = restClient
                .getDetailInfoAboutWord(keyword);
        call.enqueue(new Callback<WordCharacterization>() {
            @Override
            public void onResponse(Call<WordCharacterization> call, Response<WordCharacterization> response) {
                characterization = response.body();
                adapter = new WordDescriptionAdapter(characterization);
                recyclerView.setAdapter(adapter);
                descriptionProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<WordCharacterization> call, Throwable t) {
                Helpers.showToast(activity, getString(R.string.error_response));
                descriptionProgressBar.setVisibility(View.GONE);
            }
        });
    }




}
