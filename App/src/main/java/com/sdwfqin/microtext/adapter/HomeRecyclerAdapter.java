package com.sdwfqin.microtext.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.listener.HomeRecyclerListener;
import com.sdwfqin.microtext.model.HomeModel;

import java.util.List;

/**
 * Created by sdwfqin on 2017/4/5.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    private List<HomeModel> mList;
    private Context mContext;
    private HomeRecyclerListener mHomeRecyclerListener;

    public HomeRecyclerAdapter(Context context, List<HomeModel> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setHomeRecyclerListener(HomeRecyclerListener mHomeRecyclerListener) {
        this.mHomeRecyclerListener = mHomeRecyclerListener;
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

            if (null != mHomeRecyclerListener) {
                mHomeRecyclerListener.onStart(view, mHomeModel);
            }
        }

    }
}
