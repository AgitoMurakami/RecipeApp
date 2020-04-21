package com.example.recipeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;
import com.example.recipeapp.classes.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.FavoriteViewHolder> {

    Context context;
    private List<Recipe> dataList = new ArrayList<>();
    private final FavoriteRecyclerAdapter.ListItemClickListerner<Recipe> mOnclickListener;
    private String textView = "";

    public interface ListItemClickListerner<T> {
        void onListItemClicked(Recipe t);
    }

    public FavoriteRecyclerAdapter(Context context, FavoriteRecyclerAdapter.ListItemClickListerner onClickListener) {
        this.context = context;
        this.mOnclickListener = onClickListener;
    }

    public void setMenuList (List<Recipe> menuList) {
        dataList = new ArrayList<>();
        dataList = menuList;
        notifyDataSetChanged();
    }

    @Override
    public FavoriteRecyclerAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipe_recycler_item, parent, false);
        return new FavoriteRecyclerAdapter.FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteRecyclerAdapter.FavoriteViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnclickListener.onListItemClicked(dataList.get(position));
            }
        });
        holder.title.setId(position);
        int i = holder.title.getId();
        holder.title.setText(dataList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        //private ImageView image;
        private View itemView;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            //image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.menu_title);

        }
    }
}
