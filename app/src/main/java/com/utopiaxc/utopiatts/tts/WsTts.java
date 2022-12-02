package com.utopiaxc.utopiatts.tts;

import static com.utopiaxc.utopiatts.tts.utils.CommonTool.getTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.SystemClock;
import android.speech.tts.SynthesisCallback;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.utopiaxc.utopiatts.APP;
import com.utopiaxc.utopiatts.enums.SettingsEnum;
import com.utopiaxc.utopiatts.tts.enums.Actors;
import com.utopiaxc.utopiatts.tts.enums.OutputFormat;
import com.utopiaxc.utopiatts.tts.enums.Regions;
import com.utopiaxc.utopiatts.tts.enums.Roles;
import com.utopiaxc.utopiatts.tts.enums.Styles;
import com.utopiaxc.utopiatts.tts.utils.ByteArrayMediaDataSource;
import com.utopiaxc.utopiatts.tts.utils.CommonTool;
import com.utopiaxc.utopiatts.tts.utils.Constants;
import com.utopiaxc.utopiatts.tts.utils.Ssml;
import com.utopiaxc.utopiatts.tts.utils.WebSocketState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.Buffer;
import okio.ByteString;

public class WsTts implements Tts {
    private static final String TAG = "WsTts";
    private static volatile Tts mInstance;
    SharedPreferences mSharedPreferences;
    private volatile boolean mIsSynthesizing = false;
    private OutputFormat mOutputFormat = null;
    private Regions mRegions = null;
    private WebSocket mWebSocket = null;
    private volatile WebSocketState mWebSocketState = WebSocketState.OFFLINE;
    private OkHttpClient mClient;
    private final Buffer mData = new Buffer();
    private MediaCodec mMediaCodec;
    private String mOldMime;
    private Ssml mSsml;
    private SynthesisCallback mCallback;
    private final WebSocketListener mWebSocketListener = new WebSocketListener() {
        @Override
        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosed(webSocket, code, reason);
            Log.e(TAG, "onClosed:" + reason);
        }

        @Override
        public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosing(webSocket, code, reason);
            Log.e(TAG, "onClosing:" + reason);
            mWebSocket = null;
            mWebSocketState = WebSocketState.OFFLINE;
            if (mIsSynthesizing) {
                mWebSocket = getOrCreateWs();
            }
        }

        @Override
        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
            mWebSocket = null;
            mWebSocketState = WebSocketState.OFFLINE;
            Log.e(TAG, "onFailure, throwable = \n",t);
            getOrCreateWs().send(mSsml.toStringForWs());
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
            super.onMessage(webSocket, text);
            final String endTag = "turn.end";
            final String startTag = "turn.start";
            int endIndex = text.lastIndexOf(endTag);
            int startIndex = text.lastIndexOf(startTag);
            if (startIndex != -1) {
                mIsSynthesizing = true;
                mData.clear();
            } else if (endIndex != -1) {
                if (mCallback != null && !mCallback.hasFinished() && mIsSynthesizing) {
                    if (mOutputFormat.needDecode()) {
                        doDecode(mData.readByteString());
                    } else {
                        doUnDecode(mData.readByteString());
                    }
                }
            }
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
            super.onMessage(webSocket, bytes);
            final String audioTag = "Path:audio\r\n";
            final String startTag = "Content-Type:";
            final String endTag = "\r\nX-StreamId";
            int audioIndex = bytes.lastIndexOf(audioTag.getBytes(StandardCharsets.UTF_8)) + audioTag.length();
            int startIndex = bytes.lastIndexOf(startTag.getBytes(StandardCharsets.UTF_8)) + startTag.length();
            int endIndex = bytes.lastIndexOf(endTag.getBytes(StandardCharsets.UTF_8));
            if (audioIndex != -1) {
                try {
                    String temp = bytes.substring(startIndex, endIndex).utf8();
                    String mCurrentMime;
                    if (temp.startsWith("audio")) {
                        mCurrentMime = temp;
                    } else {
                        return;
                    }
                    if (!mOutputFormat.needDecode()) {
                        if ("audio/x-wav".equals(mCurrentMime) && bytes.lastIndexOf("RIFF".getBytes(StandardCharsets.UTF_8)) != -1) {
                            audioIndex += 44;
                        }
                    }
                    mData.write(bytes.substring(audioIndex));
                } catch (Exception e) {
                    mIsSynthesizing = false;
                }
            }
        }

        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            super.onOpen(webSocket, response);
            Log.e(TAG, "onOpen" + response.headers());
        }
    };

    public WsTts(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        initTts();
    }

    public static Tts getInstance(Context context) {
        if (mInstance == null) {
            synchronized (Tts.class) {
                if (mInstance == null) {
                    mInstance = new WsTts(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    @Override
    public boolean doSpeak(String text, int pitch, int rate,SynthesisCallback synthesisCallback) {
        mCallback=synthesisCallback;
        mIsSynthesizing = true;
        if (CommonTool.isNoVoice(text)) {
            mIsSynthesizing = false;
            return true;
        }

        long startTime = SystemClock.elapsedRealtime();
        synchronized (WsTts.this) {
            mIsSynthesizing = true;
            sendText(text, pitch, rate);
            while (mIsSynthesizing) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long time = SystemClock.elapsedRealtime() - startTime;
                if (time > 50000) {
                    return false;
                }
            }
        }
        mIsSynthesizing = false;
        return true;
    }

    @Override
    public void stopSpeak() {
        if (mWebSocket != null) {
            Objects.requireNonNull(mWebSocket).close(1000, "closed by call onStop");
            mWebSocket = null;
        }
        mIsSynthesizing = false;
        mData.clear();
    }

    @Override
    public void initTts() {
        Log.i(TAG, "initTts");
        mRegions = Regions.getRegion(
                mSharedPreferences.getString(
                        SettingsEnum.AZURE_REGION.getKey(),
                        (String) SettingsEnum.AZURE_REGION.getDefaultValue()));

        mOutputFormat = OutputFormat.getOutputFormat(
                mSharedPreferences.getString(
                        SettingsEnum.OUTPUT_FORMAT.getKey(),
                        (String) SettingsEnum.OUTPUT_FORMAT.getDefaultValue()));
        mClient = APP.getOkHttpClient();
        mWebSocket = getOrCreateWs();
        sendConfig(mWebSocket);
    }

    public synchronized void sendText(String text, int pitch, int rate) {
        Actors actor = Actors.getActor(
                mSharedPreferences.getString(SettingsEnum.ACTOR.getKey(),
                        (String) SettingsEnum.ACTOR.getDefaultValue()));
        Roles role = Roles.getRole(
                mSharedPreferences.getString(SettingsEnum.ROLE.getKey(),
                        (String) SettingsEnum.ROLE.getDefaultValue()));
        Styles style = Styles.getStyle(
                mSharedPreferences.getString(SettingsEnum.STYLE.getKey(),
                        (String) SettingsEnum.STYLE.getDefaultValue()));
        int styleDegree = mSharedPreferences.getInt(SettingsEnum.STYLE_DEGREE.getKey(),
                (Integer) SettingsEnum.STYLE_DEGREE.getDefaultValue());

        mSsml = new Ssml(text, actor.getId(), pitch,
                rate, role.getId(), style.getId(), styleDegree);

        while (mIsSynthesizing){
            Log.w(TAG,"try sendText");
            try {
                if (getOrCreateWs().send(mSsml.toStringForWs())){
                    break;
                }
            }catch (Exception e){
                try {
                    this.wait(500);
                } catch (Exception ignored) {
                }
                Log.w(TAG,"Retry sendText");
            }
        }
//        try {
//            while(!(getOrCreateWs().send(ssml.toStringForWs()))&&mIsSynthesizing){
//                Log.w(TAG,"Retry sendText");
//            }
//            boolean success = getOrCreateWs().send(ssml.toStringForWs());
//            if (!success && mIsSynthesizing) {
//                getOrCreateWs().send(ssml.toStringForWs());
//            }
//        } catch (Exception e) {
//            getOrCreateWs();
//            while (mWebSocket == null) {
//                try {
//                    this.wait(500);
//                } catch (Exception ignored) {
//                }
//            }
//            getOrCreateWs().send(ssml.toStringForWs());
//        }
    }

    private synchronized void sendConfig(@NonNull WebSocket ws) {
        String msg = "X-Timestamp:+" + getTime() + "\r\n" +
                "Content-Type:application/json; charset=utf-8\r\n" +
                "Path:speech.config\r\n\r\n"
                + "{\"context\":" +
                "{\"synthesis\":" +
                "{\"audio\":" +
                "{\"metadataoptions\":" +
                "{\"sentenceBoundaryEnabled\":\"true\",\"wordBoundaryEnabled\":\"true\"" +
                "},\"outputFormat\":\"" + mOutputFormat.getValue() + "\"}}}}";
        ws.send(msg);
    }

    public synchronized WebSocket getOrCreateWs() {
        if (mWebSocket == null) {
            if (mWebSocketState == WebSocketState.CONNECTED) {
                mClient.dispatcher().cancelAll();
            }
            String url;
            String origin;
            url = "wss://" + mRegions.getId() + ".api.speech.microsoft.com/cognitiveservices/websocket/v1?TrafficType=AzureDemo&Authorization=bearer undefined&X-ConnectionId=" + CommonTool.getMD5String(new Date().toString());
            origin = "https://azure.microsoft.com";
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", Constants.EDGE_UA)
                    .addHeader("Origin", origin)
                    .build();
            mWebSocketState = WebSocketState.CONNECTING;
            mWebSocket = mClient.newWebSocket(request, mWebSocketListener);

            mWebSocketState = WebSocketState.CONNECTED;
            sendConfig(Objects.requireNonNull(mWebSocket));
        }
        return mWebSocket;
    }

    private synchronized void doDecode(@NonNull ByteString data) {
        mIsSynthesizing = true;
        try {
            MediaExtractor mediaExtractor = new MediaExtractor();
            mediaExtractor.setDataSource(new ByteArrayMediaDataSource(data.toByteArray()));
            int audioTrackIndex = -1;
            String mime = null;
            MediaFormat trackFormat = null;
            for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                trackFormat = mediaExtractor.getTrackFormat(i);
                mime = trackFormat.getString(MediaFormat.KEY_MIME);
                if (!TextUtils.isEmpty(mime) && mime.startsWith("audio")) {
                    audioTrackIndex = i;
                    break;
                }
            }
            if (audioTrackIndex == -1) {
                Log.e(TAG, "initAudioDecoder: 没有找到音频流");
                mIsSynthesizing = false;
                return;
            }
            if ("audio/opus".equals(mime)) {
                Buffer buf = new Buffer();
                buf.write("OpusHead".getBytes(StandardCharsets.UTF_8));
                buf.writeByte(1);
                buf.writeByte(1);
                buf.writeShortLe(0);
                buf.writeIntLe(mOutputFormat.getSoundFrequency());
                buf.writeShortLe(0);
                buf.writeByte(0);
                byte[] csd1bytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
                byte[] csd2bytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
                ByteString hd = buf.readByteString();
                ByteBuffer csd0 = ByteBuffer.wrap(hd.toByteArray());
                trackFormat.setByteBuffer("csd-0", csd0);
                ByteBuffer csd1 = ByteBuffer.wrap(csd1bytes);
                trackFormat.setByteBuffer("csd-1", csd1);
                ByteBuffer csd2 = ByteBuffer.wrap(csd2bytes);
                trackFormat.setByteBuffer("csd-2", csd2);
            }
            mediaExtractor.selectTrack(audioTrackIndex);
            MediaCodec mediaCodec = getMediaCodec(mime, trackFormat);
            mediaCodec.start();
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            ByteBuffer inputBuffer;
            long TIME_OUT_US = 10000;
            while (mIsSynthesizing) {
                int inputIndex = mediaCodec.dequeueInputBuffer(TIME_OUT_US);
                if (inputIndex < 0) {
                    break;
                }
                bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
                inputBuffer = mediaCodec.getInputBuffer(inputIndex);
                if (inputBuffer != null) {
                    inputBuffer.clear();
                } else {
                    continue;
                }
                int sampleSize = mediaExtractor.readSampleData(inputBuffer, 0);

                if (sampleSize > 0) {
                    bufferInfo.size = sampleSize;
                    mediaCodec.queueInputBuffer(inputIndex, 0, sampleSize, 0, 0);
                    mediaExtractor.advance();
                } else {
                    break;
                }
                int outputIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, TIME_OUT_US);
                ByteBuffer outputBuffer;
                byte[] pcmData;
                while (outputIndex >= 0) {
                    outputBuffer = mediaCodec.getOutputBuffer(outputIndex);
                    pcmData = new byte[bufferInfo.size];
                    if (outputBuffer != null) {
                        outputBuffer.get(pcmData);
                        outputBuffer.clear();
                    }
                    mCallback.audioAvailable(pcmData, 0, bufferInfo.size);
                    mediaCodec.releaseOutputBuffer(outputIndex, false);
                    outputIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, TIME_OUT_US);
                }
            }
            mediaCodec.reset();
            mIsSynthesizing = false;
        } catch (Exception e) {
            Log.e(TAG, "doDecode", e);
            mIsSynthesizing = false;
        }
    }


    private synchronized void doUnDecode(@NonNull ByteString data) {
        mIsSynthesizing = true;
        int length = data.toByteArray().length;
        final int maxBufferSize = mCallback.getMaxBufferSize();
        int offset = 0;
        while (offset < length && mIsSynthesizing) {
            int bytesToWrite = Math.min(maxBufferSize, length - offset);
            mCallback.audioAvailable(data.toByteArray(), offset, bytesToWrite);
            offset += bytesToWrite;
        }
        mCallback.done();
        mIsSynthesizing = false;
    }

    private MediaCodec getMediaCodec(String mime, MediaFormat mediaFormat) {
        if (mMediaCodec == null || !mime.equals(mOldMime)) {
            if (null != mMediaCodec) {
                mMediaCodec.release();
            }
            try {
                mMediaCodec = MediaCodec.createDecoderByType(mime);
                mOldMime = mime;
            } catch (IOException ioException) {
                ioException.printStackTrace();
                throw new RuntimeException(ioException);
            }
        }
        mMediaCodec.reset();
        mMediaCodec.configure(mediaFormat, null, null, 0);
        return mMediaCodec;
    }
}
