package com.utopiaxc.utopiatts.tts;

public interface Tts {
    boolean doSpeak(String text, int pitch, int rate);
    void stopSpeak();
    void initTts();
}
