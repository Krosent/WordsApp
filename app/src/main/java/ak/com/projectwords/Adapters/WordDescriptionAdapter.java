package ak.com.projectwords.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ak.com.projectwords.POJOs.WordFullData.Result;
import ak.com.projectwords.POJOs.WordFullData.WordCharacterization;
import ak.com.projectwords.R;

/**
 * Created by artyomkuznetsov on 2/26/18.
 */

public class WordDescriptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   private List<Result> results;

    public WordDescriptionAdapter(WordCharacterization characterization) {
        if(characterization != null) {
            this.results = characterization.getResults();
        } else {
            this.results = null;
        }
    }

    private static class WordDescriptionHolder extends RecyclerView.ViewHolder {
        TextView definitionPHTextView;
        TextView definitionDataTextView;
        TextView partOfSpeechPHTextView;
        TextView partOfSpeechDataTextView;
        TextView synonymsPHTextView;
        TextView synonymsDataTextView;
        TextView antonymsPHTextView;
        TextView antonymsDataTextView;
        TextView examplesPHTextView;
        TextView examplesDataTextView;

        View definitionHR;
        View partOfSpeechHR;
        View synonymsHR;
        View antonymsHR;
        View examplesHR;

        public WordDescriptionHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view) {
            definitionPHTextView = view.findViewById(R.id.definitionPHTextView);
            definitionDataTextView = view.findViewById(R.id.definitionDataTextView);
            definitionHR = view.findViewById(R.id.definitionHR);

            partOfSpeechPHTextView = view.findViewById(R.id.partOfSpeechPHTextView);
            partOfSpeechDataTextView = view.findViewById(R.id.partOfSpeechDataTextView);
            partOfSpeechHR = view.findViewById(R.id.partOfSpeechHR);

            synonymsPHTextView = view.findViewById(R.id.synonymsPHTextView);
            synonymsDataTextView = view.findViewById(R.id.synonymsDataTextView);
            synonymsHR = view.findViewById(R.id.synonymsHR);

            antonymsPHTextView = view.findViewById(R.id.antonymsPHTextView);
            antonymsDataTextView = view.findViewById(R.id.antonymsDataTextView);
            antonymsHR = view.findViewById(R.id.antonymsHR);

            examplesPHTextView = view.findViewById(R.id.examplesPHTextView);
            examplesDataTextView = view.findViewById(R.id.examplesDataTextView);
            examplesHR = view.findViewById(R.id.examplesHR);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.description_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new WordDescriptionHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WordDescriptionHolder thisHolder =  ((WordDescriptionHolder) holder);
        Result result = results.get(position);
        thisHolder.definitionDataTextView.setText(result.getDefinition());
        thisHolder.partOfSpeechDataTextView.setText(result.getPartOfSpeech());

       if(result.getAntonyms() == null || result.getAntonyms().equals("")) {
           thisHolder.antonymsDataTextView.setVisibility(View.GONE);
           thisHolder.antonymsPHTextView.setVisibility(View.GONE);
           thisHolder.antonymsHR.setVisibility(View.GONE);
       } else {
           thisHolder.antonymsDataTextView.setText(result.getAntonyms());
       }

       if(result.getSynonyms() == null || result.getSynonyms().equals("")) {
           thisHolder.synonymsDataTextView.setVisibility(View.GONE);
           thisHolder.synonymsPHTextView.setVisibility(View.GONE);
           thisHolder.synonymsHR.setVisibility(View.GONE);
       } else {
           thisHolder.synonymsDataTextView.setText(result.getSynonyms());
       }

       if(result.getExamples() == null || result.getExamples().equals("")) {
           thisHolder.examplesDataTextView.setVisibility(View.GONE);
           thisHolder.examplesPHTextView.setVisibility(View.GONE);
           thisHolder.examplesHR.setVisibility(View.GONE);
       } else {
           thisHolder.examplesDataTextView.setText(result.getExamples());
       }

    }

    @Override
    public int getItemCount() {
        return  results != null ? results.size() : 0;
    }
}
