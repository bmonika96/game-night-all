package si.uni_lj.fe.seminar.gamenight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

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
    Button dodaj_igro;
    String token;
    String uporabnisko_ime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("cred", MODE_PRIVATE);
        String tokenSP = preferences.getString("cred","");
        String usernameSP = preferences.getString("uporabnisko_ime","");
        if(Objects.equals(tokenSP, "")) {
            Log.d("cred", "token is null");
            Intent intent = new Intent(DodajIgro.this, Prijava.class);
            startActivity(intent);
        }
        else {
            token = tokenSP;
            uporabnisko_ime = usernameSP;
        }
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
        dodaj_igro = findViewById(R.id.button_dodaj_igro);
        dodaj_igro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Igra igra = new Igra();
                igra.setIgra(
                        ime_igre.getText().toString(),
                        ocena.getText().toString(),
                        tezavnost.getText().toString(),
                        Integer.parseInt(min_stevilo_igralcev.getText().toString()),
                        Integer.parseInt(max_stevilo_igralcev.getText().toString()),
                        dolzina_igre.getText().toString(),
                        slika_url.getText().toString());
                Log.d("igra", String.valueOf(igra));
                Call<ResponseBody> call = gamenightApi.dodajIgro(uporabnisko_ime,igra, token);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("add", "igra dodana");
                        Intent intent = new Intent(DodajIgro.this, IgrePregled.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d("tag", "printing");
        super.onStart();

    }
}

