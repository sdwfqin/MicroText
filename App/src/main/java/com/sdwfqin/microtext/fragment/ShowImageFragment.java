package com.sdwfqin.microtext.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.activity.ShowImageActivity;
import com.sdwfqin.microtext.listener.PermissionListener;
import com.sdwfqin.microtext.model.SecondModel;
import com.sdwfqin.microtext.utils.AppUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.senab.photoview.PhotoView;

public class ShowImageFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.imageView)
    PhotoView mPhotoView;
    @BindView(R.id.scroll_text)
    ScrollView scrollText;
    private SecondModel mSecondModel;
    private int position;
    private ShowImageActivity mActivity;
    private View mView;
    private String mImageUrl;
    private static final String TAG = "ShowImageFragment";

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

        //指出fragment愿意添加item到选项菜单
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_show_image, container, false);

        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageUrl = mSecondModel.getIamgeUrl();
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final TextView introduction = (TextView) view.findViewById(R.id.introduction);

        if (!TextUtils.isEmpty(mSecondModel.getTitle())) {
            scrollText.setVisibility(View.VISIBLE);
            introduction.setText(mSecondModel.getTitle());
        } else {
            scrollText.setVisibility(View.GONE);
        }
        ViewCompat.setTransitionName(mPhotoView, mImageUrl);
        Picasso.with(getActivity()).load(mImageUrl)
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

        // setOnLongClickListener中return的值决定是否在长按后再加一个短按动作
        // true为不加短按,false为加入短按
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tupian();
                return false;
            }
        });
    }

    // 保存图片
    public void tupian() {

        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_dibu_tanchuang, null);
        final Dialog dialog = new Dialog(getActivity(), R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);

        dialog.show();

        // 设置监听
        Button xiangce = (Button) window.findViewById(R.id.btn_pick_savephoto);
        Button cancel = (Button) window.findViewById(R.id.btn_cancel);

        //保存
        xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestRuntimePermission(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        try {
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) mPhotoView.getDrawable();
                            AppUtils.saveFile(bitmapDrawable.getBitmap(), mImageUrl.substring(mImageUrl.lastIndexOf("/")), getActivity());
                            dialog.dismiss();
                            Snackbar.make(mView, "图片已保存至" + AppUtils.SAVE_REAL_PATH, Snackbar.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e(TAG, "onGranted: ", e);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onDenied() {
                        dialog.dismiss();
                        Toast.makeText(mActivity, "无权操作，请授权写入权限", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        tupian();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
