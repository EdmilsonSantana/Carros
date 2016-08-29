package br.com.livroandroid.carros.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.util.List;

import br.com.livroandroid.carros.CarrosApplication;
import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.activity.CarroActivity;
import br.com.livroandroid.carros.adapter.CarrosAdapter;
import br.com.livroandroid.carros.domain.Carro;
import br.com.livroandroid.carros.domain.CarroService;
import br.com.livroandroid.carros.utils.IOUtils;

public class CarrosFragment extends BaseFragment {

    protected RecyclerView recyclerView;

    private int tipo;

    private List<Carro> carros;

    private ProgressDialog dialog;

    public static CarrosFragment newInstance(int tipo) {
        Bundle bundle = new Bundle();
        bundle.putInt("tipo", tipo);
        CarrosFragment carrosFragment = new CarrosFragment();
        carrosFragment.setArguments(bundle);
        return carrosFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( getArguments() != null ) {
            this.tipo = getArguments().getInt("tipo");
        }
        CarrosApplication.getInstance().getBus().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carros, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(Boolean.TRUE);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskCarros();

    }

    private void taskCarros() {
       new GetCarrosTask().execute();
    }

    private CarrosAdapter.CarroOnClickListener onCLickCarro() {
        return new CarrosAdapter.CarroOnClickListener() {

            @Override
            public void onClickCarro(View view, int index) {
                Carro carro = carros.get(index);
                Intent intent = new Intent(getContext(), CarroActivity.class);
                intent.putExtra("carro", carro);
                startActivity(intent);
            }
        };
    }

    private class GetCarrosTask extends AsyncTask<Void, Void, List<Carro>> {
        @Override
        protected void onPreExecute() {
            if ( !IOUtils.isNetworkAvailable(getContext())) {
                cancel(true);
            }
        }

        @Override
        protected List<Carro> doInBackground(Void... params) {
            try {
                return CarroService.getCarros(getContext(), tipo);
            } catch (IOException e) {
                toast("Erro: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Carro> carros) {
            if ( carros != null) {
                CarrosFragment.this.carros = carros;
                recyclerView.setAdapter(new CarrosAdapter(onCLickCarro(), getContext(), carros ));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CarrosApplication.getInstance().getBus().unregister(this);
    }

    @Subscribe
    public void onBusAtualizarListaCarros(String refresh) {
        taskCarros();
    }
}
