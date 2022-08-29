package si.uni_lj.fe.seminar.gamenight;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IgrePregled extends AppCompatActivity {
    public  MyAdapterIgre adapter;


    GamenightApi gamenightApi;


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
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
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
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

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

    public void pojdiDodajIgro(View view) {
        Intent i = new Intent(this,DodajIgro.class);
        startActivity(i);
    }
}

