package br.com.livroandroid.carros.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Edmilson on 12/08/2016.
 */
public class Carro implements Parcelable {
    private long id;
    private String tipo;
    private String nome;
    private String desc;
    private String urlFoto;
    private String urlInfo;
    private String urlVideo;
    private String latitude;
    private String longitude;


    public void readFromParcel(Parcel parcel) {
        this.id = parcel.readLong();
        this.tipo = parcel.readString();
        this.nome = parcel.readString();
        this.desc = parcel.readString();
        this.urlFoto = parcel.readString();
        this.urlInfo = parcel.readString();
        this.urlVideo = parcel.readString();
        this.latitude = parcel.readString();
        this.longitude = parcel.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(tipo);
        dest.writeString(nome);
        dest.writeString(desc);
        dest.writeString(urlFoto);
        dest.writeString(urlInfo);
        dest.writeString(urlVideo);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Carro> CREATOR = new Creator<Carro>() {
        @Override
        public Carro createFromParcel(Parcel in) {
            Carro c = new Carro();
            c.readFromParcel(in);
            return c;
        }

        @Override
        public Carro[] newArray(int size) {
            return new Carro[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(String urlInfo) {
        this.urlInfo = urlInfo;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Carro{" + "nome=" + getNome() + "\'" + "}";
    }
}
