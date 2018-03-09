package ak.com.projectwords.POJOs.WordFullData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by artyomkuznetsov on 2/26/18.
 */

public class Result {
    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("partOfSpeech")
    @Expose
    private String partOfSpeech;
    @SerializedName("synonyms")
    @Expose
    private List<String> synonyms = null;
    @SerializedName("typeOf")
    @Expose
    private List<String> typeOf = null;
    @SerializedName("examples")
    @Expose
    private List<String> examples = null;
    @SerializedName("instanceOf")
    @Expose
    private List<String> instanceOf = null;
    @SerializedName("similarTo")
    @Expose
    private List<String> similarTo = null;
    @SerializedName("hasTypes")
    @Expose
    private List<String> hasTypes = null;
    @SerializedName("antonyms")
    @Expose
    private List<String> antonyms = null;
    @SerializedName("derivation")
    @Expose
    private List<String> derivation = null;
    @SerializedName("also")
    @Expose
    private List<String> also = null;
    @SerializedName("attribute")
    @Expose
    private List<String> attribute = null;
    @SerializedName("inCategory")
    @Expose
    private List<String> inCategory = null;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getSynonyms() {
        String allSynonyms = "";
        if(synonyms != null) {
            int size = synonyms.size();
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    allSynonyms += synonyms.get(i);
                } else {
                    allSynonyms += synonyms.get(i) + "\n";
                }
            }
            return allSynonyms;
        } else {
            return "";
        }
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(List<String> typeOf) {
        this.typeOf = typeOf;
    }

    public String getExamples() {
        String allExamples = "";
        if(examples != null) {
            int size = examples.size();
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    allExamples += examples.get(i);
                } else {
                    allExamples += examples.get(i) + "\n";
                }
            }
            return allExamples;
        } else {
            return "";
        }
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public List<String> getInstanceOf() {
        return instanceOf;
    }

    public void setInstanceOf(List<String> instanceOf) {
        this.instanceOf = instanceOf;
    }

    public List<String> getSimilarTo() {
        return similarTo;
    }

    public void setSimilarTo(List<String> similarTo) {
        this.similarTo = similarTo;
    }

    public List<String> getHasTypes() {
        return hasTypes;
    }

    public void setHasTypes(List<String> hasTypes) {
        this.hasTypes = hasTypes;
    }

    public String getAntonyms() {
        String allAntonyms = "";
        if(antonyms != null) {
            int size = antonyms.size();
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    allAntonyms += antonyms.get(i);
                } else {
                    allAntonyms += antonyms.get(i) + "\n";
                }
            }
            return allAntonyms;
        } else {
            return "";
        }
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }

    public List<String> getDerivation() {
        return derivation;
    }

    public void setDerivation(List<String> derivation) {
        this.derivation = derivation;
    }

    public List<String> getAlso() {
        return also;
    }

    public void setAlso(List<String> also) {
        this.also = also;
    }

    public List<String> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<String> attribute) {
        this.attribute = attribute;
    }

    public List<String> getInCategory() {
        return inCategory;
    }

    public void setInCategory(List<String> inCategory) {
        this.inCategory = inCategory;
    }
}
