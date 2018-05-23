package io.github.adamnain.gowarkop;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.adamnain.gowarkop.api.BaseApiService;
import io.github.adamnain.gowarkop.model.Pesan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiPesananActivity extends AppCompatActivity {
    //service
    BaseApiService apiService;
    ProgressDialog loading;

    @BindView(R.id.tv_nama_menu_konfirmasi)
    TextView tvNamaMenu;
    @BindView(R.id.tv_total_harga_konfirmasi)
    TextView tvTotalHarga;
    @BindView(R.id.tv_jumlah_pesanan_konfirmasi)
    TextView tvTotalPesanan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pesanan);

        tvNamaMenu.setText("namamenu");
        tvTotalPesanan.setText(getIntent().getStringExtra("totalPesanan"));
        tvTotalHarga.setText(getIntent().getStringExtra("totalHarga"));
    }

    @OnClick(R.id.btn_konfirmasi_pesanan)
    public void konfirmasiPesanan(){
        String nama = "nama";
        String noHp = "085724748508";
        String email = "email@canggih.org";
        String gambar = "https://upload.wikimedia.org/wikipedia/commons/6/64/Foods_%28cropped%29.jpg";
        String status = "0";
        postPesan(nama, noHp, email, getIntent().getStringExtra("latitude"), getIntent().getStringExtra("longitude"), getIntent().getStringExtra("namamenu"), gambar, getIntent().getStringExtra("totalPesanan"), getIntent().getStringExtra("totalHarga"), status);

    }

    private void postPesan(String nama, String noHp, String email, String latit, String longit, String namaMenu, String gambar, String jumlah, String totalHarga, String status){
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        Pesan pesan = new Pesan(nama, noHp, email, latit, longit, namaMenu, gambar, jumlah, totalHarga, status);
        Call<Pesan> call = apiService.pesan(pesan,"123");
        call.enqueue(new Callback<Pesan>() {
            @Override
            public void onResponse(Call<Pesan> call, Response<Pesan> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), "Sukses Kirim Pesanan Silahkan Lihat Pesanan!", Toast.LENGTH_SHORT).show();
                }
                else {
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed fletch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pesan> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
