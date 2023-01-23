package com.utopiaxc.utopiatts.tts.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

public class Ssml {
    private static final String TAG = "Ssml";
    private final String mActor;
    private final int mPitch;
    private final int mRate;
    private final String mStyle;
    private final int mStyleDegree;
    private final StringBuilder mText;
    private final String mRole;

    public Ssml(String text, String actor, int pitch, int rate, String role, String style,
                int styleDegree) {
        mText = new StringBuilder(text);
        mActor = actor;
        mPitch = (pitch / 4) - 25;
        mRate = (rate / 4) - 25;
        mRole = role;
        mStyle = style;
        mStyleDegree = styleDegree;
        handleContent();
    }


    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<speak xmlns=\"http://www.w3.org/2001/10/synthesis\" " +
                "xmlns:mstts=\"http://www.w3.org/2001/mstts\" " +
                "xmlns:emo=\"http://www.w3.org/2009/10/emotionml\" " +
                "version=\"1.0\" xml:lang=\"en-US\">");
        sb.append("<voice name=\"").append(mActor).append("\">");
        if (!"".equals(mStyle) || !"".equals(mRole)) {
            sb.append("<mstts:express-as ");
            if (!"".equals(mRole)) {
                sb.append("role=\"").append(mRole).append("\" ");
            }
            if (!"".equals(mStyle)) {
                sb.append("style=\"").append(mStyle).append("\" styledegree=\"")
                        .append(mStyleDegree).append("\" ");
            }
            sb.append(">");
        }
        sb.append("<prosody rate=\"").append(mRate).append("%\" pitch=\"")
                .append(mPitch).append("%\">");
        sb.append(mText);
        sb.append("</prosody>");
        if (!"".equals(mStyle)) {
            sb.append("</mstts:express-as>");
        }
        sb.append("</voice></speak>");
        Log.d(TAG, "toString = " + sb);
        return sb.toString();
    }

    public String toStringForWs() {
        long timestamp = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder()
                .append("Path:ssml\r\n")
                .append("X-RequestId:").append(CommonTool.getMD5String(mText + "" + timestamp))
                .append("\r\n")
                .append("X-Timestamp:")
                .append(CommonTool.getTime(timestamp)).append("Z\r\n")
                .append("Content-Type:application/ssml+xml\r\n\r\n");
        sb.append("<speak xmlns=\"http://www.w3.org/2001/10/synthesis\" " +
                "xmlns:mstts=\"http://www.w3.org/2001/mstts\" " +
                "xmlns:emo=\"http://www.w3.org/2009/10/emotionml\" " +
                "version=\"1.0\" xml:lang=\"zh-CN\">");
        sb.append("<voice name=\"").append(mActor).append("\">");
        if (!"".equals(mStyle) || !"".equals(mRole)) {
            sb.append("<mstts:express-as ");
            if (!"".equals(mRole)) {
                sb.append("role=\"").append(mRole).append("\" ");
            }
            if (!"".equals(mStyle)) {
                sb.append("style=\"").append(mStyle).append("\" styledegree=\"")
                        .append(mStyleDegree).append("\" ");
            }
            sb.append(">");
        }
        sb.append("<prosody rate=\"").append(mRate).append("%\" pitch=\"")
                .append(mPitch).append("%\">");
        sb.append(mText);
        sb.append("</prosody>");
        if (!"".equals(mStyle)) {
            sb.append("</mstts:express-as>");
        }
        sb.append("</voice></speak>");
        Log.d(TAG, "toString = " + sb);
        return sb.toString();
    }

    private void handleContent() {
        CommonTool.replace(mText, "\n", " ");
        CommonTool.Trim(mText);
        CommonTool.replace(mText, "&", "&amp;");
        CommonTool.replace(mText, "\"", "&quot;");
        CommonTool.replace(mText, "'", "&apos;");
        CommonTool.replace(mText, ">", "&lt;");
        CommonTool.replace(mText, "<", "&gt;");
    }
}
