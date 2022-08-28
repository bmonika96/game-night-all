package si.uni_lj.fe.seminar.gamenight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IgrePregled extends AppCompatActivity {
    private static MyAdapterIgre adapter;


    GamenightApi gamenightApi;
    private static Context context;

    // Arraylist for storing data
    private List<Igra> courseModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igre);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        gamenightApi = APIClient.getClient().create(GamenightApi.class);
        adapter = new MyAdapterIgre(getApplicationContext(), new JSONArray());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<String> call = gamenightApi.getIgre("admin_monika", "YWRtaW5fbW9uaWthOmFkbWlu");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                try {
                    assert result != null;
                    JSONArray igre = new JSONArray(result);
                    adapter.setData(igre);
                    Log.d("igre", String.valueOf(igre));


                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                Log.d("object", "jobject");
                // JSONObject oneObject = jArray.getJSONObject(1);
                //String oneObjectsItem = oneObject.getString("ime_igre");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });




    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    public void clickedIgre(MenuItem item){

    }
    public void clickedDogodki(MenuItem item){
        this.finish();
    }
    public void clickedDomov(MenuItem item){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        this.finish();
    }

}

