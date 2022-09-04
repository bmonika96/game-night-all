package si.uni_lj.fe.seminar.gamenight;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IgrePregled extends AppCompatActivity {
    public  MyAdapterIgre adapter;
    String token;
    GamenightApi gamenightApi;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("cred", MODE_PRIVATE);
        String tokenSP = preferences.getString("cred","");
        String usernameSP = preferences.getString("uporabnisko_ime","");
        if(Objects.equals(tokenSP, "")) {
            Log.d("cred", "token is null");
            Intent intent = new Intent(IgrePregled.this, Prijava.class);
            startActivity(intent);
        }
        else {
            token = tokenSP;
            username = usernameSP;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igre);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);


        gamenightApi = APIClient.getClient().create(GamenightApi.class);
        adapter = new MyAdapterIgre(getApplicationContext(), new JSONArray(), item -> {
            Intent intent = new Intent(IgrePregled.this, IgraPodatki.class);
            intent.putExtra("igra",item.toString());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    @Override
    protected void onStart() {
        super.onStart();
        Call<String> call = gamenightApi.getIgre(username, token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                String result = response.body();
                try {
                    assert result != null;
                    JSONArray igre = new JSONArray(result);
                    adapter.setData(igre);

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
    public interface OnItemClickListener {
        void onItemClick(JSONObject item);
    }

    public void pojdiDodajIgro(View view) {
        Intent i = new Intent(this,DodajIgro.class);
        startActivity(i);
    }
}

