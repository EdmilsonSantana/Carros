package br.com.livroandroid.carros.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.fragments.CarrosFragment;

/**
 * Created by Edmilson on 13/08/2016.
 */
public class TabsAdapter extends FragmentPagerAdapter{

    private Context context;

    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.classicos);
        } else if (position == 1) {
            return context.getString(R.string.esportivos);
        } else if ( position == 2) {
            context.getString(R.string.luxo);
        }
        return context.getString(R.string.favoritos);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        if (position == 0) {
            f = CarrosFragment.newInstance(R.string.classicos);
        } else if (position == 1) {
            f = CarrosFragment.newInstance(R.string.esportivos);
        } else if(position == 2) {
            f = CarrosFragment.newInstance(R.string.luxo);
        } else {
            f = CarrosFragment.newInstance(R.string.favoritos);
        }
        return f;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
