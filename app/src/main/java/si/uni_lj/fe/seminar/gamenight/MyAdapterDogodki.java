package si.uni_lj.fe.seminar.gamenight;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

public class MyAdapterDogodki extends RecyclerView.Adapter<MyAdapterDogodki.MyViewHolder> {
    private JSONArray dogodki;


    public void setData(JSONArray dogodki) {
        this.dogodki = dogodki;
        notifyDataSetChanged();
    }

    public MyAdapterDogodki(Context context, JSONArray dogodki) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.dogodki = dogodki;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewIme;
        TextView textViewDatum;
        TextView textViewIgra;
        TextView textViewZmagovalec;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewIme = itemView.findViewById(R.id.title);
            this.textViewDatum = itemView.findViewById(R.id.datum);
            this.textViewIgra = itemView.findViewById(R.id.odigrana_igra);
            this.textViewZmagovalec = itemView.findViewById(R.id.zmagovalec);
        }
    }
    private final LayoutInflater mInflater;
    private final Context mContext;

    @Override
    public MyAdapterDogodki.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dogodek_card, parent, false);


        MyAdapterDogodki.MyViewHolder myViewHolder = new MyAdapterDogodki.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyAdapterDogodki.MyViewHolder holder, final int listPosition) {

        if(this.dogodki != null) {
            try {
                if(dogodki.getJSONObject(listPosition) != null) {
                    TextView textViewIme = holder.textViewIme;
                    TextView textViewDatum = holder.textViewDatum;
                    TextView textViewIgra = holder.textViewIgra;
                    TextView textViewZmagovalec = holder.textViewZmagovalec;


                    try {
                        textViewIme.setText("Ime skupine: "+ dogodki.getJSONObject(listPosition).getString("ime_skupine"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        textViewDatum.setText("Datum: " + dogodki.getJSONObject(listPosition).getString("datum"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        textViewIgra.setText("Odigrana igra: "+dogodki.getJSONObject(listPosition).getString("odigrana_igra"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        textViewZmagovalec.setText("Zmagovalec: " + dogodki.getJSONObject(listPosition).getString("zmagovalec"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    holder.textViewIme.setText("ni skupine");
                    holder.textViewDatum.setText("ni datuma");
                    holder.textViewIgra.setText("ni skupine");
                    holder.textViewZmagovalec.setText("ni datuma");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else {
            Log.e("spet neki", "spet");
        }
    }

    @Override
    public int getItemCount() {
        if(dogodki != null) {
            return dogodki.length();
        }
        else {
            return -1;

        }
    }
}
