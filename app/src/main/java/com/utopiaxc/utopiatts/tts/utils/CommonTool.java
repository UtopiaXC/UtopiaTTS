package com.utopiaxc.utopiatts.tts.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import okio.ByteString;

public class CommonTool {

    static final Pattern NoVoicePattern = Pattern.compile("[\\s\\p{C}\\p{P}\\p{Z}\\p{S}]");
    static final SimpleDateFormat sdf = new SimpleDateFormat(
            "EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH);


    public static boolean isNoVoice(CharSequence charSequence) {
        return NoVoicePattern.matcher(charSequence).replaceAll("").isEmpty();
    }

    @SuppressWarnings("unused")
    public static void removeAllBlankSpace(StringBuilder sb) {
        int j = 0;
        for (int i = 0; i < sb.length(); i++) {
            if (!(Character.isWhitespace(sb.charAt(i)) || sb.charAt(i) == '　')) {
                sb.setCharAt(j++, sb.charAt(i));
            }
        }
        sb.delete(j, sb.length());
    }

    public static void Trim(StringBuilder sb) {
        if (sb == null || sb.length() == 0) return;
        int st = 0;
        while (Character.isWhitespace(sb.charAt(st)) || sb.charAt(st) == '　') {
            st++;
        }
        if (st > 0) {
            sb.delete(0, st);
        }

        int ed = sb.length();
        while (Character.isWhitespace(sb.charAt(ed - 1)) || sb.charAt(ed - 1) == '　') {
            ed--;
        }
        if (ed < sb.length()) {
            sb.delete(ed, sb.length());
        }

    }


    public static void replace(StringBuilder builder, String from, String to) {
        int index = builder.indexOf(from);
        while (index != -1) {
            builder.replace(index, index + from.length(), to);
            index += to.length(); // Move to the end of the replacement
            index = builder.indexOf(from, index);
        }
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();

        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

    public static String getTime() {
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getTime(long timestamp) {
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    public static String getMD5String(String str) {
        return ByteString.of(str.getBytes(StandardCharsets.UTF_8)).md5().hex();
    }
}
