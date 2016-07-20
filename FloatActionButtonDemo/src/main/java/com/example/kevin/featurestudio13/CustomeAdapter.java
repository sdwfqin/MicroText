package com.example.kevin.featurestudio13;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kevin on 15/7/23.
 */
public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> data = new ArrayList<>();

    public CustomeAdapter(Context context) {
        inflater = LayoutInflater.from(context);

    }

    public void bindData(ArrayList<String> data) {
        if (data != null) {
            this.data = data;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    @Override
    public CustomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomeAdapter.ViewHolder viewHolder, int i) {

        viewHolder.getTextView().setText(""+data.get(i));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
