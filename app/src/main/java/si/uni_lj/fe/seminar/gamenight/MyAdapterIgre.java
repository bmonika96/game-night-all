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

public class MyAdapterIgre extends RecyclerView.Adapter<MyAdapterIgre.MyViewHolder> {

    private JSONArray igre;


    public void setData(JSONArray igre) {
        this.igre = igre;
        notifyDataSetChanged();
    }

    public MyAdapterIgre(Context context, JSONArray igre) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.igre = igre;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.title);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.ocena);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.photo);
        }
    }
    private final LayoutInflater mInflater;
    private Context mContext;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.igra_card, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        String word = "";
        if(this.igre != null) {
            try {
                if(igre.getJSONObject(listPosition) != null) {
                    TextView textViewName = holder.textViewName;
                    TextView textViewVersion = holder.textViewVersion;
                    ImageView imageView = holder.imageViewIcon;

                    try {
                        textViewName.setText(igre.getJSONObject(listPosition).getString("ime_igre"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        textViewVersion.setText(igre.getJSONObject(listPosition).getString("ocena"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Glide.with(this.mContext).load(igre.getJSONObject(listPosition).getString("slika_url")).into(imageView);

                }
                else {
                    holder.textViewName.setText("nothing");
                    holder.imageViewIcon.setImageResource(R.drawable.ozadje);
                    holder.textViewVersion.setText("ocena");
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
        if(igre != null) {
            return igre.length();
        }
        else {
            return -1;

        }
    }

}