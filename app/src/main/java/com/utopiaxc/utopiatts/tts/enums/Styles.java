package com.utopiaxc.utopiatts.tts.enums;

public enum Styles {
    NONE("NONE",""),
    CALM("CALM","calm"),
    FEARFUL("FEARFUL","fearful"),
    CHEERFUL("CHEERFUL","cheerful"),
    DISGRUNTLED("DISGRUNTLED","disgruntled"),
    SERIOUS("SERIOUS","serious"),
    ANGRY("ANGRY","angry"),
    GENTLE("GENTLE","gentle"),
    AFFECTIONATE("AFFECTIONATE","affectionate"),
    EMBARRASSED("EMBARRASSED","embarrassed"),
    DEPRESSED("DEPRESSED","depressed"),
    ENVIOUS("ENVIOUS","envious"),
    ASSISTANT("ASSISTANT","assistant"),
    CUSTOMER_SERVICE("CUSTOMER_SERVICE","customerservice"),
    NEWSCAST("NEWSCAST","newscast"),
    LYRICAL("LYRICAL","lyrical"),
    POETRY_READING("POETRY_READING","poetry-reading"),
    ADVERTISEMENT_UPBEAT("ADVERTISEMENT_UPBEAT","Advertisement_upbeat"),
    SPORTS_COMMENTARY("SPORTS_COMMENTARY","Sports_commentary"),
    SPORTS_COMMENTARY_EXCITED("SPORTS_COMMENTARY_EXCITED","Sports_commentary_excited"),
    NARRATION_RELAXED("NARRATION_RELAXED","narration-relaxed"),
    DOCUMENTARY_NARRATION("DOCUMENTARY_NARRATION","documentary-narration");

    private final String mName;
    private final String mId;

    Styles(String name, String id){
        mName=name;
        mId=id;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public static Styles getStyle(String name) {
        for (Styles style : Styles.values()) {
            if (style.getName().equals(name)) {
                return style;
            }
        }
        return NONE;
    }
}