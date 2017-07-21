package com.sdwfqin.microtext.ui.essay.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.model.bean.EssayBean;

import java.util.List;

/**
 * Created by sdwfqin on 2017/7/21.
 */
public class EssayAdapter extends BaseQuickAdapter<EssayBean, BaseViewHolder> {

    public EssayAdapter(@LayoutRes int layoutResId, @Nullable List<EssayBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EssayBean item) {
        helper.setText(R.id.essay_items_title, item.getTitle() + "")
                .setText(R.id.essay_items_content, item.getContent() + "")
                .addOnClickListener(R.id.essay_root);
    }
}
