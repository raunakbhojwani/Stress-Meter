package edu.dartmouth.cs.raunakbhojwani.stressmeter_raunak;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.view.LineChartView;
import android.graphics.Color;
import java.io.IOException;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.widget.TableRow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import java.io.BufferedReader;
import java.io.File;
import android.widget.TextView;
import java.util.List;

/**
 * Created by RaunakBhojwani on 1/25/17.
 * Visualization fragment is where the results are displayed, that is the linechart and the table of results
 */


public class VisualizationFragment extends Fragment {

    public static final String TAG = "DebugTag";
    ArrayList<UserStress> mUserStressLevels;
    private LineChartView mChartView;
    private LineChartData mData;

    public VisualizationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View resultsView = inflater.inflate(R.layout.fragment_visualization, container, false);
        StressAlert.alertEnd();

        mUserStressLevels = interpretResults();

        plotLineChart(resultsView);

        fillResultsTable(resultsView);

        return resultsView;

    }

    public ArrayList<UserStress> interpretResults()
    {
        ArrayList<UserStress> listOfStressResults = new ArrayList<>();

        File dir = Environment.getExternalStorageDirectory();
        String directory = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d(TAG, directory);

        File resultsFile = new File(dir, "StressMeterResponse.csv");
        BufferedReader reader;
        FileInputStream fis;

        if(resultsFile.exists())
        {
            try {
                fis = new FileInputStream(resultsFile);
                reader = new BufferedReader(new InputStreamReader(fis));

                String line = reader.readLine();
                while (line != null)
                {
                    String[] stressData = line.split(",");
                    listOfStressResults.add(new UserStress(Long.parseLong(stressData[0]), Integer.parseInt(stressData[1])));
                    line = reader.readLine();
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d(TAG, "FileNotFound Exception!");
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "IOException!");
            }
        }

        return listOfStressResults;
    }

    public void plotLineChart(View v) {

        mChartView = (LineChartView) v.findViewById(R.id.line_chart_view);
        mData = new LineChartData();

        Axis xAxis = new Axis();
        Axis yAxis = new Axis().setHasLines(true);
        xAxis.setName("Instances");
        yAxis.setName("Stress Level");

        mData.setAxisXBottom(xAxis);
        mData.setAxisYLeft(yAxis);

        ArrayList<PointValue> graphPoints = new ArrayList<>();
        for (int instance = 0; instance< mUserStressLevels.size(); instance++) {
            int stressValue = mUserStressLevels.get(instance).getUserStress();
            graphPoints.add(new PointValue(instance, stressValue));
        }

        Line lineGraph = new Line(graphPoints).setColor(Color.RED).setCubic(true);
        lineGraph.setFilled(true);
        List<Line> lineList = new ArrayList<>();
        lineList.add(lineGraph);

        mData.setLines(lineList);
        mChartView.setLineChartData(mData);

    }

    public void fillResultsTable(View v) {

        TableLayout timeStressTable = (TableLayout) v.findViewById(R.id.time_stress_table_layout);
        TableRow eachInstance;
        TextView timeTextView, stressTextView;

        for(int instance = 0; instance < mUserStressLevels.size(); instance++) {

            long timeOfResult = mUserStressLevels.get(instance).getTimeOfResult();
            int userStress = mUserStressLevels.get(instance).getUserStress();

            eachInstance = new TableRow(getActivity());

            timeTextView = new TextView(getActivity());
            timeTextView.setText(String.valueOf(timeOfResult));

            stressTextView = new TextView(getActivity());
            stressTextView.setText(String.valueOf(userStress));

            timeTextView.setTypeface(null, 1);
            timeTextView.setTextSize(13);

            stressTextView.setTypeface(null, 1);
            stressTextView.setTextSize(13);


            timeTextView.setPadding(220, 0, 560, 0);
            timeTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            stressTextView.setGravity(Gravity.CENTER_HORIZONTAL);


            eachInstance.addView(timeTextView);
            eachInstance.addView(stressTextView);
            timeStressTable.addView(eachInstance);
        }
    }


}
