package com.hutech.doan.mangajava.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hutech.doan.mangajava.R;

/**
 * Created by Thuan on 11/24/2017.
 */

public class AboutFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment,
                container, false);

        return view;
    }

}
