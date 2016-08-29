package br.com.livroandroid.carros.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.domain.Carro;
import br.com.livroandroid.carros.fragments.CarroFragment;
import br.com.livroandroid.carros.fragments.CarrosFragment;

public class CarroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        setUpToolbar();

        Carro carro = (Carro) getIntent().getParcelableExtra("carro");
        getSupportActionBar().setTitle(carro.getNome());
        getSupportActionBar().setDisplayHomeAsUpEnabled(Boolean.TRUE);
        ImageView appBarImg = (ImageView) findViewById(R.id.appBarImg);
        Picasso.with(getContext()).load(carro.getUrlFoto()).into(appBarImg);
        if ( savedInstanceState == null) {
            CarroFragment fragment = new CarroFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.CarroFragment, fragment).commit();
        }
    }
}
