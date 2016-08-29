package br.com.livroandroid.carros;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.util.List;

import br.com.livroandroid.carros.domain.Carro;
import br.com.livroandroid.carros.domain.CarroService;

/**
 * Created by EdmilsonS on 14/08/2016.
 */
public class CarroServiceTest extends AndroidTestCase {
    public void testGetCarros() throws IOException {
        List<Carro> carros = CarroService.getCarros(getContext(), R.string.esportivos);
        assertNotNull(carros);

        assertTrue(carros.size() == 10);

        Carro c0 = carros.get(0);
        assertEquals("Ferrari FF", c0.getNome());
        assertEquals("44.532218", c0.getLatitude());
        assertEquals("10.864019", c0.getLongitude());

        Carro c9 = carros.get(9);
        assertEquals("MERCEDES-BENZ C63 AMG", c9.getNome());
        assertEquals("-23.564224", c9.getLatitude());
        assertEquals("-46.653156", c9.getLongitude());
    }
}
