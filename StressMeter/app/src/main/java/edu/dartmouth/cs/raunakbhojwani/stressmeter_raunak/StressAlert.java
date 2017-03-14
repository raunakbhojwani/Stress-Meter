package edu.dartmouth.cs.raunakbhojwani.stressmeter_raunak;

import android.media.MediaPlayer;
import android.os.Vibrator;
import android.content.Context;

/**
 * Created by RaunakBhojwani on 1/26/17.
 * Facilitates the alarm since it needs to be enabled and disabled in different activities and fragments
 */

public class StressAlert {

    public static Vibrator mVibrator;
    public static MediaPlayer mAlertPlayer;
    public static final int mVolumeLevel = 90;

    public static void alertBegin(Context context, int rawSoundId) {
        mAlertPlayer = MediaPlayer.create(context, rawSoundId);
        mAlertPlayer.setVolume(mVolumeLevel, mVolumeLevel);
        mAlertPlayer.setLooping(true);

        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] echoPattern = {0, 150, 1500};
        mVibrator.vibrate(echoPattern, 0);

        mAlertPlayer.start();
    }

    public static void alertEnd() {

        if (mVibrator.hasVibrator()) {
            mVibrator.cancel();
        }

        if (mAlertPlayer.isPlaying()) {
            mAlertPlayer.stop();
        }
    }
}
