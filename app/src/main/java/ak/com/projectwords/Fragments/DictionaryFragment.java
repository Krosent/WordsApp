package ak.com.projectwords.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ak.com.projectwords.Activities.WordDetailsActivity;
import ak.com.projectwords.Adapters.DictionaryAdapter;
import ak.com.projectwords.Interfaces.OnItemClicked;
import ak.com.projectwords.Helper.Helpers;
import ak.com.projectwords.POJOs.Word;
import ak.com.projectwords.R;
import ak.com.projectwords.Services.AppDatabase;

import static android.os.AsyncTask.execute;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DictionaryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DictionaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DictionaryFragment extends Fragment  implements OnItemClicked {
    private OnFragmentInteractionListener mListener;
    private RecyclerView dictionaryRecyclerView;
    private FloatingActionButton addWordFloatingActionButton;
    private DictionaryAdapter adapter;
    private AppDatabase db;

    public DictionaryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DictionaryFragment newInstance() {
        DictionaryFragment fragment = new DictionaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void init(View v) {
        dictionaryRecyclerView = v.findViewById(R.id.dictionaryRecyclerView);
        addWordFloatingActionButton = v.findViewById(R.id.addWordFloatingActionButton);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void configureAdapter(AppDatabase db) {
        adapter = new DictionaryAdapter(getContext());
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        dictionaryRecyclerView.setLayoutManager(layoutManager);
        dictionaryRecyclerView.setAdapter(adapter);
        fillAdapterFromLDB(db, adapter); // Fills adapter with data from local database
        adapter.setOnItemClicked(this);
        Helpers.createItemTouchHelper(adapter).attachToRecyclerView(dictionaryRecyclerView);
    }

    private void fillAdapterFromLDB(AppDatabase db, DictionaryAdapter adapter) {
        List<Word> words = null;
        try {
            words = new AsyncTask<Void, Void, List<Word>>() {

                @Override
                protected List<Word> doInBackground(Void... voids) {
                    return db.service().fetchAllWords();
                }
            }.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (words != null) {
            for (Word word : words) {
                adapter.add(word);
            }
        }
    }

    private AlertDialog.Builder initBuilder() {
        View v = getLayoutInflater().inflate(R.layout.dialog_add_new_word, null);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setView(v);
        return mBuilder;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dictionary, container, false);
        init(view);

        //Database instance
        db = AppDatabase.getDatabase(getContext());
        // Adapter configuration
        configureAdapter(db);

        // Floating action button which uses for opening alert dialog
        addWordFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create Dialog
                AlertDialog dialog = initBuilder().create();

                // Customization of dialog
                dialog.setTitle(R.string.dialog_title);
                dialog.setIcon(R.drawable.ic_notebook);
                dialog.show();

                // init dialog inner views
                TextInputEditText addWordInputEditText = dialog.findViewById(R.id.addWordInputEditText);
                Button addWordButton = dialog.findViewById(R.id.addWordButton);
                TextInputLayout WordInputWrapper = dialog.findViewById(R.id.wordWrapper);

                // Get data from input field if button is clicked
                addWordButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(addWordInputEditText.getText().length() > 0) {
                            // Create model
                            Word word = new Word(Helpers
                                    .setCapitalLetter(addWordInputEditText.getText().toString()));
                            // Insert model to local db
                            execute(() -> db.service().insert(word));
                            // Insert model to adapter
                            adapter.add(word);

                            dialog.hide();
                            WordInputWrapper.setErrorEnabled(false);
                        } else {
                            WordInputWrapper.setError("Field is empty!");
                        }

                    }
                });

            }
        });
        return view;
    }

    @Override
    public void onItemClicked(int position) {
        if(adapter.getWords() != null) {
            Intent intent = new Intent(getActivity(), WordDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("word", adapter.getWords().get(position).getWord());
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Log.e("Words list is", " null");
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
