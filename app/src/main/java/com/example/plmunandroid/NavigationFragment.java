// NavigationFragment.java
package com.example.plmunandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;

public class NavigationFragment extends Fragment {
    private String roomName;
    private String building;
    private String floor;

    public NavigationFragment() {
        // Required empty constructor
    }

    public static NavigationFragment newInstance(String roomName, String building, String floor) {
        NavigationFragment fragment = new NavigationFragment();
        Bundle args = new Bundle();
        args.putString("roomName", roomName);
        args.putString("building", building);
        args.putString("floor", floor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomName = getArguments().getString("roomName");
            building = getArguments().getString("building");
            floor = getArguments().getString("floor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        TextView titleText = view.findViewById(R.id.navigation_title);
        TextView directionsText = view.findViewById(R.id.directions_text);
        ImageButton backButton = view.findViewById(R.id.back_button);

        titleText.setText("Directions to " + roomName);
        directionsText.setText(getDirections());

        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private String getDirections() {
        StringBuilder directions = new StringBuilder();

        // Add starting point
        directions.append("Starting from the Main Gate:\n\n");

        // Building-specific directions
        switch (building) {
            case "Rizal Building":
                directions.append("1. Enter through the main gate\n");
                directions.append("2. Walk straight ahead towards the Rizal Building\n");
                directions.append("3. Enter the Rizal Building main entrance\n");

                if (roomName.equals("Library")) {
                    directions.append("4. The Library is on the first floor\n");
                    directions.append("5. Turn right after entering the building\n");
                    directions.append("6. The Library entrance will be on your left\n");
                } else if (roomName.equals("Heroes Hall")) {
                    directions.append("4. Take the stairs to the 3rd floor\n");
                    directions.append("5. Turn right at the top of the stairs\n");
                    directions.append("6. Heroes Hall will be at the end of the corridor\n");
                }
                break;

            case "Bonifacio Building":
                directions.append("1. Enter through the main gate\n");
                directions.append("2. Turn right towards the Bonifacio Building\n");
                directions.append("3. Enter through the main entrance\n");

                if (roomName.equals("Computer Laboratory 1")) {
                    directions.append("4. Take the stairs to the 2nd floor\n");
                    directions.append("5. Turn left at the top of the stairs\n");
                    directions.append("6. Computer Laboratory 1 is the third room on your right\n");
                } else if (roomName.equals("CITCS Faculty")) {
                    directions.append("4. Take the stairs to the 2nd floor\n");
                    directions.append("5. Turn right at the top of the stairs\n");
                    directions.append("6. CITCS Faculty room is at the end of the corridor\n");
                } else if (roomName.equals("Medical Clinic")) {
                    directions.append("4. The Medical Clinic is on the first floor\n");
                    directions.append("5. Turn left after entering the building\n");
                    directions.append("6. The clinic will be on your right\n");
                }
                break;
        }

        directions.append("\nEstimated walking time: 3-5 minutes");
        return directions.toString();
    }
}