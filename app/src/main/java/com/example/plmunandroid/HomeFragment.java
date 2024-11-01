package com.example.plmunandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private EditText searchBox;
    private MaterialCardView buildingCard1, buildingCard2, buildingCard3, buildingCard4;
    private TextView noResultsText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize search box and building cards
        searchBox = view.findViewById(R.id.search_box);
        buildingCard1 = view.findViewById(R.id.rizal);
        buildingCard2 = view.findViewById(R.id.bonifacio);
        buildingCard3 = view.findViewById(R.id.delpilar);
        buildingCard4 = view.findViewById(R.id.mabini);
        noResultsText = view.findViewById(R.id.no_results_text);

        // listener for the search box
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBuildings(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Initialize TabLayout
        tabLayout = view.findViewById(R.id.tab_layout);

        // Buildings tab as selected by default
        if (tabLayout.getTabAt(0) != null) {
            tabLayout.getTabAt(0).select();
        }

        // Handle tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {  // Rooms tab
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout, new RoomsFragment())
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return view;
    }

    // Filter function to search for buildings
    private void filterBuildings(String query) {
        query = query.toLowerCase().trim();

        if (query.isEmpty()) {
            // Show all buildings if the search query is empty
            buildingCard1.setVisibility(View.VISIBLE);
            buildingCard2.setVisibility(View.VISIBLE);
            buildingCard3.setVisibility(View.VISIBLE);
            buildingCard4.setVisibility(View.VISIBLE);
            noResultsText.setVisibility(View.GONE);
        } else {
            // Filter by checking if the query matches any building name
            buildingCard1.setVisibility("rizal building, rlrc, research and learning resource center".contains(query) ? View.VISIBLE : View.GONE);
            buildingCard2.setVisibility("bonifacio building, main".contains(query) ? View.VISIBLE : View.GONE);
            buildingCard3.setVisibility("del pilar building, annex".contains(query) ? View.VISIBLE : View.GONE);
            buildingCard4.setVisibility("mabini building, student center".contains(query) ? View.VISIBLE : View.GONE);

            // Check visibility of each card
            boolean anyVisible = (buildingCard1.getVisibility() == View.VISIBLE ||
                    buildingCard2.getVisibility() == View.VISIBLE ||
                    buildingCard3.getVisibility() == View.VISIBLE ||
                    buildingCard4.getVisibility() == View.VISIBLE);

            // Show or hide the no-results message based on anyVisible
            noResultsText.setVisibility(anyVisible ? View.GONE : View.VISIBLE);
        }
    }

    // Method to navigate to ExploreFragment for RLRC info
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button rlrcButton = view.findViewById(R.id.rlrc_details);

        rlrcButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(MainActivity.EXTRA_SHOW_RLRC_INFO, true);
            startActivity(intent);
        });
    }
}
