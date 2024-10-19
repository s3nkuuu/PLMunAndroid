package com.example.plmunandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.BuildingViewHolder> {
    private List<Building> buildingsList;
    private List<Building> buildingsListFull;
    private OnBuildingClickListener listener;

    public BuildingsAdapter(List<Building> buildingsList) {
        this.buildingsList = buildingsList;
        this.buildingsListFull = new ArrayList<>(buildingsList);
    }

    @NonNull
    @Override
    public BuildingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_building, parent, false);
        return new BuildingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingViewHolder holder, int position) {
        Building building = buildingsList.get(position);
        holder.buildingName.setText(building.getName());
        holder.buildingDescription.setText(building.getDescription());
        holder.buildingImage.setImageResource(building.getImageResource());

        holder.viewDetailsButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewDetailsClick(building);
            }
        });

        holder.navigateButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNavigateClick(building);
            }
        });
    }

    @Override
    public int getItemCount() {
        return buildingsList.size();
    }

    public void filter(String query) {
        buildingsList.clear();
        if (query.isEmpty()) {
            buildingsList.addAll(buildingsListFull);
        } else {
            query = query.toLowerCase();
            for (Building building : buildingsListFull) {
                if (building.getName().toLowerCase().contains(query) ||
                        building.getDescription().toLowerCase().contains(query)) {
                    buildingsList.add(building);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setOnBuildingClickListener(OnBuildingClickListener listener) {
        this.listener = listener;
    }

    static class BuildingViewHolder extends RecyclerView.ViewHolder {
        ImageView buildingImage;
        TextView buildingName;
        TextView buildingDescription;
        MaterialButton viewDetailsButton;
        MaterialButton navigateButton;

        BuildingViewHolder(View itemView) {
            super(itemView);
            buildingImage = itemView.findViewById(R.id.building_image);
            buildingName = itemView.findViewById(R.id.building_name);
            buildingDescription = itemView.findViewById(R.id.building_description);
            viewDetailsButton = itemView.findViewById(R.id.view_details_button);
            navigateButton = itemView.findViewById(R.id.navigate_button);
        }
    }

    public interface OnBuildingClickListener {
        void onViewDetailsClick(Building building);
        void onNavigateClick(Building building);
    }

    // Building model class (move this to a separate file in a real project)
    public static class Building {
        private String name;
        private String description;
        private int imageResource;

        public Building(String name, String description, int imageResource) {
            this.name = name;
            this.description = description;
            this.imageResource = imageResource;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public int getImageResource() { return imageResource; }
    }
}