package com.example.recipeapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeapp.AppExecutor;
import com.example.recipeapp.R;
import com.example.recipeapp.adapters.MenuRecyclerAdapter;
import com.example.recipeapp.adapters.StepRecyclerAdapter;
import com.example.recipeapp.classes.Ingridient;
import com.example.recipeapp.classes.Recipe;
import com.example.recipeapp.classes.Step;
import com.example.recipeapp.connectinternet.ConnectInternet;
import com.example.recipeapp.connectinternet.NetworkUtils;
import com.example.recipeapp.database.AppDatabase;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements StepRecyclerAdapter.ListItemClickListener {

    private String id, name, serving, photo;
    private TextView nameTextView, servingTextView, ingredientsTextView, stepsTextview;
    private RecyclerView stepRecyclerView;
    private FrameLayout stepFrame;
    private ImageView favoriteButton;

    private List<Ingridient> ingredientList = new ArrayList<Ingridient>();
    private List<Step> stepList = new ArrayList<Step>();

    private String TAG = "DETAILACTIVITY";

    //adapter
    private StepRecyclerAdapter mAdapter;

    //bool for star
    public Boolean starCondition = false;

    //database
    private AppDatabase recipeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        recipeDB = AppDatabase.getInstance(this.getApplicationContext());

        //setup favorite button
        favoriteButton = findViewById(R.id.star);

        //find view
        nameTextView = findViewById(R.id.name);
        servingTextView = findViewById(R.id.serving);
        ingredientsTextView = findViewById(R.id.ingredient);
        stepsTextview = findViewById(R.id.steps);

        //recycler view
        stepFrame = findViewById(R.id.frameSteps);
        stepRecyclerView = findViewById(R.id.recyclerViewSteps);

        //get intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("ID");
            name = extras.getString("NAME");
            serving = extras.getString("SERVING");
            ingredientList = extras.getParcelableArrayList("INGREDIENT");
            stepList = extras.getParcelableArrayList("STEP");
            photo = extras.getString("PHOTO");
        }

        final int idnow = Integer.parseInt(id);
        //set star condition
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Recipe favouriteEntry = recipeDB.taskDao().loadFavById(idnow);
                if (favouriteEntry != null){
                    AppExecutor.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            starCondition = true;
                            Log.d(TAG, "run: SHOW THIS IF TRUE ");
                            setIconFav(starCondition, favoriteButton);
                        }
                    });

                } else {
                    AppExecutor.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            starCondition = false;
                            Log.d(TAG, "run: SHOW THIS IF TRUE ");
                            setIconFav(starCondition, favoriteButton);
                        }
                    });
                }
            }
        });
        Log.d(TAG, "onCreate: STAR" + starCondition);


        //settexts
        nameTextView.setText(name);
        servingTextView.setText(servingTextView.getText() + " " + serving);

        //ingredient
        //ingredientsTextView.setText(ingredient.getQuantity().toString());
        for (int i = 0; i < ingredientList.size(); i++) {
            String ingredientQuantity = ingredientList.get(i).getIngredient();
            String measureQuantity = ingredientList.get(i).getMeasure();
            String quantityQuantity = ingredientList.get(i).getQuantity().toString();

            ingredientsTextView.setText(ingredientsTextView.getText() + "- " + measureQuantity + " "
                    + quantityQuantity + " of "
                    + ingredientQuantity + " \n" + " \n");
        }

        //stepsTextview.setText(step.getDescription().toString());
        //FILL IN RecycleView
        mAdapter = new StepRecyclerAdapter(this, this);
        mAdapter.setMenuList(stepList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        stepRecyclerView.setLayoutManager(mLayoutManager);
        stepRecyclerView.setItemAnimator(new DefaultItemAnimator());
        stepRecyclerView.setAdapter(mAdapter);
        stepFrame.setVisibility(View.VISIBLE);


        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //change ID and serving to integer
                AppExecutor.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        int idInt = Integer.parseInt(id);
                        int servingInt = Integer.parseInt(serving);

                        try {
                            Recipe recipeEntry = new Recipe(idInt, name, ingredientList, stepList, servingInt, photo);
                            recipeDB.taskDao().insertTask(recipeEntry);
                            Log.d(TAG, "run: INSERT");

                            starCondition = true;

                        }catch (Exception e) {
                            Recipe recipeEntry = new Recipe(idInt, name, ingredientList, stepList, servingInt, photo);
                            recipeDB.taskDao().deleteTask(recipeEntry);
                            Log.d(TAG, "run: DELETE");

                            starCondition = false;

                        }
                    }
                });

                //if clause for inserting
                setIconFav(!starCondition, favoriteButton);
            }
        });


    }

    private  void setIconFav(Boolean isfavourite, ImageView star){

        if (!isfavourite){
            Log.d("Paporit","garis");
            star.setImageResource(R.drawable.emptystar);
            Toast.makeText(this, "Not in favourites", Toast.LENGTH_SHORT).show();
            this.starCondition = false;
        }else {
            Log.d("Paporit","isi");
            star.setImageResource(R.drawable.filledstar);
            Toast.makeText(this, "Added to favourites", Toast.LENGTH_SHORT).show();
            this.starCondition = true;
        }

    }

    @Override
    public void onListItemClicked(Step t) {
        Toast.makeText(this.getApplicationContext(),t.getVideoURL(),Toast.LENGTH_SHORT).show();
    }

}
