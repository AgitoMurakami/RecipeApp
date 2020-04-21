package com.example.recipeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;
import com.example.recipeapp.classes.Recipe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.MenuViewHolder> {

    Context context;
    private List<Recipe> dataList = new ArrayList<>();
    private final ListItemClickListerner<Recipe> mOnclickListener;
    private String textView = "";

    public interface ListItemClickListerner<T> {
        void onListItemClicked(Recipe t);
    }

    public MenuRecyclerAdapter(Context context, ListItemClickListerner onClickListener) {
        this.context = context;
        this.mOnclickListener = onClickListener;
    }

    public void setMenuList (List<Recipe> menuList) {
        dataList = new ArrayList<>();
        dataList = menuList;
        notifyDataSetChanged();
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipe_recycler_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, final int position) {
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

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        //private ImageView image;
        private View itemView;

        public MenuViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            //image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.menu_title);

        }
    }
}