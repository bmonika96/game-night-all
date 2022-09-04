package si.uni_lj.fe.seminar.gamenight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogodkiPregled extends AppCompatActivity {
    private static MyAdapterDogodki adapter;
    GamenightApi gamenightApi;
    String token;
    private static Context context;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("cred", MODE_PRIVATE);
        String tokenSP = preferences.getString("cred","");
        String usernameSP = preferences.getString("uporabnisko_ime","");
        if(Objects.equals(tokenSP, "")) {
            Log.d("cred", "token is null");
            Intent intent = new Intent(DogodkiPregled.this, Prijava.class);
            startActivity(intent);
        }
        else {
            token = tokenSP;
            username = usernameSP;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogodki);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        gamenightApi = APIClient.getClient().create(GamenightApi.class);
        adapter = new MyAdapterDogodki(getApplicationContext(), new JSONArray());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<String> call = gamenightApi.getDogodki(username, token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                try {
                    assert result != null;
                    JSONArray dogodki = new JSONArray(result);
                    adapter.setData(dogodki);
                    Log.d("igre", String.valueOf(dogodki));


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

    public void pojdiDodajDogodek(View view) {
        Intent intent = new Intent(DogodkiPregled.this, DodajDogodek.class);
        startActivity(intent);
    }
}
