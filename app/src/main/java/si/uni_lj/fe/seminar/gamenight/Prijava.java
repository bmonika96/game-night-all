package si.uni_lj.fe.seminar.gamenight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Prijava extends AppCompatActivity {
        TextView uporabnisko_ime;
        TextView geslo;
        GamenightApi gamenightApi;
        Button login;


        public static int sheight;
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            Log.d("tag", "printing");
            super.onCreate(savedInstanceState);
            Log.d("tag", "printing");
            setContentView(R.layout.login);


            uporabnisko_ime=findViewById(R.id.uporabnisko_ime);
            geslo=findViewById(R.id.geslo);
            login=findViewById(R.id.login);
            gamenightApi = APIClient.getClient().create(GamenightApi.class);



        }
    private void loginUser(String userName, String password) {
        String token = "Basic " + Base64.encodeToString((userName + ":" + password).getBytes(), Base64.NO_WRAP);
        Call<LoginPodatki> call = gamenightApi.login(token);
        call.enqueue(new Callback<LoginPodatki>() {
            @Override
            public void onResponse(Call<LoginPodatki> call, Response<LoginPodatki> response) {
                LoginPodatki result = response.body();
                assert result != null && result.getToken() != null;
                SharedPreferences preferences = getSharedPreferences("cred", MODE_PRIVATE);
                preferences.edit().putString("cred", token).apply();
                preferences.edit().putString("uporabnisko_ime", userName).apply();
                Intent intent = new Intent(Prijava.this, MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<LoginPodatki> call, Throwable t) {
                Log.d("Login", "Login data wrong");

            }
        });



    }

        @Override
        protected void onStart() {
            Log.d("tag", "printing");
            super.onStart();
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // on below line we are getting data from our edit text.
                    String uporabnisko_ime_string = uporabnisko_ime.getText().toString();
                    String geslo_string = geslo.getText().toString();

                    // checking if the entered text is empty or not.
                    if (TextUtils.isEmpty(uporabnisko_ime_string) && TextUtils.isEmpty(geslo_string)) {
                        Toast.makeText(Prijava.this, "Vpišite podatke za prijavo", Toast.LENGTH_SHORT).show();
                    }

                    // calling a method to login our user.
                    loginUser(uporabnisko_ime_string, geslo_string);
                }
            });

        }

    public void odpriRegistracija(View view) {
        Intent intent = new Intent(Prijava.this, Registracija.class);
        startActivity(intent);
    }


}
