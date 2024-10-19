package com.example.plmunandroid;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class RoomsFragment extends Fragment {
    private TabLayout tabLayout;
    private EditText searchBox;
    private ImageButton filterButton;
    private ChipGroup buildingFilterChips;
    private List<Room> roomsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rooms, container, false);

        // Initialize views
        tabLayout = view.findViewById(R.id.tab_layout);
        searchBox = view.findViewById(R.id.search_box);
        filterButton = view.findViewById(R.id.filter_button);
        buildingFilterChips = view.findViewById(R.id.building_filter_chips);

        // Initialize rooms list (replace with your actual data)
        roomsList = new ArrayList<>();
        roomsList.add(new Room("Room 301", "Computer Laboratory", "RLRC Building"));
        roomsList.add(new Room("Room 302", "Science Laboratory", "RLRC Building"));
        roomsList.add(new Room("Room 201", "Lecture Room", "GPB Building"));
        // Add more rooms as needed

        // Set up search functionality
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRooms(s.toString(), getSelectedBuilding());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle building filter selection
        buildingFilterChips.setOnCheckedStateChangeListener((group, checkedIds) -> {
            filterRooms(searchBox.getText().toString(), getSelectedBuilding());
        });

        // Set the Rooms tab as selected
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
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }

    private String getSelectedBuilding() {
        int checkedChipId = buildingFilterChips.getCheckedChipId();
        if (checkedChipId == View.NO_ID) return "All Buildings";
        // You'll need to implement this to return the selected building name
        // based on the checked chip ID
        return "All Buildings";
    }

    private void filterRooms(String query, String selectedBuilding) {
        List<Room> filteredList = new ArrayList<>();
        for (Room room : roomsList) {
            boolean matchesSearch = room.getName().toLowerCase().contains(query.toLowerCase()) ||
                    room.getType().toLowerCase().contains(query.toLowerCase());
            boolean matchesBuilding = selectedBuilding.equals("All Buildings") ||
                    room.getBuilding().equals(selectedBuilding);

            if (matchesSearch && matchesBuilding) {
                filteredList.add(room);
            }
        }
        // Update your UI with filtered rooms
        // You'll need to implement this based on how you're displaying the rooms
    }

    // Room model class
    private static class Room {
        private String name;
        private String type;
        private String building;

        public Room(String name, String type, String building) {
            this.name = name;
            this.type = type;
            this.building = building;
        }

        public String getName() { return name; }
        public String getType() { return type; }
        public String getBuilding() { return building; }
    }
}