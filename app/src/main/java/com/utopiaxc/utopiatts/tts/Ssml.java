package com.utopiaxc.utopiatts.tts;

import android.util.Log;

import androidx.annotation.NonNull;

public class Ssml {
    private static final String TAG = "Ssml";
    private final String actor;
    private final int pitch;
    private final int rate;
    private final String style;
    private final int styleDegree;
    private final String text;

    public Ssml(String text, String actor, int pitch, int rate, String style, int styleDegree) {
        this.text = text;
        this.actor = actor;
        this.pitch = (pitch/4)-25;
        this.rate = (rate/4)-25;
        this.style = style;
        this.styleDegree = styleDegree;
    }


    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<speak xmlns=\"http://www.w3.org/2001/10/synthesis\" xmlns:mstts=\"http://www.w3.org/2001/mstts\" xmlns:emo=\"http://www.w3.org/2009/10/emotionml\" version=\"1.0\" xml:lang=\"en-US\">");
        sb.append("<voice name=\"").append(actor).append("\">");
        if (!"".equals(style)) {
            sb.append("<mstts:express-as style=\"").append(style).append("\" styledegree=\"").append(styleDegree).append("\" >");
        }
        sb.append("<prosody rate=\"").append(rate).append("%\" pitch=\"").append(pitch).append("%\">");
        sb.append(text);
        sb.append("</prosody>");
        if (!"".equals(style)) {
            sb.append("</mstts:express-as>");
        }
        sb.append("</voice></speak>");
        Log.d(TAG, "toString = " + sb);
        return sb.toString();
    }
}
