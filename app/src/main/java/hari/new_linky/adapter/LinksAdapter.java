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
        holder.tv_title.setText(links.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
