package com.example.recipeapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;
import com.example.recipeapp.activities.VideoPopActivity;
import com.example.recipeapp.classes.Recipe;
import com.example.recipeapp.classes.Step;

import java.util.ArrayList;
import java.util.List;

public class StepRecyclerAdapter extends RecyclerView.Adapter<StepRecyclerAdapter.MenuViewHolder> {


    Context context;
    private List<Step> dataList = new ArrayList<>();
    private final ListItemClickListener<Step> mOnclickListener;
    private String textView = "";

    public interface ListItemClickListener<T> {
        void onListItemClicked(Step t);
    }

    public StepRecyclerAdapter(Context context, ListItemClickListener onClickListener) {
        this.context = context;
        this.mOnclickListener = onClickListener;
    }

    public void setMenuList (List<Step> menuList) {
        dataList = new ArrayList<>();
        dataList = menuList;
        notifyDataSetChanged();
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.step_recycler_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnclickListener.onListItemClicked(dataList.get(position));
            }
        });
        holder.title.setId(position);
        final int i = holder.title.getId();
        if (i == 0) {
            holder.title.setText("Starting Preparation ");
        } else {
            holder.title.setText("Step " + String.valueOf(dataList.get(i).getId()));
        }

        holder.videoButton.setText("i");
        holder.videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                //if you need position, just use recycleViewHolder.getAdapterPosition();
                holder.videoButton.setId(position);
                Intent intent = new Intent(v.getContext(), VideoPopActivity.class);
                intent.putExtra("URL", dataList.get(i).getVideoURL());
                intent.putExtra("DESCRIPTION", dataList.get(i).getDescription());
                context.startActivity(intent);
            }
        });


        //Glide.with(context).load("https://image.tmdb.org/t/p/w185/"+dataList.get(position).getPosterPath()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private Button videoButton;
        //private ImageView image;
        private View itemView;

        public MenuViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            //image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.menu_title);
            videoButton = (Button) itemView.findViewById(R.id.videobutton);
        }
    }
}