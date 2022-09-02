package si.uni_lj.fe.seminar.gamenight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IgraPodatki extends AppCompatActivity {

    ImageView photo;
    GamenightApi gamenightApi;
    TextView ime_igre;
    TextView ocena ;
    TextView tezavnost;
    TextView min_st_igralcev;
    TextView max_st_igralcev;
    TextView dolzina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.igra_podatki);
        photo = findViewById(R.id.photo);
        ime_igre = findViewById(R.id.ime_igre);
        ocena = findViewById(R.id.ocena);
        min_st_igralcev = findViewById(R.id.min_st_igralcev);
        max_st_igralcev = findViewById(R.id.max_st_igralcev);
        tezavnost = findViewById(R.id.tezavnost);
        dolzina = findViewById(R.id.dolzina_igre);
        }
    @Override
    protected void onStart() {
        super.onStart();

        try {
            JSONObject igra = new JSONObject(getIntent().getStringExtra("igra"));
            Log.d("igraaa", String.valueOf(igra));
            ime_igre.setText("Ime igre: "+igra.getString("ime_igre"));
            ocena.setText("Ocena: "+ igra.getString(  "ocena"));
            min_st_igralcev.setText("Minimalno število igralcev:"+ igra.getString("min_stevilo_igralcev"));
            max_st_igralcev.setText("Maksimalno število igralcev:"+igra.getString("max_stevilo_igralcev"));
            tezavnost.setText("Težavnost:"+igra.getString("tezavnost"));
            dolzina.setText("Dolžina igre: "+igra.getString("dolzina_igre"));

            Glide.with(this).load(igra.getString("slika_url")).into(photo);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void clickedIgre(MenuItem item){

    }
    public void clickedDogodki(MenuItem item){
        this.finish();
    }
    public void clickedDomov(MenuItem item){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        this.finish();
    }

    public void pojdiDodajIgro(View view) {
        Intent i = new Intent(this,DodajIgro.class);
        startActivity(i);
    }
}


