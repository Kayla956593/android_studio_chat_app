package com.koddev.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class OnBoardingFragment3 extends Fragment {
    ImageView btn4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.frag_three,container,false);

        btn4 = (ImageView) root.findViewById(R.id.r);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_tmp();
            }
        });

        return root;
    }

    private void open_tmp() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
