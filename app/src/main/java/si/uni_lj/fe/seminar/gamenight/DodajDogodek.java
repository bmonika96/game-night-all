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

public class DodajDogodek extends AppCompatActivity {
    EditText ime_skupine;
    EditText datum;
    EditText odigrana_igra;
    EditText zmagovalec;
    GamenightApi gamenightApi;

    public static int sheight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("tag", "printing");
        super.onCreate(savedInstanceState);
        Log.d("tag", "printing");
        setContentView(R.layout.activity_dodaj_dogodek);


        ime_skupine=findViewById(R.id.ime_skupine);
        datum=findViewById(R.id.datum);
        odigrana_igra=findViewById(R.id.odigrana_igra);
        zmagovalec = findViewById(R.id.zmagovalec);
        gamenightApi = APIClient.getClient().create(GamenightApi.class);

    }

    @Override
    protected void onStart() {
        Log.d("tag", "printing");
        super.onStart();

    }
    public void dodajDogodek(View view) {
    JSONObject dogodek = new JSONObject();
    try {
        dogodek.put("ime_skupine", ime_skupine.getText().toString());
        dogodek.put("datum", datum.getText().toString());
        dogodek.put("odigrana_igra", odigrana_igra.getText().toString());
        dogodek.put("zmagovalec", zmagovalec.getText().toString());

    } catch (JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    Call<ResponseBody> call = gamenightApi.dodajDogodek("admin_monika", dogodek);
    call.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            Log.d("add", "dogodek dodan");
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    });

}


}
