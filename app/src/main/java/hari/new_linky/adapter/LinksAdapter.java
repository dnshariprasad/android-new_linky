package hari.new_linky.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hari.new_linky.R;
import hari.new_linky.model.Link;

/**
 * Created by Ramesh on 9/16/16.
 */
public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.ViewHolder> {
    private Context context;
    private List<Link> links;

    public LinksAdapter(Context context, List<Link> links) {
        this.context = context;
        this.links = links;
    }

    public void notifyData(List<Link> links) {
        this.links = links;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_link, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(links.get(position).getTitle());
        holder.tvTargetCategory.setText(links.get(position).getTargetCategory());
        holder.tvTargetInfo.setText(links.get(position).getTargetInfo());
        holder.tvDescription.setText(links.get(position).getDescription());
        holder.tvTags.setText(links.get(position).getTags());
        holder.tvCreatedAt.setText(links.get(position).getCreatedAt());
        holder.tvUpdatedAt.setText(links.get(position).getUpdatedAt());
        holder.tvUrl.setText(links.get(position).getUrl());

    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  tvTitle, tvTargetCategory, tvTargetInfo, tvDescription, tvTags, tvCreatedAt, tvUpdatedAt, tvUrl;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTargetCategory = (TextView) itemView.findViewById(R.id.tv_targetCategory);
            tvTargetInfo = (TextView) itemView.findViewById(R.id.tv_targetInfo);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            tvTags = (TextView) itemView.findViewById(R.id.tv_tags);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tv_createdAt);
            tvUpdatedAt = (TextView) itemView.findViewById(R.id.tv_updatedAt);
            tvUrl = (TextView) itemView.findViewById(R.id.tv_url);

        }
    }
}
