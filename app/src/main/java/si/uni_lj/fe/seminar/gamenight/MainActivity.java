package si.uni_lj.fe.seminar.gamenight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    TextView datum;
    GamenightApi gamenightApi;

    public static int sheight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("cred", MODE_PRIVATE);
        Log.d("token", preferences.getString("cred",""));
        String token = preferences.getString("cred","");
        if(Objects.equals(token, "")) {
            Log.d("cred", "token is null");
            Intent intent = new Intent(MainActivity.this, Prijava.class);
            startActivity(intent);
        }


        Log.d("tag", "printing");
        super.onCreate(savedInstanceState);
        Log.d("tag", "printing");
        setContentView(R.layout.activity_main);


        datum=findViewById(R.id.profil_datum);
        datum.setText(danesDatum());
        gamenightApi = APIClient.getClient().create(GamenightApi.class);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    //pridobimo današnji datum
    private String danesDatum() {
        Calendar cal = Calendar.getInstance();
        int leto = cal.get(Calendar.YEAR);
        int mesec = cal.get(Calendar.MONTH);
        mesec = mesec + 1; // gre samo od 0 do 11
        int dan = cal.get(Calendar.DAY_OF_MONTH);
        return dan+"."+mesec+"."+leto;
    }


    public void odpriIgre(View view) {
        Intent intent = new Intent(MainActivity.this, IgrePregled.class);
        startActivity(intent);
    }
    public void odpriDogodki(View view) {
        Intent intent = new Intent(MainActivity.this, DogodkiPregled.class);
        startActivity(intent);
    }
    public void pojdiPredlagalnik(View view) {
        Intent intent = new Intent(MainActivity.this, Predlagalnik.class);
        startActivity(intent);
    }
    public void odjava(View view){
        SharedPreferences preferences = getSharedPreferences("cred", 0);
        preferences.edit().remove("cred").apply();
        Intent intent = new Intent(this, Prijava.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        startActivity(intent);
        finish();

    }

}