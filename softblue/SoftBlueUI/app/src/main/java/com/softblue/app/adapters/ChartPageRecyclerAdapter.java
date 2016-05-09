package com.softblue.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softblue.app.R;
import com.softblue.app.model.ChartPage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kamil.niezrecki@gmail.com
 */
public class ChartPageRecyclerAdapter extends RecyclerView.Adapter<ChartPageRecyclerAdapter.SimpleItemViewHolder> {

    private List<ChartPage> items;

    public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.page_label)
        TextView pageLabel;
        @BindView(R.id.sub_label)
        TextView subPageLabel;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public ChartPageRecyclerAdapter(List<ChartPage> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public SimpleItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.page_item, viewGroup, false);
        return new SimpleItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleItemViewHolder viewHolder, int position) {
        viewHolder.pageLabel.setText(items.get(position).getLabel());
        viewHolder.subPageLabel.setText(items.get(position).getSubLabel());
    }
}