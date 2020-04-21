package com.example.recipeapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.recipeapp.AppExecutor;
import com.example.recipeapp.R;
import com.example.recipeapp.activities.DetailActivity;
import com.example.recipeapp.adapters.FavoriteRecyclerAdapter;
import com.example.recipeapp.adapters.MenuRecyclerAdapter;
import com.example.recipeapp.classes.Ingridient;
import com.example.recipeapp.classes.Recipe;
import com.example.recipeapp.classes.Step;
import com.example.recipeapp.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment implements FavoriteRecyclerAdapter.ListItemClickListerner {

    //PREBUILTS
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //context
    private Context context;

    //parts
    private FrameLayout frame;
    private FavoriteRecyclerAdapter mAdapter;
    private RecyclerView recyclerView;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorite, container, false);

        //FINDVIEW
        frame = view.findViewById(R.id.framefavorit);
        recyclerView = view.findViewById(R.id.recyclerViewFav);

        //FILL IN
        mAdapter = new FavoriteRecyclerAdapter(context, (FavoriteRecyclerAdapter.ListItemClickListerner) this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        final ArrayList<Recipe> favoritRecipe = new ArrayList<Recipe>();
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Recipe> favorit = AppDatabase
                        .getInstance(context.getApplicationContext()).taskDao().loadAllTasks();

                for (Recipe recipeEntry : favorit) {
                    Recipe a = new Recipe(recipeEntry.getId()
                            , recipeEntry.getName()
                            , recipeEntry.getIngredients()
                            , recipeEntry.getSteps()
                            , recipeEntry.getServings()
                            , recipeEntry.getImage());

                    favoritRecipe.add(a);
                }
            }
        });

        AppExecutor.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                mAdapter.setMenuList(favoritRecipe);
            }
        });

        frame.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final ArrayList<Recipe> favoritRecipe = new ArrayList<Recipe>();
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Recipe> favorit = AppDatabase
                        .getInstance(context.getApplicationContext()).taskDao().loadAllTasks();

                for (Recipe recipeEntry : favorit) {
                    Recipe a = new Recipe(recipeEntry.getId()
                            , recipeEntry.getName()
                            , recipeEntry.getIngredients()
                            , recipeEntry.getSteps()
                            , recipeEntry.getServings()
                            , recipeEntry.getImage());

                    favoritRecipe.add(a);
                }
            }
        });

        AppExecutor.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                mAdapter.setMenuList(favoritRecipe);
            }
        });
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onListItemClicked(Recipe t) {
        Toast.makeText(this.getContext(),t.getName(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, DetailActivity.class);

        ArrayList<Ingridient> ingredientArrayList = new ArrayList<>();
        ArrayList<Step> stepArrayList = new ArrayList<>();

        List<Ingridient> ingredientList = t.getIngredients();
        List<Step> stepList = t.getSteps();

        for (Ingridient ingredient : ingredientList) {
            ingredientArrayList.add(ingredient);
        }

        for (Step step : stepList) {
            stepArrayList.add(step);
        }

        intent.putExtra("ID", t.getId().toString());
        intent.putExtra("NAME", t.getName());
        intent.putExtra("SERVING", t.getServings().toString());
        intent.putParcelableArrayListExtra("INGREDIENT", ingredientArrayList);
        intent.putParcelableArrayListExtra("STEP", stepArrayList);
        intent.putExtra("PHOTO",t.getImage());

        startActivity(intent);
    }
}
