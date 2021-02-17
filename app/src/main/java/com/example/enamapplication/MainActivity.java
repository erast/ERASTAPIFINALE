package com.example.enamapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    TextView Name, Status, Type, Gender;
    ImageView imgIcon;
    String id="1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name=findViewById(R.id.name);
        Status=findViewById(R.id.status);
        Type=findViewById(R.id.type);
        Gender=findViewById(R.id.gender);
        afficher();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recherchee, menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setQueryHint("Write the number of a rick and morty character");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                id=query;
                afficher();

                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(getCurrentFocus() !=null)
                {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void afficher()
    {
        String url="https://rickandmortyapi.com/api/character/" + id;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                   

                    String name =response.getString("name");
                    String status=response.getString("status");
                    String type=response.getString("type");
                    String gender=response.getString("gender");

                    Name.setText(name);
                    Status.setText(status);
                    Type.setText(type);
                    Gender.setText(gender);

                    String ImageUri= "https://rickandmortyapi.com/api/character/avatar/"+id+".jpeg";
                    imgIcon=findViewById(R.id.imgIcon);
                    Uri myUri= Uri.parse(ImageUri);
                    Picasso.with(MainActivity.this).load(myUri).resize(200,200).into(imgIcon);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;
        
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

    }
}
