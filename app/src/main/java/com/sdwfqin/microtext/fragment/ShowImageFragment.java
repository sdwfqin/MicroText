package com.sdwfqin.microtext.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sdwfqin.microtext.Model.SecondModel;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.activity.ShowImageActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowImageFragment extends Fragment {

    private SecondModel mSecondModel;
    private int position;
    private ShowImageActivity mActivity;
    @InjectView(R.id.imageView)
    PhotoView mPhotoView;

    public static Fragment newFragment(SecondModel SecondModel, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mainList", SecondModel);
        bundle.putInt("position", position);
        ShowImageFragment fragment = new ShowImageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (ShowImageActivity) context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSecondModel = (SecondModel) this.getArguments().getSerializable("mainList");
        position = this.getArguments().getInt("position", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_show_image, container, false);

        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String imageUrl = mSecondModel.getIamgeUrl();
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final TextView introduction = (TextView) view.findViewById(R.id.introduction);
        introduction.setText(mSecondModel.getTitle());
        ViewCompat.setTransitionName(mPhotoView, imageUrl);
        Picasso.with(getActivity()).load(imageUrl)
                .into(mPhotoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        Picasso.with(mActivity)
                .load(mSecondModel.getIamgeUrl())
                .into(mPhotoView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
