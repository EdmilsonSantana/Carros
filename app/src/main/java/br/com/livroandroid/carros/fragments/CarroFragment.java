package br.com.livroandroid.carros.fragments;


import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.livroandroid.carros.CarrosApplication;
import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.domain.Carro;
import br.com.livroandroid.carros.domain.CarroDB;


public class CarroFragment extends BaseFragment {

    private Carro carro;
    private FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro, container, false);

        carro = (Carro) getArguments().getParcelable("carro");

        TextView tDesc = (TextView) view.findViewById(R.id.tDesc);
        tDesc.setText(carro.getDesc());

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTask( taskFavoritar());

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startTask(checkFavorito());
    }

    private TaskListener<Boolean> checkFavorito() {
        return new TaskListener<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
                CarroDB  db = new CarroDB(getContext());
                return db.exists(carro.getNome());
            }

            @Override
            public void updateView(Boolean exists) {
                setFabColor(exists);
            }
            @Override
            public void onError(Exception exception) { }
            @Override
            public void onCancelled(String cod) { }
        };
    }

    private TaskListener<Boolean> taskFavoritar() {
        return new TaskListener<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
                CarroDB db = new CarroDB(getContext());
                boolean exists = db.exists(carro.getNome());
                if ( !exists ) {
                    db.save(carro);
                    return true;
                }else {
                    db.delete(carro);
                    return false;
                }
            }

            @Override
            public void updateView(Boolean favoritou) {
                if(favoritou) {
                    toast("Carro adicionado aos favoritos");
                } else {
                    toast("Carro removido dos favoritos");
                }
                CarrosApplication.getInstance().getBus().post("refresh");

                setFabColor(favoritou);
            }

            @Override
            public void onError(Exception exception) { }
            @Override
            public void onCancelled(String cod) {}
        };
    }

    private void setFabColor(Boolean favorito) {
        if ( favorito ) {
            fab.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.yellow));
        } else {
            fab.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.accent));
        }

    }
}
