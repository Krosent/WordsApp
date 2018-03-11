package ak.com.projectwords.Fragments;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ak.com.projectwords.Adapters.FoundWordsAdapter;
import ak.com.projectwords.POJOs.Antonyms;
import ak.com.projectwords.POJOs.Synonyms;
import ak.com.projectwords.POJOs.Word;
import ak.com.projectwords.Other.Helpers;
import ak.com.projectwords.POJOs.WordFullData.Rhymes;
import ak.com.projectwords.R;
import ak.com.projectwords.Services.RestService;
import ak.com.projectwords.Services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ak.com.projectwords.Other.Helpers.hideKeyboard;
import static ak.com.projectwords.Other.Helpers.isNetworkConnected;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // Creating UI elements variables
    private static SearchFragment fragment;
    EditText searchEditText;
    Spinner selectTypeSpinner;
    FoundWordsAdapter adapter;
    ProgressBar searchProgressBar;
    RecyclerView WordsRecyclerView;
    // *********

    // Timer variable for search edit text listener
    Timer timer;
    // *********

    // Retrofit initialization is here
    RestService service = new RetrofitClient().getClient().create(RestService.class);
    // ******

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        if (fragment == null) {
            fragment = new SearchFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init(View view){
        WordsRecyclerView = view.findViewById(R.id.WordsRecyclerView);
        searchEditText = view.findViewById(R.id.searchEditText);
        selectTypeSpinner = view.findViewById(R.id.selectTypeSpinner);
        searchProgressBar = view.findViewById(R.id.searchProgressBar);
        searchProgressBar.setVisibility(View.GONE);
    }

    private void initSpinnerAdapter() {
        ArrayAdapter<CharSequence> choiceTypeAdapter;
        choiceTypeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.selector_search_entries, android.R.layout.simple_spinner_dropdown_item);
        selectTypeSpinner.setAdapter(choiceTypeAdapter);
    }

    private void initMainAdapter() {
        adapter = new FoundWordsAdapter();
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        WordsRecyclerView.addItemDecoration(new Helpers.VerticalSpaceItemDecoration(25));

        WordsRecyclerView.setLayoutManager(layoutManager);
        WordsRecyclerView.setAdapter(adapter);
    }

    private void searchTextFieldConfig() {
        // Search Edit Text Listener
        searchEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v, getActivity());
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                // Implement progress bar visible here
                Helpers.setVisibilityOfProgressBar(View.VISIBLE, getActivity(),
                        searchProgressBar);
                // ******************************
                // Send request only after 600 ms text changed
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (editable.toString().trim().length() > 0) {
                            // Make retrofit call here
                            if(isNetworkConnected(getContext())) {
                                fillAdapterFromRest(selectTypeSpinner.getSelectedItem().toString(),
                                        editable);
                            } else {
                                Helpers.showToast(getContext(), getString(R.string.no_internet_message));
                            }
                            // ***********************

                            // if item of spinner has been changed then
                            selectTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parentView,
                                                           View selectedItemView, int position,
                                                           long id) {
                                    Helpers.setVisibilityOfProgressBar(View.VISIBLE, getActivity(),
                                            searchProgressBar);
                                    // RETROFIT REQUEST HERE
                                    if(isNetworkConnected(getContext())) {
                                        fillAdapterFromRest(selectTypeSpinner.getSelectedItem().toString(),
                                                editable);
                                    } else {
                                        Helpers.showToast(getContext(), getString(R.string.no_internet_message));
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parentView) {
                                }

                            });
                        } else {
                            clearAdapter();
                            Helpers.setVisibilityOfProgressBar(View.GONE, getActivity(),
                                    searchProgressBar);
                        }
                    }
                }, 600);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Init of view elements
        init(view);

        initSpinnerAdapter();

        searchTextFieldConfig();

        // Found Words Adapter Configuration
        initMainAdapter();
        // ******************************

        return view;
    }

    private void fillAdapterFromRest(String type, Editable editable) {
        // Clear adapter from old data
        clearAdapter();

        if (type.equals("Synonyms")) {
            Call<Synonyms> call = service.getSynonyms(editable.toString());
            call.enqueue(new Callback<Synonyms>() {
                @Override
                public void onResponse(Call<Synonyms> call, Response<Synonyms> response) {
                    if (response.body() != null) {
                        List<String> synonyms = response.body().getSynonyms();
                        for (String synonym : synonyms) {
                            Word word = new Word(Helpers.setCapitalLetter(synonym));
                            adapter.add(word);
                        }
                    } else {
                        Helpers.showToast(getContext(),
                                "Sorry, we didn't find such word in synonyms");
                    }
                    Helpers.setVisibilityOfProgressBar(View.GONE, getActivity(),
                            searchProgressBar);
                }

                @Override
                public void onFailure(Call<Synonyms> call, Throwable t) {
                    displayError();
                }
            });

        } else if (type.equals("Antonyms")) {
            Call<Antonyms> call = service.getAntonyms(editable.toString());
            call.enqueue(new Callback<Antonyms>() {
                @Override
                public void onResponse(Call<Antonyms> call, Response<Antonyms> response) {
                    if (response.body() != null) {
                        List<String> antonyms = response.body().getAntonyms();
                        for (String antonym : antonyms) {
                            Word word = new Word(Helpers.setCapitalLetter(antonym));
                            adapter.add(word);
                        }
                    } else {
                        Helpers.showToast(getContext(),
                                "Sorry, we didn't find such word in antonyms");
                    }
                    Helpers.setVisibilityOfProgressBar(View.GONE, getActivity(),
                            searchProgressBar);
                }

                @Override
                public void onFailure(Call<Antonyms> call, Throwable t) {
                    displayError();
                }
            });
        } else if (type.equals("Rhymes")) {
            // Make call to collect data from the rest api
            Call<Rhymes> call = service.getRhymes(editable.toString());
            call.enqueue(new Callback<Rhymes>() {
                @Override
                public void onResponse(Call<Rhymes> call, Response<Rhymes> response) {
                    if (response.body() != null && response.body().getRhymes().getAll() != null) {
                        List<String> rhymes = response.body().getRhymes().getAll();
                        for (String rhyme : rhymes) {
                            Word word = new Word(Helpers.setCapitalLetter(rhyme));
                            adapter.add(word);
                        }
                        Helpers.setVisibilityOfProgressBar(View.GONE, getActivity(),
                                searchProgressBar);
                    } else {
                        Helpers.showToast(getContext(),
                                "Sorry, we didn't find such word in rhymes");
                    }
                }

                @Override
                public void onFailure(Call<Rhymes> call, Throwable t) {
                  displayError();
                }
            });
        }
    }

    private void displayError() {
        Helpers.showToast(getActivity(), getString(R.string.error_response));
        Helpers.setVisibilityOfProgressBar(View.GONE, getActivity(),
                searchProgressBar);
    }

    private void clearAdapter() {
        // Clear recycle view's adapter on the ui thread
        if(getActivity() != null) {
            Runnable mRunnable = new Runnable() {
                public void run() {
                    adapter.clear();
                }
            };
            getActivity().runOnUiThread(mRunnable);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}
