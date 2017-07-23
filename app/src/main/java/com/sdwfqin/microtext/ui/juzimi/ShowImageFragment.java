package com.sdwfqin.microtext.ui.juzimi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseFragment;
import com.sdwfqin.microtext.contract.ShowImageContract;
import com.sdwfqin.microtext.model.bean.JuZiMiBean;
import com.sdwfqin.microtext.presenter.ShowImagePresenter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShowImageFragment extends BaseFragment<ShowImagePresenter> implements ShowImageContract.View {

    @BindView(R.id.showimage_img)
    PhotoView showimageImg;
    @BindView(R.id.showimage_text)
    TextView showimageText;
    @BindView(R.id.showimage_scroll_text)
    ScrollView showimageScrollText;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private JuZiMiBean mJuZiMiBean;
    private int position;
    private String mImageUrl;

    public static ShowImageFragment newInstance(JuZiMiBean juZiMiBean, int position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("mainList", juZiMiBean);
        bundle.putInt("position", position);
        ShowImageFragment fragment = new ShowImageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJuZiMiBean = (JuZiMiBean) this.getArguments().getSerializable("mainList");
        position = this.getArguments().getInt("position", 0);

        //指出fragment愿意添加item到选项菜单
        setHasOptionsMenu(true);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_show_image;
    }

    @Override
    protected void initEventAndData() {
        mImageUrl = mJuZiMiBean.getIamgeUrl();

        if (!TextUtils.isEmpty(mJuZiMiBean.getTitle())) {
            showimageScrollText.setVisibility(View.VISIBLE);
            showimageText.setText(mJuZiMiBean.getTitle());
        } else {
            showimageScrollText.setVisibility(View.GONE);
        }

        // ViewCompat.setTransitionName(showimageImg, mImageUrl);

        Picasso.with(getActivity()).load(mImageUrl)
                .into(showimageImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });

        // setOnLongClickListener中return的值决定是否在长按后再加一个短按动作
        // true为不加短按,false为加入短按
        showimageImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //tupian();
                return false;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //tupian();
    }
}
