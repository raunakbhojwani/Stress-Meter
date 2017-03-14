package edu.dartmouth.cs.raunakbhojwani.stressmeter_raunak;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.content.Intent;

/*
 * Created by Raunak Bhojwani on 1/26/2017
 * Influenced and guided by: https://developer.android.com/guide/topics/ui/layout/gridview.html#example
 */
public class ImageRequestFragment extends Fragment {

    private Integer mGridNumber = 0;


    public ImageRequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View stessLevelView = inflater.inflate(R.layout.fragment_image_request, container, false);


        final GridView mGridView = (GridView) stessLevelView.findViewById(R.id.image_request_grid_view);
        final BaseAdapter imageAdapter = new ImageAdapter(getActivity(), mGridNumber);
        mGridView.setAdapter(imageAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent stressLevelIntent = new Intent(getActivity(), ImageResponseActivity.class);
                stressLevelIntent.putExtra("position", "" + position);
                stressLevelIntent.putExtra("grid_number", "" + mGridNumber);
                startActivityForResult(stressLevelIntent, 1);
            }
        });

        Button mMoreImagesButton = (Button) stessLevelView.findViewById(R.id.more_images_button);
        mMoreImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                StressAlert.alertEnd();
                mGridNumber = (mGridNumber + 1) % 3;

                final BaseAdapter imageAdapter = new ImageAdapter(getActivity(), mGridNumber);
                mGridView.setAdapter(imageAdapter);
            }
        });

        return stessLevelView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 1) {
                getActivity().finish();
            }
        }
    }

}
