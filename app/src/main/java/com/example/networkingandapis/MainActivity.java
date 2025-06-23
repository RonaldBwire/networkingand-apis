package com.example.networkingandapis;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle; // Import Bundle
import android.util.Log; // Import Log for logging
import android.view.View; // Import View for OnClickListener
import android.widget.Button; // Import Android's Button, not Firebase's
import androidx.recyclerview.widget.LinearLayoutManager; // Import LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView; // Import RecyclerView

import com.android.volley.Request; // Import Volley Request
import com.android.volley.RequestQueue; // Import Volley RequestQueue
import com.android.volley.toolbox.StringRequest; // Import Volley StringRequest
import com.android.volley.toolbox.Volley; // Import Volley for newRequestQueue

import org.json.JSONArray; // Import JSONArray
import org.json.JSONException; // Import JSONException
import org.json.JSONObject; // Import JSONObject

import java.util.ArrayList; // Import ArrayList
import java.util.List; // Import List

// Assuming you have a Post class and MyAdapter class defined elsewhere
// If not, you'll need to create them.
// For example:
/*
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    // Add getters if needed
    public int getUserId() { return userId; }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
}
*/

/*
// Example MyAdapter (simplified, you'll need to fill this out based on your item layout)
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PostViewHolder> {

    private List<Post> postList;

    public MyAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.titleTextView.setText(post.getTitle());
        holder.bodyTextView.setText(post.getBody());
        // Set other views if you have them
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView bodyTextView;
        // Add other TextViews/ImageViews for your item_post layout

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title); // Replace with your actual ID
            bodyTextView = itemView.findViewById(R.id.text_view_body);   // Replace with your actual ID
        }
    }
}
*/


public class MainActivity extends AppCompatActivity { // Removed 'implements extension' - not a standard Android interface unless custom defined

    Button btnFetch;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    ArrayList<Post> postList = new ArrayList<>();
    MyAdapter adapter;
    // Removed 'private Object id;' - R.id is automatically available from your R file

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Correct way to set content view from layout XML
        setContentView(R.layout.activity_main);

        // Correct way to find views using R.id
        btnFetch = findViewById(R.id.btnFetch);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter here as well, so it's not null before the first fetch
        // and data can be set even if the list is initially empty.
        adapter = new MyAdapter(postList);
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        // Set up the click listener for the button
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData();
            }
        });
        // Or using a lambda expression (which you had, but with syntax correction):
        // btnFetch.setOnClickListener(view -> fetchData());
    }

    private void fetchData() {
        String url = "https://jsonplaceholder.typicode.com/posts";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("API Response", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        postList.clear(); // Clear existing data before adding new

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            int userId = obj.getInt("userId");
                            int id = obj.getInt("id");
                            String title = obj.getString("title");
                            String body = obj.getString("body");

                            postList.add(new Post(userId, id, title, body));
                        }
                        // Notify the adapter that the data set has changed
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JSON Error", e.toString());
                    }
                },
                error -> Log.e("Volley Error", error.toString()));

        requestQueue.add(stringRequest);
    }
}