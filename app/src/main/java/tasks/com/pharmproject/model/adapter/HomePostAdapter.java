package tasks.com.pharmproject.model.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import tasks.com.pharmproject.R;


public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.ViewHolder> {
    Context context;
    ArrayList objects;

    //
    public HomePostAdapter(ArrayList objects) {
        this.objects = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_home_post_item, parent, false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button btnShare;
        TextView textViewAuthorName, textViewStatus, textViewSupName, textViewContent, textViewNumberOfShare;

        ViewHolder(View v) {
            super(v);

        }
    }

}
