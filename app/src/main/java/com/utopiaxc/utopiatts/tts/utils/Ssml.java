package com.utopiaxc.utopiatts.tts.utils;

import android.util.Log;

import androidx.annotation.NonNull;

public class Ssml {
    private static final String TAG = "Ssml";
    private final String mActor;
    private final int mPitch;
    private final int mRate;
    private final String mStyle;
    private final int mStyleDegree;
    private final String mText;
    private final String mRole;

    public Ssml(String text, String actor, int pitch, int rate,String role, String style, int styleDegree) {
        this.mText = text;
        this.mActor = actor;
        this.mPitch = (pitch/4)-25;
        this.mRate = (rate/4)-25;
        this.mRole =role;
        this.mStyle = style;
        this.mStyleDegree = styleDegree;
    }


    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<speak xmlns=\"http://www.w3.org/2001/10/synthesis\" xmlns:mstts=\"http://www.w3.org/2001/mstts\" xmlns:emo=\"http://www.w3.org/2009/10/emotionml\" version=\"1.0\" xml:lang=\"en-US\">");
        sb.append("<voice name=\"").append(mActor).append("\">");
        if (!"".equals(mStyle)||!"".equals(mRole)) {
            sb.append("<mstts:express-as ");
            if (!"".equals(mRole)) {
                sb.append("role=\"").append(mRole).append("\" ");
            }
            if (!"".equals(mStyle)) {
                sb.append("style=\"").append(mStyle).append("\" styledegree=\"").append(mStyleDegree).append("\" ");
            }
            sb.append("></mstts:express-as>");
        }
        sb.append("<prosody rate=\"").append(mRate).append("%\" pitch=\"").append(mPitch).append("%\">");
        sb.append(mText);
        sb.append("</prosody>");
        if (!"".equals(mStyle)) {
            sb.append("</mstts:express-as>");
        }
        sb.append("</voice></speak>");
        Log.d(TAG, "toString = " + sb);
        return sb.toString();
    }
}
