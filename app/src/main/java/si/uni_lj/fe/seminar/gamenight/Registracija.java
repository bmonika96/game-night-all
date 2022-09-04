package si.uni_lj.fe.seminar.gamenight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registracija extends AppCompatActivity {
    EditText uporabnisko_ime;
    EditText geslo;
    GamenightApi gamenightApi;
    EditText ponovi_geslo;
    EditText ime;
    EditText priimek;
    EditText email;
    Button registracija;

    public static int sheight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registracija);
        uporabnisko_ime=findViewById(R.id.uporabnisko_ime);
        geslo=findViewById(R.id.geslo);
        email=findViewById(R.id.email);
        ponovi_geslo=findViewById(R.id.ponovi_geslo);
        ime=findViewById(R.id.ime);
        priimek=findViewById(R.id.priimek);
        gamenightApi = APIClient.getClient().create(GamenightApi.class);
        registracija = findViewById(R.id.registracija);

    }
    private void registerUser(String uporabnisko_ime, String ime, String priimek, String geslo, String email) {
        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setUser(ime, priimek, uporabnisko_ime, geslo, email);
        Call<String> call = gamenightApi.registracija(uporabnik);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                Toast.makeText(Registracija.this, "Registracija uspešna", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Registracija.this, Prijava.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Login", "Login data wrong");
                Toast.makeText(Registracija.this, "Nekaj je šlo narobe", Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    protected void onStart() {
        Log.d("tag", "printing");
        super.onStart();
        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // on below line we are getting data from our edit text.
                String uporabnisko_ime_string = uporabnisko_ime.getText().toString();
                String geslo_string = geslo.getText().toString();
                String ponovi_geslo_string = ponovi_geslo.getText().toString();
                String ime_string = ime.getText().toString();
                String priimek_string = priimek.getText().toString();
                String email_string = email.getText().toString();

                if(!geslo_string.equals(ponovi_geslo_string)) {
                    Toast.makeText(Registracija.this, "Gesli se ne ujemata", Toast.LENGTH_SHORT).show();
                    return;
                }
                ;
                // checking if the entered text is empty or not.
                if (TextUtils.isEmpty(uporabnisko_ime_string) && TextUtils.isEmpty(geslo_string)) {
                    Toast.makeText(Registracija.this, "Vpišite podatke za prijavo", Toast.LENGTH_SHORT).show();
                }

                // calling a method to login our user.
                registerUser(uporabnisko_ime_string, ime_string, priimek_string, geslo_string, email_string);
            }
        });

    }

    public void odpriPrijava(View view) {
        Intent intent = new Intent(Registracija.this, Prijava.class);
        startActivity(intent);
    }

}
