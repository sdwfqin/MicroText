package com.sdwfqin.microtext.ui.juzimi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.chrisbanes.photoview.PhotoView;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseFragment;
import com.sdwfqin.microtext.base.Constants;
import com.sdwfqin.microtext.contract.ShowImageContract;
import com.sdwfqin.microtext.model.bean.JuZiMiBean;
import com.sdwfqin.microtext.presenter.ShowImagePresenter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.zip.Inflater;

import butterknife.BindView;

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
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_show_image;
    }

    @Override
    protected void initEventAndData() {

        initMenuClick();

        if (getArguments() != null) {
            mJuZiMiBean = (JuZiMiBean) getArguments().getSerializable("mainList");
            position = getArguments().getInt("position", 0);
            mImageUrl = mJuZiMiBean.getIamgeUrl();
        }

        if (!TextUtils.isEmpty(mJuZiMiBean.getTitle())) {
            showimageScrollText.setVisibility(View.VISIBLE);
            showimageText.setText(mJuZiMiBean.getTitle());
        } else {
            showimageScrollText.setVisibility(View.GONE);
        }

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
                saveImage();
                return false;
            }
        });
    }

    /**
     * 使用Activity中toolbar的menu
     */
    private void initMenuClick() {
        ShowImageActivity activity = (ShowImageActivity) getActivity();
        Toolbar toolbar = activity.toolbar;
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_save:
                        saveImage();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 保存图片
     */
    public void saveImage() {
        if (SDCardUtils.isSDCardEnable()) {
            BitmapDrawable bitmapDrawable = null;
            try {
                // TODO:第二张图滑动到第一张图会找不到图片，保存图片会错乱，可能跟viewpager缓存有关
                bitmapDrawable = (BitmapDrawable) showimageImg.getDrawable();
                String file = Constants.SAVE_REAL_PATH + mImageUrl.substring(mImageUrl.lastIndexOf("/"));
                if (FileUtils.createOrExistsFile(file)) {
                    byte[] bitmap2Bytes = ImageUtils.bitmap2Bytes(bitmapDrawable.getBitmap(), Bitmap.CompressFormat.JPEG);
                    if (FileIOUtils.writeFileFromBytesByStream(file, bitmap2Bytes)) {
                        Snackbar.make(mView, "图片已保存至：" + Constants.SAVE_REAL_PATH, Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri uri = Uri.fromFile(new File(Constants.SAVE_REAL_PATH));
                        intent.setData(uri);
                        mContext.sendBroadcast(intent);
                    } else {
                        Toast.makeText(mActivity, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mActivity, "保存失败", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(mActivity, "保存失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mActivity, "内存卡不可用", Toast.LENGTH_SHORT).show();
        }
    }
}
