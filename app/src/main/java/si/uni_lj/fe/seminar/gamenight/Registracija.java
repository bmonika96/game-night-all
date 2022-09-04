package si.uni_lj.fe.seminar.gamenight;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                String result = response.body();
                if(result == null){
                    Toast.makeText(Registracija.this, "Uporabniško ime že obstaja", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(Registracija.this, "Registracija uspešna", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Registracija.this, Prijava.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d("Login", "Login data wrong");
                Toast.makeText(Registracija.this, "Nekaj je šlo narobe", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registracija.setOnClickListener(v -> {
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
            if (TextUtils.isEmpty(uporabnisko_ime_string) && TextUtils.isEmpty(geslo_string)) {
                Toast.makeText(Registracija.this, "Vpišite podatke za prijavo", Toast.LENGTH_SHORT).show();
            }
            registerUser(uporabnisko_ime_string, ime_string, priimek_string, geslo_string, email_string);
        });
    }
    public void odpriPrijava(View view) {
        Intent intent = new Intent(Registracija.this, Prijava.class);
        startActivity(intent);
    }
}
