package com.sdwfqin.microtext.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdwfqin.microtext.Model.SecondModel;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.activity.ShowImageActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<SecondModel> mMainList;
    private Activity activity;
    private boolean hasTitle;

    public RecyclerViewAdapter(Activity activity, List<SecondModel> mainList,boolean hasTitle) {
        this.activity = activity;
        this.mMainList = mainList;
        this.hasTitle = hasTitle;
    }

    public List<SecondModel> getmMainList() {
        return mMainList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.recycler_view_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(activity)
                .load(mMainList.get(position).getIamgeUrl())
                .placeholder(R.drawable.downloading)
                .error(R.drawable.downloading)
                .into(holder.imageView);
        if (hasTitle) {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(mMainList.get(position).getTitle());
        } else {
            holder.title.setVisibility(View.GONE);
        }
        holder.imageView.setTag(mMainList.get(position).getIamgeUrl());
        ViewCompat.setTransitionName(holder.imageView, mMainList.get(position).getIamgeUrl());
    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgView);
            title = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Bitmap bitmap = null;
//                            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
//                            if (bitmapDrawable != null) {
//                                bitmap = bitmapDrawable.getBitmap();
//                            }
                            Intent intent = new Intent(activity, ShowImageActivity.class);
                            intent.putExtra("mainList", (Serializable) mMainList);
                            intent.putExtra("position", getLayoutPosition());
                            intent.putExtra(AppConfig.COLOR, 0xff000000);
//                            intent.putExtra(AppConfig.COLOR, AppUtils.getPaletteColor(bitmap));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ActivityOptionsCompat options = ActivityOptionsCompat
                                        .makeSceneTransitionAnimation(activity, itemView, mMainList.get(getLayoutPosition()).getIamgeUrl());
                                ActivityCompat.startActivity(activity, intent, options.toBundle());
                            } else {
                                activity.startActivity(intent);
                            }

                        }
                    }

            );
        }
    }
}
