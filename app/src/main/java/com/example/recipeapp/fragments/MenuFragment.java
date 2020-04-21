package com.example.recipeapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.recipeapp.R;
import com.example.recipeapp.activities.DetailActivity;
import com.example.recipeapp.adapters.MenuRecyclerAdapter;
import com.example.recipeapp.classes.Ingridient;
import com.example.recipeapp.classes.Recipe;
import com.example.recipeapp.classes.Step;
import com.example.recipeapp.connectinternet.ConnectInternet;
import com.example.recipeapp.connectinternet.IConnectInternet;
import com.example.recipeapp.connectinternet.NetworkUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements IConnectInternet, MenuRecyclerAdapter.ListItemClickListerner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private URL recipeURL;

    //context
    private Context context;

    //parts
    private FrameLayout frame;
    private MenuRecyclerAdapter mAdapter;
    private RecyclerView recyclerView;


    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //PREBUILT CODES FROM IDE
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        //FINDVIEW
        frame = view.findViewById(R.id.frame);
        recyclerView = view.findViewById(R.id.recyclerView);

        //FILL IN
        mAdapter = new MenuRecyclerAdapter(context, (MenuRecyclerAdapter.ListItemClickListerner) this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //connect to the internet
        try {
            recipeURL = NetworkUtils.buildRecipeURL();
        }catch (MalformedURLException e) {
            String textToShow = "URL recipe failed to build";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
        }

        if(recipeURL != null){
            Log.d("URLMAIN",""+recipeURL);
            new ConnectInternet(this).execute(recipeURL);
            frame.setVisibility(View.VISIBLE);
        }try {
            NetworkUtils.buildRecipeURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return view;
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

    @Override
    public void callback(List<Recipe> obj) {
        mAdapter.setMenuList(obj);
    }
}
