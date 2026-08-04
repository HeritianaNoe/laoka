package com.lakabe.laoka.gs.adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.dialog.UpdateFadyFragment;
import com.lakabe.laoka.gs.model.Fady;
import com.lakabe.laoka.gs.interfaces.FadyListener;
import com.lakabe.laoka.gs.utils.WordUtils;

import java.util.List;

public class FadyAdapter extends RecyclerView.Adapter<FadyAdapter.FadyViewHolder>{
    private FragmentActivity activity;
    private List<Fady> fadies;
    private FadyListener listener;

    public FadyAdapter(FragmentActivity activity, List<Fady> fadies, FadyListener l){
        this.listener = l;
        this.activity = activity;
        this.fadies = fadies;
    }

    public class FadyViewHolder extends RecyclerView.ViewHolder{
        TextView name_fady;
        ImageView btn_suppr, btn_edit;

        public FadyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_fady = itemView.findViewById(R.id.name_fady);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_suppr = itemView.findViewById(R.id.btn_suppr);

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fady f = fadies.get(getAdapterPosition());

                    UpdateFadyFragment dialog = UpdateFadyFragment.getInstance(f, getAdapterPosition(), new onFadyListener());
                    dialog.show(activity.getSupportFragmentManager(), "EDIT");
                }
            });

            btn_suppr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fady f = fadies.get(getAdapterPosition());

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                    alertDialogBuilder.setTitle("FAMPITANDREMANA!");
                    alertDialogBuilder.setMessage("Tena ho vonoina marina ve?");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Eny", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            if(fadies.remove(f)){
                                notifyItemRemoved(getAdapterPosition());
                                listener.onDelete(f, getAdapterPosition(), view);
                            }
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Tsia", (dialog, which) -> {

                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        }
    }

    @NonNull
    @Override
    public FadyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FadyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fady, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FadyViewHolder holder, int position) {
        String name = fadies.get(position).getName();
        holder.name_fady.setText(WordUtils.readMore(name));
        holder.btn_edit.setImageResource(R.drawable.ic_mode_edit_black_24dp);
        holder.btn_suppr.setImageResource(R.drawable.ic_delete_white_24dp);
    }

    @Override
    public int getItemCount() {
        return fadies.size();
    }

    private class onFadyListener implements FadyListener {

        @Override
        public void onInsert(Fady fady, int pos,  View view) {

        }

        @Override
        public void onUpdate(Fady fady, int pos,  View view) {
            fadies.set(pos, fady);
            notifyItemChanged(pos);
            listener.onUpdate(fady, pos, view);
        }

        @Override
        public void onDelete(Fady fady, int pos,  View view) {

        }
    }

}
