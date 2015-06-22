package com.abed.cobi.View;

import android.animation.Animator;
import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abed.cobi.R;
import com.abed.cobi.View.Components.RotatableFrameLayout;


public class SideLayout extends Fragment implements RotatableFrameLayout.OnRotateListener{


    RotatableFrameLayout rotatable_layout;
    ImageView img;
    TextView txt;
    int[] imgs_adapter={R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4};
    String[] txts_adapter={"Stone","Meditation","Sea","Sleep"};

    public SideLayout() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_custom_layout, container, false);
        rotatable_layout=(RotatableFrameLayout)rootView.findViewById(R.id.rotatable_layout);
        rotatable_layout.setRotateListener(this);
        img=(ImageView)rootView.findViewById(R.id.img);
        txt=(TextView)rootView.findViewById(R.id.txt);
        return rootView;
    }

    @Override
    public void onRotate(int step) {
        int index=(step%imgs_adapter.length+imgs_adapter.length)%imgs_adapter.length;
        img.setImageResource(imgs_adapter[index]);
        txt.setText(txts_adapter[index]);
        img.animate().alpha(1).start();
        txt.animate().alpha(1).start();
    }

    @Override
    public void onRotateStarted() {
        img.animate().alpha(0).start();
        txt.animate().alpha(0).start();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
