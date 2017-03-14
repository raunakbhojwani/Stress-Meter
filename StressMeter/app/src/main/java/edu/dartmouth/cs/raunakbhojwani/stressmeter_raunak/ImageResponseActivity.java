package edu.dartmouth.cs.raunakbhojwani.stressmeter_raunak;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import java.io.File;
import java.io.FileOutputStream;


/**
 * Created by RaunakBhojwani on 1/25/17.
 * Activity that shows the cancel and submit buttons once the image is chosen
 */


public class ImageResponseActivity extends Activity {

    public static final String TAG = "DebugTag";

    Button mCancelButton, mSubmitButton;
    Integer mStressLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_response);

        StressAlert.alertEnd();

        Intent stressLevelIntent = getIntent();
        final String imagePosition = stressLevelIntent.getStringExtra("position");
        final String gridNumber = stressLevelIntent.getStringExtra("grid_number");

        ImageView chosenImageView = (ImageView) findViewById(R.id.choose_image_view);
        chosenImageView.setImageResource(PSM.getGridById(Integer
                .parseInt(gridNumber))[Integer.parseInt(imagePosition)]);

        switch (Integer.parseInt(imagePosition)) {
            case 0:
                mStressLevel = 6;
                break;
            case 1:
                mStressLevel = 8;
                break;
            case 2:
                mStressLevel = 14;
                break;
            case 3:
                mStressLevel = 16;
                break;
            case 4:
                mStressLevel = 5;
                break;
            case 5:
                mStressLevel = 7;
                break;
            case 6:
                mStressLevel = 13;
                break;
            case 7:
                mStressLevel = 15;
                break;
            case 8:
                mStressLevel = 2;
                break;
            case 9:
                mStressLevel = 4;
                break;
            case 10:
                mStressLevel = 10;
                break;
            case 11:
                mStressLevel = 12;
                break;
            case 12:
                mStressLevel = 1;
                break;
            case 13:
                mStressLevel = 3;
                break;
            case 14:
                mStressLevel = 9;
                break;
            case 15:
                mStressLevel = 11;
                break;
            default:
                break;
        }

        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mSubmitButton = (Button) findViewById(R.id.submit_button);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSubmitButton.setOnClickListener(
                new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String response = System.currentTimeMillis() / 1000 + "," + mStressLevel + "\n";
                Log.d(TAG, response);


                if (isExternalStorageWritable()) {
                    Log.d(TAG, "WRITABLE");
                }
                else {
                    Log.d(TAG, "NOT WRITABLE!!");
                }

                if (isExternalStorageReadable()) {
                    Log.d(TAG, "READABLE");
                }
                else {
                    Log.d(TAG, "NOT READABLE!!");
                }


                File responseFile = new File(Environment.getExternalStorageDirectory(),"StressMeterResponse.csv");
                Log.d(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());

                if (!responseFile.exists()) {
                    try {
                        responseFile.createNewFile();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (responseFile == null) {
                    Log.d(TAG, "responseFile is NULL!");
                }
                Log.d(TAG, "File Created");

                FileOutputStream fos;
                Log.d(TAG, "FOS created");

                try
                {
                    fos = new FileOutputStream(responseFile, true);
                    fos.write(response.getBytes());
                    fos.flush();
                    fos.close();
                    Log.d(TAG, "File saved!");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.d(TAG, "Exception caught for file");
                }

                Intent submitIntent = new Intent();
                submitIntent.putExtra("result", "true");
                setResult(Activity.RESULT_OK, submitIntent);
                Log.d(TAG, "Set Activity Result!");
                finish();
                Log.d(TAG, "Finished!");
            }
        });
    }
    /* Functions to check whether storage is writeable and readable
     *
     */

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}
