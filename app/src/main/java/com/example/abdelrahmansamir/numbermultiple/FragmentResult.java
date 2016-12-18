package com.example.abdelrahmansamir.numbermultiple;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentResult extends Fragment {

    static RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_result, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleViewerTextView);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        return rootView;

    }


}
