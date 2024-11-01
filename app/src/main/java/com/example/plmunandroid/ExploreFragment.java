package com.example.plmunandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ExploreFragment extends Fragment {

    private boolean showRLRCInfo;

    public static ExploreFragment newInstance(boolean showRLRCInfo) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putBoolean("showRLRCInfo", showRLRCInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            showRLRCInfo = getArguments().getBoolean("showRLRCInfo");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button rlrcButton = view.findViewById(R.id.rlrc_btn);
        Button mainButton = view.findViewById(R.id.main_btn);

        if (showRLRCInfo) {
            showBottomSheet("RLRC");
        }

        rlrcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet("RLRC");
            }
        });

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet("MAIN");
            }
        });
    }

    private void showBottomSheet(String type) {
        if ("RLRC".equals(type)) {
            RLRCInfo rlrcInfoBottomSheet = new RLRCInfo();
            rlrcInfoBottomSheet.show(getParentFragmentManager(), rlrcInfoBottomSheet.getTag());
        } else if ("MAIN".equals(type)) {
            MAINInfo mainInfoBottomSheet = new MAINInfo();
            mainInfoBottomSheet.show(getParentFragmentManager(), mainInfoBottomSheet.getTag());
        }
    }
}
