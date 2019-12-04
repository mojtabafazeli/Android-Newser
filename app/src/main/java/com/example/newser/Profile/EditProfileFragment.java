package com.example.newser.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newser.Utils.UniversalImageLoader;
import com.example.newser.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class EditProfileFragment extends Fragment {
    private static final String TAG = "ProfileActivity";

    ImageView profilePhoto;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        profilePhoto = view.findViewById(R.id.profile_photo);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));

        setProfileImage();

        ImageView backArrow = view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;

    }

    private void setProfileImage() {
        String imgURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
        UniversalImageLoader.setImage(imgURL, profilePhoto, null, "https://");
    }

}