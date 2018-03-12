package ak.com.projectwords.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ak.com.projectwords.Interfaces.OnItemClicked;
import ak.com.projectwords.POJOs.Word;
import ak.com.projectwords.R;
import ak.com.projectwords.Services.AppDatabase;

/**
 * Created by artyomkuznetsov on 2/23/18.
 */

public class DictionaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Word> words;
    AppDatabase db;
    Context context;
    OnItemClicked onItemClicked;

    public DictionaryAdapter(Context context) {
        this.words = new ArrayList<>();
        db = AppDatabase.getDatabase(context);
        this.context = context;
    }

    private class DictionaryViewHolder extends RecyclerView.ViewHolder  {
        TextView wordTextView;

        public DictionaryViewHolder(View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.wordTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClicked.onItemClicked(getAdapterPosition());
                }
            });
        }


    }


    public void add(Word word) {
        words.add(word);
        notifyItemInserted(words.size() - 1);
    }

    public void remove(int position) {
        removeFromDB(words.get(position).getWord());
        words.remove(position);
    }

    public void clear() {
        words.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.dictionary_item, parent, false);
        final RecyclerView.ViewHolder viewHolder = new DictionaryAdapter.DictionaryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Word wordOnPosition = words.get(position);
        ((DictionaryViewHolder) holder).wordTextView.setText(wordOnPosition.getWord());
    }

    @Override
    public int getItemCount() {
        if (words == null) {
            return 0;
        }
        return words.size();
    }

    private void removeFromDB(final String word) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.service().deleteWord(word);
                return null;
            }
        }.execute();
    }

    public void setOnItemClicked(OnItemClicked itemClickable) {
        this.onItemClicked = itemClickable;
    }

    public List<Word> getWords() {
        return words;
    }
}
