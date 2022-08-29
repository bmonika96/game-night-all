package si.uni_lj.fe.seminar.gamenight;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DodajIgro extends AppCompatActivity {
    EditText ime_igre;
    EditText ocena;
    EditText tezavnost;
    EditText min_stevilo_igralcev;
    EditText max_stevilo_igralcev;
    EditText dolzina_igre;
    EditText slika_url;
    GamenightApi gamenightApi;

    public static int sheight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("tag", "printing");
        super.onCreate(savedInstanceState);
        Log.d("tag", "printing");
        setContentView(R.layout.activity_dodaj_igro);


        ime_igre=findViewById(R.id.ime_igre);
        ocena=findViewById(R.id.ocena);
        tezavnost=findViewById(R.id.tezavnost);
        min_stevilo_igralcev = findViewById(R.id.min_stevilo_igralcev);
        max_stevilo_igralcev=findViewById(R.id.max_stevilo_igralcev);
        dolzina_igre=findViewById(R.id.dolzina_igre);
        slika_url = findViewById(R.id.slika_url);
        gamenightApi = APIClient.getClient().create(GamenightApi.class);

    }

    @Override
    protected void onStart() {
        Log.d("tag", "printing");
        super.onStart();

    }
    public void dodajIgro(View view) {
        JSONObject igra = new JSONObject();
        try {
            igra.put("ime_igre", ime_igre.getText().toString());
            igra.put("ocena", ocena.getText().toString());
            igra.put("tezavnost", tezavnost.getText().toString());
            igra.put("min_stevilo_igralcev", min_stevilo_igralcev.getText().toString());
            igra.put("max_stevilo_igralcev", max_stevilo_igralcev.getText().toString());
            igra.put("dolzina_igre", dolzina_igre.getText().toString());
            igra.put("slika_url", slika_url.getText().toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Call<ResponseBody> call = gamenightApi.dodajIgro("admin_monika", igra);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("add", "igra dodana");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


}

