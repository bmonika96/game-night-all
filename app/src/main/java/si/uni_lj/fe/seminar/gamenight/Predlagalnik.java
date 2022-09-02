package si.uni_lj.fe.seminar.gamenight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Predlagalnik extends AppCompatActivity {

    GamenightApi gamenightApi;
    TextView rezultat_igra;
    TextView stevilka ;
    JSONArray igre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.predlagalnik);
        rezultat_igra = findViewById(R.id.rezultat_igra);
        stevilka = findViewById(R.id.stevilka);
        gamenightApi = APIClient.getClient().create(GamenightApi.class);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Call<String> call = gamenightApi.getIgre("admin_monika", "YWRtaW5fbW9uaWthOmFkbWlu");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                String result = response.body();
                try {
                    assert result != null;
                    igre = new JSONArray(result);
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
    public void predlagajIgro(View view) {
        Random rand = new Random();
        int n = rand.nextInt(igre.length()-1)+1; // vrne med 0 length -1
        try {
            JSONObject randomIgraObj = igre.getJSONObject(n);
            rezultat_igra.setText(randomIgraObj.getString("ime_igre"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void vrziKocko(View view) {
        Random rand = new Random();
        Log.d("stevilka", "pride v funkcijo");
        int n = rand.nextInt(6)+1; // vrne med 0 5
        Log.d("stevilka", String.valueOf(n));
        stevilka.setText(String.valueOf(n));
    }
}


