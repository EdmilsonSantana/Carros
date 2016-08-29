package br.com.livroandroid.carros.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.domain.Carro;

/**
 * Created by Edmilson on 12/08/2016.
 */
public class CarroAdapter extends RecyclerView.Adapter<CarroAdapter.CarrosViewHolder> {

    protected static final String TAG = "livroandroid";

    private final Context context;

    private final List<Carro> carros;

    private CarroOnClickListener carroOnClickListener;

    public CarroAdapter(CarroOnClickListener carroOnClickListener, Context context, List<Carro> carros) {
        this.carroOnClickListener = carroOnClickListener;
        this.context = context;
        this.carros = carros;
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if ( this.carros != null ) {
            size = carros.size();
        }
        return size;
    }

    @Override
    public CarroAdapter.CarrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_carro, parent, false);
        CarrosViewHolder holder = new CarrosViewHolder(view);
        Log.i(TAG, "Holder criado");
        return holder;
    }

    @Override
    public void onBindViewHolder(final CarrosViewHolder holder, final int position) {

        Carro carro = carros.get(position);
        Log.i(TAG, "Bind");
        holder.tNome.setText(carro.getNome());
        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.with(context).load(carro.getUrlFoto()).fit().into(holder.img,
                new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });

        if ( carroOnClickListener != null ) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carroOnClickListener.onClickCarro(holder.itemView, position);
                }
            });
        }
    }

    public interface CarroOnClickListener {
        public void onClickCarro(View view, int index);
    }

    public static class CarrosViewHolder extends RecyclerView.ViewHolder {

        public TextView tNome;
        ImageView img;
        ProgressBar progressBar;
        CardView cardView;

        public CarrosViewHolder(View view) {
            super(view);
            tNome = (TextView) view.findViewById(R.id.text);
            img = (ImageView) view.findViewById(R.id.img);
            progressBar = (ProgressBar) view.findViewById(R.id.progressImg);
            cardView  = (CardView) view.findViewById(R.id.card_view);
        }
    }
}
