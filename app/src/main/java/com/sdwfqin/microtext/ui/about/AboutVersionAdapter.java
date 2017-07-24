package com.sdwfqin.microtext.ui.about;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.model.bean.VersionBean;

import java.util.List;

/**
 * Created by sdwfqin on 2017/7/24.
 */
public class AboutVersionAdapter extends BaseQuickAdapter<VersionBean, BaseViewHolder> {

    public AboutVersionAdapter(@LayoutRes int layoutResId, @Nullable List<VersionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VersionBean item) {
        helper
                .setText(R.id.items_version_code, item.getCode())
                .setText(R.id.items_version_des, item.getDes());
    }
}
