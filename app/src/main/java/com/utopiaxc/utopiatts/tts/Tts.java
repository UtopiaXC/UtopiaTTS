package com.utopiaxc.utopiatts.tts;

import android.speech.tts.SynthesisCallback;

public interface Tts {
    boolean doSpeak(String text, int pitch, int rate, SynthesisCallback synthesisCallback);
    void stopSpeak();
    void initTts();
}
