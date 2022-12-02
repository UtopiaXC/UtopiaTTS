package com.utopiaxc.utopiatts.tts.enums;

import android.media.AudioFormat;

import androidx.annotation.NonNull;

import com.microsoft.cognitiveservices.speech.SpeechSynthesisOutputFormat;

public enum OutputFormat {
    AUDIO_16K_HZ_128K_BIT_RATE_MONO_MP3("AUDIO_16K_HZ_128K_BIT_RATE_MONO_MP3",
            "audio-16khz-128kbitrate-mono-mp3",
            true,
            16000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio16Khz128KBitRateMonoMp3),
    AUDIO_16K_HZ_16_BIT_32KBPS_MONO_OPUS("AUDIO_16K_HZ_128K_BIT_RATE_MONO_MP3",
            "audio-16khz-128kbitrate-mono-mp3",
            true,
            16000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio16Khz16Bit32KbpsMonoOpus),
    AUDIO_16K_HZ_16KBPS_MONO_SIREN("AUDIO_16K_HZ_16KBPS_MONO_SIREN",
            "audio-16khz-16kbps-mono-siren",
            false,
            16000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio16Khz16KbpsMonoSiren),
    AUDIO_16K_HZ_32K_BIT_RATE_MONO_MP3("AUDIO_16K_HZ_32K_BIT_RATE_MONO_MP3",
            "audio-16khz-32kbitrate-mono-mp3",
            true,
            16000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio16Khz32KBitRateMonoMp3),
    AUDIO_16K_HZ_64K_BIT_RATE_MONO_MP3("AUDIO_16K_HZ_64K_BIT_RATE_MONO_MP3",
            "audio-16khz-64kbitrate-mono-mp3",
            true,
            16000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio16Khz64KBitRateMonoMp3),
    AUDIO_24K_HZ_160K_BIT_RATE_MONO_MP3("AUDIO_24K_HZ_160K_BIT_RATE_MONO_MP3",
            "audio-24khz-160kbitrate-mono-mp3",
            true,
            24000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio24Khz160KBitRateMonoMp3),
    AUDIO_24K_HZ_16_BIT_24KBPS_MONO_OPUS("AUDIO_24K_HZ_16_BIT_24KBPS_MONO_OPUS",
            "audio-24khz-16bit-24kbps-mono-opus",
            true,
            24000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio24Khz16Bit24KbpsMonoOpus),
    AUDIO_24K_HZ_16_BIT_48KBPS_MONO_OPUS("AUDIO_24K_HZ_16_BIT_48KBPS_MONO_OPUS",
            "audio-24khz-16bit-48kbps-mono-opus",
            true,
            24000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio24Khz16Bit48KbpsMonoOpus),
    AUDIO_24K_HZ_48K_BIT_RATE_MONO_MP3("AUDIO_24K_HZ_48K_BIT_RATE_MONO_MP3",
            "audio-24khz-48kbitrate-mono-mp3",
            true,
            24000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio24Khz48KBitRateMonoMp3),
    AUDIO_24K_HZ_96K_BIT_RATE_MONO_MP3("AUDIO_24K_HZ_96K_BIT_RATE_MONO_MP3",
            "audio-24khz-96kbitrate-mono-mp3",
            true,
            24000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio24Khz96KBitRateMonoMp3),
    AUDIO_48K_HZ_192K_BIT_RATE_MONO_MP3("AUDIO_48K_HZ_192K_BIT_RATE_MONO_MP3",
            "audio-48khz-192kbitrate-mono-mp3",
            true,
            48000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio48Khz192KBitRateMonoMp3),
    AUDIO_48K_HZ_96K_BIT_RATE_MONO_MP3("AUDIO_48K_HZ_96K_BIT_RATE_MONO_MP3",
            "audio-48khz-96kbitrate-mono-mp3",
            true,
            48000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Audio48Khz192KBitRateMonoMp3),
    OGG_16K_HZ_16_BIT_MONO_OPUS("OGG_16K_HZ_16_BIT_MONO_OPUS",
            "ogg-16khz-16bit-mono-opus",
            true,
            16000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Ogg16Khz16BitMonoOpus),
    OGG_24K_HZ_16_BIT_MONO_OPUS("OGG_24K_HZ_16_BIT_MONO_OPUS",
            "ogg-24khz-16bit-mono-opus",
            true,
            24000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Ogg24Khz16BitMonoOpus),
    OGG_48K_HZ_16_BIT_MONO_OPUS("OGG_48K_HZ_16_BIT_MONO_OPUS",
            "ogg-48khz-16bit-mono-opus",
            true,
            48000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Ogg48Khz16BitMonoOpus),
    RAW_16K_HZ_16_BIT_MONO_PCM("RAW_16K_HZ_16_BIT_MONO_PCM",
            "raw-16khz-16bit-mono-pcm",
            false,
            16000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Raw16Khz16BitMonoPcm),
    RAW_16K_HZ_16_BIT_MONO_TRUE_SILK("RAW_16K_HZ_16_BIT_MONO_TRUE_SILK",
            "raw-16khz-16bit-mono-true-silk",
            false,
            16000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Raw16Khz16BitMonoTrueSilk),
    RAW_22050_HZ_16_BIT_MONO_PCM("RAW_22050_HZ_16_BIT_MONO_PCM",
            "raw-22050hz-16bit-mono-pcm",
            false,
            22050, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Raw22050Hz16BitMonoPcm),
    RAW_24K_HZ_16_BIT_MONO_PCM("RAW_24K_HZ_16_BIT_MONO_PCM",
            "raw-24khz-16bit-mono-pcm",
            false,
            24000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Raw24Khz16BitMonoPcm),
    RAW_24K_HZ_16_BIT_MONO_TRUE_SILK("RAW_24K_HZ_16_BIT_MONO_TRUE_SILK",
            "raw-24khz-16bit-mono-true-silk",
            false,
            24000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Raw24Khz16BitMonoTrueSilk),
    RAW_44100_HZ_16_BIT_MONO_PCM("RAW_44100_HZ_16_BIT_MONO_PCM",
            "raw-44100hz-16bit-mono-pcm",
            false,
            44100, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Raw44100Hz16BitMonoPcm),
    RAW_48K_HZ_16_BIT_MONO_PCM("RAW_48K_HZ_16_BIT_MONO_PCM",
            "raw-48khz-16bit-mono-pcm",
            false,
            48000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Raw48Khz16BitMonoPcm),
    RAW_8K_HZ_16_BIT_MONO_PCM("RAW_8K_HZ_16_BIT_MONO_PCM",
            "raw-8khz-16bit-mono-pcm",
            false,
            8000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Raw8Khz16BitMonoPcm),
    RAW_8K_HZ_8_BIT_MONO_ALAW("RAW_8K_HZ_8_BIT_MONO_ALAW",
            "raw-8khz-8bit-mono-alaw",
            false,
            8000, AudioFormat.ENCODING_PCM_8BIT,
            SpeechSynthesisOutputFormat.Raw8Khz8BitMonoALaw),
    RAW_8K_HZ_8_BIT_MONO_MULAW("RAW_8K_HZ_8_BIT_MONO_MULAW",
            "raw-8khz-8bit-mono-mulaw",
            false,
            8000, AudioFormat.ENCODING_PCM_8BIT,
            SpeechSynthesisOutputFormat.Raw8Khz8BitMonoMULaw),
    RIFF_16K_HZ_16_BIT_MONO_PCM("RIFF_16K_HZ_16_BIT_MONO_PCM",
            "riff-16khz-16bit-mono-pcm",
            false,
            16000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Riff16Khz16BitMonoPcm),
    RIFF_16K_HZ_16KBPS_MONO_SIREN("RIFF_16K_HZ_16KBPS_MONO_SIREN",
            "riff-16khz-16kbps-mono-siren",
            false,
            16000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Riff16Khz16KbpsMonoSiren),
    RIFF_22050_HZ_16_BIT_MONO_PCM("RIFF_22050_HZ_16_BIT_MONO_PCM",
            "riff-22050hz-16bit-mono-pcm",
            false,
            22050, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Riff22050Hz16BitMonoPcm),
    RIFF_24K_HZ_16_BIT_MONO_PCM("RIFF_24K_HZ_16_BIT_MONO_PCM",
            "riff-24khz-16bit-mono-pcm",
            false,
            24000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Riff24Khz16BitMonoPcm),
    RIFF_44100_HZ_16_BIT_MONO_PCM("RIFF_44100_HZ_16_BIT_MONO_PCM",
            "riff-44100hz-16bit-mono-pcm",
            false,
            44100, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Riff44100Hz16BitMonoPcm),
    RIFF_48K_HZ_16_BIT_MONO_PCM("RIFF_48K_HZ_16_BIT_MONO_PCM",
            "riff-48khz-16bit-mono-pcm",
            false,
            48000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Riff48Khz16BitMonoPcm),
    RIFF_8K_HZ_16_BIT_MONO_PCM("RIFF_8K_HZ_16_BIT_MONO_PCM",
            "riff-8khz-16bit-mono-pcm",
            false,
            8000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Riff8Khz16BitMonoPcm),
    RIFF_8K_HZ_8_BIT_MONO_ALAW("RIFF_8K_HZ_8_BIT_MONO_ALAW",
            "riff-8khz-8bit-mono-alaw",
            false,
            8000, AudioFormat.ENCODING_PCM_8BIT,
            SpeechSynthesisOutputFormat.Riff8Khz8BitMonoALaw),
    RIFF_8K_HZ_8_BIT_MONO_MULAW("RIFF_8K_HZ_8_BIT_MONO_MULAW",
            "riff-8khz-8bit-mono-mulaw",
            false,
            8000, AudioFormat.ENCODING_PCM_8BIT,
            SpeechSynthesisOutputFormat.Riff8Khz8BitMonoMULaw),
    WEBM_16K_HZ_16_BIT_MONO_OPUS("WEBM_16K_HZ_16_BIT_MONO_OPUS",
            "webm-16khz-16bit-mono-opus",
            true,
            16000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Webm16Khz16BitMonoOpus),
    WEBM_24K_HZ_16_BIT_24KBPS_MONO_OPUS("WEBM_24K_HZ_16_BIT_24KBPS_MONO_OPUS",
            "webm-24khz-16bit-24kbps-mono-opus",
            true,
            24000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Webm24Khz16Bit24KbpsMonoOpus),
    WEBM_24K_HZ_16_BIT_MONO_OPUS("WEBM_24K_HZ_16_BIT_MONO_OPUS",
            "webm-24khz-16bit-mono-opus",
            true,
            24000, AudioFormat.ENCODING_PCM_16BIT,
            SpeechSynthesisOutputFormat.Webm24Khz16BitMonoOpus),
    ;

    private final String mName;
    private final int mSoundFrequency;
    private final int mAudioFormat;
    private final SpeechSynthesisOutputFormat mSpeechSynthesisOutputFormat;
    private final String mValue;
    private final boolean mNeedDecode;

    OutputFormat(String name, String value, boolean needDecode, int soundFrequency, int audioFormat,
                 SpeechSynthesisOutputFormat speechSynthesisOutputFormat) {
        mName = name;
        mValue = value;
        mNeedDecode = needDecode;
        mSoundFrequency = soundFrequency;
        mAudioFormat = audioFormat;
        mSpeechSynthesisOutputFormat = speechSynthesisOutputFormat;
    }

    @NonNull
    @Override
    public String toString() {
        return mName;
    }

    public String getName() {
        return mName;
    }

    public String getValue() {
        return mValue;
    }

    public int getSoundFrequency() {
        return mSoundFrequency;
    }

    public int getAudioFormat() {
        return mAudioFormat;
    }

    public SpeechSynthesisOutputFormat getSpeechSynthesisOutputFormat() {
        return mSpeechSynthesisOutputFormat;
    }

    public boolean needDecode(){
        return mNeedDecode;
    }

    public static OutputFormat getOutputFormat(String name) {
        for (OutputFormat outputFormat : OutputFormat.values()) {
            if (outputFormat.getName().equals(name)) {
                return outputFormat;
            }
        }
        return RAW_48K_HZ_16_BIT_MONO_PCM;
    }
}
