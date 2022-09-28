package com.utopiaxc.utopiatts.tts;

public interface Tts {
    void doSpeak(String text, int pitch, int rate);
    void stopSpeak();
    void initTts();
}
