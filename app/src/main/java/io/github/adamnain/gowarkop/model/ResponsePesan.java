package io.github.adamnain.gowarkop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePesan {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Pesan pesan;


    public ResponsePesan() {
    }


    public ResponsePesan(String status, Pesan pesan) {
        super();
        this.status = status;
        this.pesan = pesan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pesan getPesan() {
        return pesan;
    }

    public void setData(Pesan pesan) {
        this.pesan = pesan;
    }

}
