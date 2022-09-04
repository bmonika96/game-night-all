package si.uni_lj.fe.seminar.gamenight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;

public class MyAdapterDogodki extends RecyclerView.Adapter<MyAdapterDogodki.MyViewHolder> {
    private JSONArray dogodki;


    @SuppressLint("NotifyDataSetChanged")
    public void setData(JSONArray dogodki) {
        this.dogodki = dogodki;
        notifyDataSetChanged();
    }

    public MyAdapterDogodki(Context context, JSONArray dogodki) {
        LayoutInflater mInflater = LayoutInflater.from(context);
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

    @NonNull
    @Override
    public MyAdapterDogodki.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dogodek_card, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapterDogodki.MyViewHolder holder, final int listPosition) {
        try {
            Log.d("g", String.valueOf(dogodki.getJSONObject(listPosition)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(this.dogodki != null) {
            try {
                if(dogodki.getJSONObject(listPosition) != null) {
                    TextView textViewIme = holder.textViewIme;
                    TextView textViewDatum = holder.textViewDatum;
                    TextView textViewIgra = holder.textViewIgra;
                    TextView textViewZmagovalec = holder.textViewZmagovalec;


                    try {
                        textViewIme.setText(String.format("Ime skupine: %s", dogodki.getJSONObject(listPosition).getString("ime_skupine")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        textViewDatum.setText(String.format("Datum: %s", dogodki.getJSONObject(listPosition).getString("datum")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        textViewIgra.setText(String.format("Odigrana igra: %s", dogodki.getJSONObject(listPosition).getString("odigrana_igra")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        textViewZmagovalec.setText(String.format("Zmagovalec: %s", dogodki.getJSONObject(listPosition).getString("zmagovalec")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    holder.textViewIme.setText(R.string.niskupine);
                    holder.textViewDatum.setText(R.string.nidatuma);
                    holder.textViewIgra.setText(R.string.niskupine);
                    holder.textViewZmagovalec.setText(R.string.nidatuma);
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
