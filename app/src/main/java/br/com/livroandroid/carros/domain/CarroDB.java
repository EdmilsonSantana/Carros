package br.com.livroandroid.carros.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EdmilsonS on 18/08/2016.
 */
public class CarroDB extends SQLiteOpenHelper {

    private static final String TAG = "sql";
    public static final String NOME_BANCO = "livro_android.sqlite";
    private static final int VERSAO_BANCO = 1;
    private static final String NOME_TABELA = "carro";

    public CarroDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a tabela carro...");
        StringBuilder builder = new StringBuilder();
        builder.append(" create table if not exists carro ");
        builder.append(" (_id integer primary key autoincrement, nome text, ");
        builder.append(" desc text, url_foto text, url_video text, latitude text, ");
        builder.append(" longitude text, tipo text); ");
        db.execSQL(builder.toString());
        Log.d(TAG, "Tabela carro criada com sucesso.");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public long save(Carro carro) {
        long id = carro.getId();
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("nome", carro.getNome());
            values.put("desc", carro.getDesc());
            values.put("url_foto", carro.getUrlFoto());
            values.put("url_video", carro.getUrlVideo());
            values.put("latitude", carro.getLatitude());
            values.put("longitude", carro.getLongitude());
            values.put("tipo", carro.getTipo());
            if ( id != 0 ) {
                String _id = String.valueOf(carro.getId());
                String[] whereArgs = new String[]{_id};
                int count = db.update(NOME_TABELA, values, "_id=?", whereArgs);
                return count;
            } else {
                id = db.insert(NOME_TABELA, "", values);
                return id;
            }
        } finally {
             db.close();
        }
    }

    public int delete(Carro carro) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String id = String.valueOf(carro.getId());
            int count = db.delete(NOME_TABELA, "_id=?", new String[]{id});
            Log.i(TAG, "Deletou [" + count + "] registro(s).");
            return count;
        } finally {
            db.close();
        }
    }

    public List<Carro> findAll() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor c = db.query(NOME_TABELA, null, null, null, null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }

    private List<Carro> toList(Cursor c) {
        List<Carro> carros = new ArrayList<>();
        if ( c.moveToFirst()) {
            do {
                Carro carro = new Carro();
                carro.setId(c.getLong(c.getColumnIndex("_id")));
                carro.setNome(c.getString(c.getColumnIndex("nome")));
                carro.setDesc(c.getString(c.getColumnIndex("desc")));
                carro.setUrlFoto(c.getString(c.getColumnIndex("url_foto")));
                carro.setUrlVideo(c.getString(c.getColumnIndex("url_video")));
                carro.setLatitude(c.getString(c.getColumnIndex("latitude")));
                carro.setLongitude(c.getString(c.getColumnIndex("longitude")));
                carro.setTipo(c.getString(c.getColumnIndex("tipo")));

                carros.add(carro);
            } while(c.moveToNext());
        }
        return carros;
    }

    public boolean exists(String nome) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.query("carro", null, "nome=?", new String[]{nome}, null,
                    null,null,null);
            return c.getCount() > 0;
        } finally {
            db.close();
        }
    }
    public void execSQL(String sql) {
        execSQL(sql, null);
    }

    public void execSQL(String sql, Object[] args) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql, args);
        } finally {
            db.close();
        }
    }


}
