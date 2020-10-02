package com.example.newsapplication.ui.detail;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.newsapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DetailFragment extends Fragment {
    String imageURLD;
    String authorD;
    String titleD;
    String descriptionD;
    String publishedAtD;
    String urlD;

    Button button;

    public static final String IMAGE_URL = "image_url";
    public static final String AUTHOR = "author";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PUBLISHED_AT = "published";
    public static final String URL = "url";
    TextView title, author, description, published, url;
    ImageView imageView;
    NestedScrollView nestedScrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    private void getData() {
        imageURLD = getArguments().getString(IMAGE_URL);
        authorD = getArguments().getString(AUTHOR);
        titleD = getArguments().getString(TITLE);
        descriptionD = getArguments().getString(DESCRIPTION);
        publishedAtD = getArguments().getString(PUBLISHED_AT);
        urlD = getArguments().getString(URL);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setData();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)
                        .navigateUp();
            }
        });
    }

    private void setData() {
        Glide
                .with(this)
                .load(Uri.parse(imageURLD))
                .transform(new RoundedCorners(16))
                .into(imageView);
        author.setText(authorD);
        title.setText(titleD);
        description.setText(descriptionD);
        published.setText(getDate(publishedAtD));
        url.setText(urlD);
    }

    private void init(View view) {
        author = view.findViewById(R.id.detail_author_tv);
        title = view.findViewById(R.id.detail_title_tv);
        description = view.findViewById(R.id.detail_description_tv);
        published = view.findViewById(R.id.detail_published_at_tv);
        url = view.findViewById(R.id.detail_url_tv);
        imageView = view.findViewById(R.id.detail_image);
        nestedScrollView = view.findViewById(R.id.detail_scroll_view);
        button = view.findViewById(R.id.detail_btn);
    }

    public String getDate(String date) {
        String date1 = "";
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").
                    parse(Objects.requireNonNull(date));
            date1 = new SimpleDateFormat("dd-MMM-yyyy")
                    .format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }
}