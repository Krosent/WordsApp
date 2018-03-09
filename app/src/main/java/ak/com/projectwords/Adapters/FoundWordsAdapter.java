package ak.com.projectwords.Adapters;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ak.com.projectwords.POJOs.Word;
import ak.com.projectwords.Other.Helpers;
import ak.com.projectwords.R;
import ak.com.projectwords.Services.AppDatabase;

import static android.os.AsyncTask.execute;

/**
 * Created by artyomkuznetsov on 1/18/18.
 */

public class FoundWordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ADD_TO_DB = 1;
    private static final int REMOVE_FROM_DB = 0;
    RecyclerView.ViewHolder viewHolder;
    private List<Word> words;
    AppDatabase db;

    public FoundWordsAdapter() {
        this.words = new ArrayList<>();
    }


    public void add(Word word) {
        this.words.add(word);
        notifyItemInserted(words.size() - 1);
    }

    public void clear() {
        words.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView foundWordTextView;
        ImageButton saveWordButton;

        public ViewHolder(View v) {
            super(v);
            foundWordTextView = v.findViewById(R.id.wordTextView);
            saveWordButton = v.findViewById(R.id.saveWordImageButton);
            saveWordButton.setTag(ADD_TO_DB);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewHolder = null;
        View view;
        db = AppDatabase.getDatabase(parent.getContext());
        view = layoutInflater.inflate(R.layout.search_item, parent, false);
        final RecyclerView.ViewHolder viewHolder = new ViewHolder(view);

        ((ViewHolder) viewHolder).saveWordButton.setOnClickListener(view1 -> {
            Word clickedWord = words.get(viewHolder.getAdapterPosition());
            ImageButton clickButton = ((ViewHolder) viewHolder).saveWordButton;
            int resource = (int) clickButton.getTag();

            if (resource == ADD_TO_DB) {
                clickButton.setImageResource(R.drawable.ic_remove_circle_black_24dp);
                clickButton.setTag(REMOVE_FROM_DB);
                // HERE IS SHOULD BE ADDING WORD TO THE DATABASE
                execute(() -> db.service().insert(clickedWord));
                // ***********
                Log.e("Object added", clickedWord.toString());

            } else if (resource == REMOVE_FROM_DB) {
                clickButton.setImageResource(R.drawable.add_to_dictionary);
                clickButton.setTag(ADD_TO_DB);

                // Remove word from DATABASE
                execute(() -> db.service().deleteWord(clickedWord.getWord()));
                Log.e("Object tried tb deleted", clickedWord.toString());
                // ***********
            }




            Helpers.showToast(parent.getContext(),
                    "Add button was clicked for: " + clickedWord.getWord());
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Word currentWord = words.get(position);
        ((ViewHolder) holder).foundWordTextView.setText(currentWord.getWord());

        if (isStored(currentWord.getWord(), db)) {
            ((ViewHolder) holder).saveWordButton
                    .setImageResource(R.drawable.ic_remove_circle_black_24dp);
            ((ViewHolder) holder).saveWordButton.setTag(REMOVE_FROM_DB);
        } else {
            ((ViewHolder) holder).saveWordButton
                    .setImageResource(R.drawable.add_to_dictionary);
            ((ViewHolder) holder).saveWordButton.setTag(ADD_TO_DB);
        }
    }

    @Override
    public int getItemCount() {
        if (words.size() != 0) {
            return words.size();
        }
        return 0;
    }

    private boolean isStored(String word, AppDatabase db) {
        Word checkWord = new Word(word);
        Word result = null;
        try {
            result = new AsyncTask<Word, Void, Word>() {
                @Override
                protected Word doInBackground(Word... words) {
                    return db.service().fetchWord(words[0].getWord());
                }
            }.execute(checkWord).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result != null) {
            return true;
        } else {
            return false;
        }
    }

}
