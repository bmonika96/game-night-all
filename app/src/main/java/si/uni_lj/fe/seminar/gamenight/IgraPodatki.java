package si.uni_lj.fe.seminar.gamenight;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;

public class IgraPodatki extends AppCompatActivity {

    ImageView photo;
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
            ime_igre.setText(String.format("%s%s", getString(R.string.igra_podatki_ime), igra.getString("ime_igre")));
            ocena.setText(String.format("%s%s", getString(R.string.igra_podatki_ocena), igra.getString("ocena")));
            min_st_igralcev.setText(String.format("%s%s", getString(R.string.igra_podatki_min_igr), igra.getString("min_stevilo_igralcev")));
            max_st_igralcev.setText(String.format("%s%s", getString(R.string.igra_podatki_max_igr), igra.getString("max_stevilo_igralcev")));
            tezavnost.setText(String.format("%s%s", getString(R.string.igra_podatki_tezavnost), igra.getString("tezavnost")));
            dolzina.setText(String.format("%s%s", getString(R.string.igra_podatki_slika_url), igra.getString("dolzina_igre")));

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


