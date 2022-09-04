package si.uni_lj.fe.seminar.gamenight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyAdapterIgre extends RecyclerView.Adapter<MyAdapterIgre.MyViewHolder> {

    private JSONArray igre;
    private final IgrePregled.OnItemClickListener listener;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(JSONArray igre) {
        this.igre = igre;

        notifyDataSetChanged();
    }

    public MyAdapterIgre(Context context, JSONArray igre, IgrePregled.OnItemClickListener listener) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        this.listener = listener;
        this.mContext = context;
        this.igre = igre;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.title);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.photo);
        }

        public void bind(JSONObject jsonObject, IgrePregled.OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(jsonObject));
        }
    }

    private final Context mContext;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.igra_card, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int listPosition) {
        if(this.igre != null) {
            try {
                if(igre.getJSONObject(listPosition) != null) {
                    TextView textViewName = holder.textViewName;
                    ImageView imageView = holder.imageViewIcon;

                    try {
                        textViewName.setText(igre.getJSONObject(listPosition).getString("ime_igre"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Glide.with(this.mContext).load(igre.getJSONObject(listPosition).getString("slika_url")).into(imageView);

                }
                else {
                    holder.textViewName.setText(R.string.problem_dogodki_pregled);
                    holder.imageViewIcon.setImageResource(R.drawable.ozadje);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else {
            Log.e("tezava", "nekaj je slo narobe");
        }
        try {
            holder.bind(igre.getJSONObject(listPosition), listener);
        } catch (JSONException e) {
            e.printStackTrace();
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