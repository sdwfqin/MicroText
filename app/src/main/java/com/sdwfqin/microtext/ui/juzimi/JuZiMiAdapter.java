package com.sdwfqin.microtext.ui.juzimi;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.model.bean.JuZiMiBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sdwfqin on 2017/7/22.
 */

public class JuZiMiAdapter extends BaseQuickAdapter<JuZiMiBean, BaseViewHolder> {

    public JuZiMiAdapter(@LayoutRes int layoutResId, @Nullable List<JuZiMiBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JuZiMiBean item) {
        if (item.getTitle() != null) {
            helper.setText(R.id.items_juzimi_title, item.getTitle())
                    .setVisible(R.id.items_juzimi_title, true);
        } else {
            helper.setVisible(R.id.items_juzimi_title, false);
        }
        Picasso.with(mContext)
                .load(item.getIamgeUrl())
                .placeholder(R.drawable.downloading)
                .error(R.drawable.downloading)
                .into((ImageView) helper.getView(R.id.items_juzimi_imgView));
//        Glide.with(mContext)
//                .load(item.getIamgeUrl())
//                // .placeholder(R.drawable.downloading)
//                .error(R.drawable.downloading) // 异常占位图
//                .crossFade() // 淡入加载效果
//                .into((ImageView) helper.getView(R.id.items_juzimi_imgView));
    }
}
