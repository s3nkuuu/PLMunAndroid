package com.example.plmunandroid;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;

public class RoomsFragment extends Fragment {

    private TabLayout tabLayout;
    private EditText searchBox;
    private View roomCard1, roomCard2, roomCard3, roomCard4, roomCard5;
    private TextView noResultsText;
    private ChipGroup chipGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rooms, container, false);

        tabLayout = view.findViewById(R.id.tab_layout);
        searchBox = view.findViewById(R.id.search_box);
        roomCard1 = view.findViewById(R.id.library);
        roomCard2 = view.findViewById(R.id.cl1);
        roomCard3 = view.findViewById(R.id.heroeshall);
        roomCard4 = view.findViewById(R.id.CITCSFaculty);
        roomCard5 = view.findViewById(R.id.medical_clinic);
        noResultsText = view.findViewById(R.id.no_results_text);
        chipGroup = view.findViewById(R.id.chip_group);

        // Rooms tab as selected
        if (tabLayout.getTabAt(1) != null) {
            tabLayout.getTabAt(1).select();
        }

        // Handle tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {  // Buildings tab
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout, new HomeFragment())
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

        // text change listener for search box
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRooms(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // chip selection listener
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = view.findViewById(checkedId);
            if (chip != null) {
                String selectedChipText = chip.getText().toString();
                filterRoomsWithChips(selectedChipText);
            }
        });

        return view;
    }

    // Filter function to search for rooms
    private void filterRooms(String query) {
        query = query.toLowerCase().trim();

        if (query.isEmpty()) {
            // Show all rooms if the search query is empty
            showAllRooms();
            noResultsText.setVisibility(View.GONE);
        } else {
            // Filter by checking if the query matches any room name
            roomCard1.setVisibility("library".contains(query) ? View.VISIBLE : View.GONE);
            roomCard2.setVisibility("comlab1, cl1, computer laboratory 1".contains(query) ? View.VISIBLE : View.GONE);
            roomCard3.setVisibility("heroes hall".contains(query) ? View.VISIBLE : View.GONE);
            roomCard4.setVisibility("citcs faculty".contains(query) ? View.VISIBLE : View.GONE);
            roomCard5.setVisibility("medical clinic, clinic".contains(query) ? View.VISIBLE : View.GONE);

            // Check visibility of each card
            boolean anyVisible = (roomCard1.getVisibility() == View.VISIBLE ||
                    roomCard2.getVisibility() == View.VISIBLE ||
                    roomCard3.getVisibility() == View.VISIBLE ||
                    roomCard4.getVisibility() == View.VISIBLE ||
                    roomCard5.getVisibility() == View.VISIBLE);

            // Show or hide the no-results message based on anyVisible
            noResultsText.setVisibility(anyVisible ? View.GONE : View.VISIBLE);
        }
    }

    // Filter function to handle room filtering based on selected chips
    private void filterRoomsWithChips(String selectedChip) {
        switch (selectedChip) {
            case "All Buildings":
                showAllRooms();
                break;
            case "RLRC":
                roomCard1.setVisibility(View.VISIBLE);
                roomCard2.setVisibility(View.GONE);
                roomCard3.setVisibility(View.VISIBLE);
                roomCard4.setVisibility(View.GONE);
                roomCard5.setVisibility(View.GONE);
                break;
            case "Main":
                roomCard1.setVisibility(View.GONE);
                roomCard2.setVisibility(View.VISIBLE);
                roomCard3.setVisibility(View.GONE);
                roomCard4.setVisibility(View.VISIBLE);
                roomCard5.setVisibility(View.VISIBLE);
                break;
            default:
                showAllRooms();
                break;
        }
    }

    // function to show all room cards
    private void showAllRooms() {
        roomCard1.setVisibility(View.VISIBLE);
        roomCard2.setVisibility(View.VISIBLE);
        roomCard3.setVisibility(View.VISIBLE);
        roomCard4.setVisibility(View.VISIBLE);
        roomCard5.setVisibility(View.VISIBLE);
    }
}
