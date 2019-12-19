package com.example.newser.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.newser.R;
import com.example.newser.Utils.FilePaths;
import com.example.newser.Utils.FileSearch;
import com.example.newser.Utils.GridImageAdapter;

import java.util.ArrayList;

public class SelectProfilePicture extends Fragment {
    private String TAG = "SelectProfilePicture Fragment";

    //constants
    private static final int NUM_OF_COLUMNS = 3;


    private ImageView saveButton;
    private GridView gridView;
    private ArrayList<String> imgURLs;

    //vars
    private ArrayList<String> directories;
    private String mAppend = "file:/";
    private String selectedImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_profile_picture, container, false);
        directories = new ArrayList<>();
        gridView = view.findViewById(R.id.gridview);
        init();

        EditProfileFragment fragment = new EditProfileFragment();

        saveButton = view.findViewById(R.id.saveChanges);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle profilePicture = new Bundle();
                profilePicture.putString("profilePicture", selectedImage);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }

        });

        ImageView backArrow = view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });

        return view;
    }

    private void init() {
        FilePaths filePaths = new FilePaths();

//        check if other folders inside folder pictures;
        if (FileSearch.getDirectoryPath((filePaths.PICTURES)) != null) {
            directories = FileSearch.getDirectoryPath(filePaths.PICTURES);
        }
        directories.add(filePaths.CAMERA);

        //setup our image grid for the images
        setupGridView(directories);
    }

    private void setupGridView(ArrayList<String> selectedDirectory) {
        imgURLs = new ArrayList<String>();
        for (int i = 0; i < selectedDirectory.size(); i++) {
            String directory = selectedDirectory.get(i);
            imgURLs.addAll(FileSearch.getFilePath(directory));
        }

        //set the grid column width
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth / NUM_OF_COLUMNS;
        gridView.setColumnWidth(imageWidth);
        //use the grid adapter to adapt the images to grid view
        GridImageAdapter adapter = new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview, mAppend, imgURLs);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedImage = imgURLs.get(position);
            }
        });

    }
}
