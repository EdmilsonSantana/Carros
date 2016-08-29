package br.com.livroandroid.carros.domain;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.utils.FileUtils;
import br.com.livroandroid.carros.utils.XMLUtils;
import livroandroid.lib.utils.HttpHelper;


/**
 * Created by Edmilson on 12/08/2016.
 */
public class CarroService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "CarroService";
    private static final String URL = "http://www.livroandroid.com.br/livro/carros/carros_{tipo}.json";

    public static List<Carro> getCarros(Context context, int tipo) throws IOException {
        List<Carro> carros = null;
        if ( tipo == R.string.favoritos ) {
            CarroDB db = new CarroDB(context);
            carros = db.findAll();
        } else {
            String url = URL.replace("{tipo}", getTipo(tipo));
            HttpHelper httpHelper = new HttpHelper();
            String json = httpHelper.doGet(url);
            carros = parserJSON(json);
        }
        return carros;
    }

    private static String getTipo(int tipo) {
        if (tipo == R.string.classicos) {
            return "classicos";
        } else if (tipo == R.string.esportivos) {
            return "esportivos";
        } else {
            return "luxo";
        }
    }



    private static List<Carro> parserJSON(String json) throws IOException {
        List<Carro> carros = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("carros");
            JSONArray jsonCarros = obj.getJSONArray("carro");

            for (int i = 0; i < jsonCarros.length(); i++) {
                JSONObject jsonCarro = jsonCarros.getJSONObject(i);
                Carro c = new Carro();
                c.setNome(jsonCarro.optString("nome"));
                c.setDesc(jsonCarro.optString("desc"));
                c.setUrlFoto(jsonCarro.optString("url_foto"));
                c.setUrlInfo(jsonCarro.optString("url_info"));
                c.setUrlVideo(jsonCarro.optString("url_video"));
                c.setLatitude(jsonCarro.optString("latitude"));
                c.setLongitude(jsonCarro.optString("longitude"));
                if (LOG_ON) {
                    Log.d(TAG, "Carro " + c.getNome() + " > " + c.getUrlFoto());
                }
                carros.add(c);
            }
            if (LOG_ON) {
                Log.d(TAG, carros.size() + " encontrados.");
            }
        } catch (JSONException ex) {
            throw new IOException(ex.getMessage(), ex);
        }
        return carros;
    }

    private static String readFile(Context context, int tipo) throws IOException {
        if (tipo == R.string.classicos) {
            return FileUtils.readRawFileString(context, R.raw.carros_classicos, "UTF-8");
        } else if (tipo == R.string.esportivos) {
            return FileUtils.readRawFileString(context, R.raw.carros_esportivos, "UTF-8");
        }
        return FileUtils.readRawFileString(context, R.raw.carros_luxo, "UTF-8");
    }

    private static List<Carro> parserXML(Context context, String xml) {
        List<Carro> carros = new ArrayList<>();

        Element root = XMLUtils.getRoot(xml, "UTF-8");
        List<Node> nodeCarros = XMLUtils.getChildren(root, "carro");

        for (Node node: nodeCarros) {
            Carro c = new Carro();

            c.setNome(XMLUtils.getText(node, "nome"));
            c.setDesc(XMLUtils.getText(node, "desc"));
            c.setUrlFoto(XMLUtils.getText(node, "url_foto"));
            c.setUrlInfo(XMLUtils.getText(node, "url_info"));
            c.setUrlVideo(XMLUtils.getText(node, "url_video"));
            c.setLatitude(XMLUtils.getText(node, "latitude"));
            c.setLongitude(XMLUtils.getText(node, "longitude"));
            if (LOG_ON) {
                Log.d(TAG, "Carro " + c.getNome() + " > " + c.getUrlFoto());
            }
            carros.add(c);
        }
        if (LOG_ON) {
            Log.d(TAG, carros.size() + " encontrados.");
        }
        return carros;
    }
}
