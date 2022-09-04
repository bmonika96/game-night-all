package si.uni_lj.fe.seminar.gamenight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class DodajDogodek extends AppCompatActivity{
    EditText ime_skupine;
    EditText datum;
    EditText zmagovalec;
    GamenightApi gamenightApi;
    JSONArray igre;
    ArrayList<String> imena = new ArrayList<String>();
    Spinner spinner;
    ArrayAdapter<String> adapter;
    String token;

    public static int sheight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("tag", "printing");
        super.onCreate(savedInstanceState);
        Log.d("tag", "printing");
        setContentView(R.layout.activity_dodaj_dogodek);
        SharedPreferences preferences = getSharedPreferences("cred", MODE_PRIVATE);
        String tokenSP = preferences.getString("cred","");
        if(Objects.equals(tokenSP, "")) {
            Log.d("cred", "token is null");
            Intent intent = new Intent(DodajDogodek.this, Prijava.class);
            startActivity(intent);
        }
        else {
            token = tokenSP;
        }


        ime_skupine=findViewById(R.id.ime_skupine);
        datum=findViewById(R.id.datum);
        zmagovalec = findViewById(R.id.zmagovalec);
        gamenightApi = APIClient.getClient().create(GamenightApi.class);
        spinner =  (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, imena);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int selectionCurrent = spinner.getSelectedItemPosition();
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (selectionCurrent != position){
                    // Your code here
                }
                selectionCurrent= position;
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Your code here
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d("tag", "printing");
        super.onStart();
        Call<String> call = gamenightApi.getIgre("admin_monika", token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                String result = response.body();
                try {
                    assert result != null;
                    igre = new JSONArray(result);
                    imena.add(0, "Izberi odigrano igro");
                    for (int index = 0; index < igre.length(); index++) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = igre.getJSONObject(index);
                            String ime = jsonObject.getString("ime_igre");
                            imena.add(ime);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("igre", String.valueOf(imena));

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


    public void dodajDogodek(View view) {
    Dogodek dogodek = new Dogodek();
    dogodek.setDogodek(ime_skupine.getText().toString(), datum.getText().toString(),String.valueOf(spinner.getSelectedItem()),zmagovalec.getText().toString());
    Log.d("dogodek", String.valueOf(dogodek));
    Call<ResponseBody> call = gamenightApi.dodajDogodek("admin_monika",dogodek, "YWRtaW5fbW9uaWthOmFkbWlu");
    call.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            Log.d("add", "dogodek dodan");
            Intent i = new Intent(DodajDogodek.this,DogodkiPregled.class);
            startActivity(i);
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    });

}
}
