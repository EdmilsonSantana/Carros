package br.com.livroandroid.carros.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.livroandroid.carros.R;

public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;

    protected void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected void setUpNavDrawer() {
        final ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(Boolean.TRUE);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if ( navigationView != null && drawerLayout != null ) {
            setNavViewValues(navigationView, R.string.nav_drawer_username,
                    R.string.nav_drawer_email, R.mipmap.ic_launcher);

            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem item) {
                            item.setChecked(Boolean.TRUE);

                            drawerLayout.closeDrawers();

                            onNavDrawerItemSelected(item);

                            return false;
                        }
                    }
            );
        }
    }


    public static void setNavViewValues(NavigationView navView, int nome, int email, int img) {
        View headerView = navView.getHeaderView(0);
        TextView tNome = (TextView) headerView.findViewById(R.id.tNome);
        TextView tEmail = (TextView) headerView.findViewById(R.id.tEmail);
        ImageView imgView = (ImageView) headerView.findViewById(R.id.img);
        tNome.setText(nome);
        tEmail.setText(email);
        imgView.setImageResource(img);
    }


    private void onNavDrawerItemSelected(MenuItem menuItem) {
        Intent intent = new Intent(getContext(), CarrosActivity.class);
        switch ( menuItem.getItemId() ) {
            case R.id.nav_item_carros_todos:
                toast("Clicou em carros");
                break;
            case R.id.nav_item_carros_classicos:
                intent.putExtra("tipo", R.string.classicos);
                startActivity(intent);
                break;
            case R.id.nav_item_carros_esportivos:
                intent.putExtra("tipo", R.string.esportivos);
                startActivity(intent);
                break;
            case R.id.nav_item_carros_luxos:
                intent.putExtra("tipo", R.string.luxo);
                startActivity(intent);
                break;
            case R.id.nav_item_site_livro:
                startActivity(new Intent(getContext(), SiteLivroActivity.class));
                break;
            case R.id.nav_item_settings:
                toast("Clicou em configurações");
                break;
        }
    }

    protected Context getContext() {
        return this;
    }

    protected void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
            case android.R.id.home:
                if ( drawerLayout != null ) {
                    openDrawner();
                    return Boolean.TRUE;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void openDrawner() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    protected void closeDrawner() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    protected void snack(View view, String msg) {
        this.snack(view, msg, (Runnable) null);
    }

    protected void snack(View view, String msg, final Runnable runnable) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener() {
            public void onClick(View v) {
                if (runnable != null) {
                    runnable.run();
                }
            }
        }).show();
    }
}
