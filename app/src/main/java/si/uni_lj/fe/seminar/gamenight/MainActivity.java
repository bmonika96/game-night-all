package si.uni_lj.fe.seminar.gamenight;

import android.content.Intent;
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



public class MainActivity extends AppCompatActivity {

    TextView datum;
    TextView igreField;
    GamenightApi gamenightApi;

    public static int sheight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Log.d("tag", "printing");
        super.onStart();
        Call<String> call = gamenightApi.getIgre("admin_monika", "YWRtaW5fbW9uaWthOmFkbWlu");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                try {
                    assert result != null;
                    JSONArray jArray = new JSONArray(result);
                    Log.d("object", "jobject");
                    JSONObject oneObject = jArray.getJSONObject(1);
                    Log.d("get1ST", "1ST");
                    String oneObjectsItem = oneObject.getString("ime_igre");
                    Log.d("ime", oneObjectsItem);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

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

   // public void odpri_nov_vnos_dogodki(View view) {
     //   Intent intent = new Intent(this, NovVnosDogodek.class);
      //  startActivity(intent);
    //}
}