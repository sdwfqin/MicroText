package com.sdwfqin.microtext.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.model.HomeModel;

import java.util.List;

/**
 * Created by sdwfqin on 2017/4/5.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    private List<HomeModel> mList;
    private Context mContext;
    private onClickListener mOnClickListener;

    public HomeRecyclerAdapter(Context context, List<HomeModel> list) {
        mContext = context;
        mList = list;
    }
    /**
     * 和Activity通信的接口
     */
    public interface onClickListener {
        void onStart(View view,HomeModel homeModel);
    }

    public onClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(onClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }


    @Override
    public HomeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items, parent, false);
        return new HomeRecyclerAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HomeRecyclerAdapter.ViewHolder holder, int position) {
        holder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView home_items_title;
        TextView home_items_content;
//            TextView home_items_info;

        private HomeModel mHomeModel;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            home_items_title = (TextView) itemView.findViewById(R.id.home_items_title);
            home_items_content = (TextView) itemView.findViewById(R.id.home_items_content);
//                home_items_info = (TextView) itemView.findViewById(R.id.home_items_info);
        }

        public void bindData(HomeModel homeModel) {

            mHomeModel = homeModel;
            home_items_title.setText(mHomeModel.getTitle());
            home_items_content.setText(mHomeModel.getContent());
//                home_items_info.setText(mHomeModel.getInfo());

        }

        @Override
        public void onClick(View view) {

            if (null != mOnClickListener) {
                mOnClickListener.onStart(view,mHomeModel);
            }
        }

    }
}
