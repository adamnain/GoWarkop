package io.github.adamnain.gowarkop;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.adamnain.gowarkop.api.BaseApiService;
import io.github.adamnain.gowarkop.api.UtilsApi;
import io.github.adamnain.gowarkop.model.Pesan;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiPesananActivity extends AppCompatActivity {
    //service
    BaseApiService apiService;
    ProgressDialog loading;
    //session
    SessionManager session;

    private GpsTracker gpsTracker;
    double latitude, longitude;


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

        ButterKnife.bind(this);

        apiService = UtilsApi.getAPIService();
        session  = new SessionManager(getApplicationContext());


        tvNamaMenu.setText(getIntent().getStringExtra("namamenu"));
        tvTotalPesanan.setText("Total Pesanan: "+getIntent().getStringExtra("totalPesanan"));
        tvTotalHarga.setText("Total Harga Rp."+getIntent().getStringExtra("totalHarga"));
    }

    @OnClick(R.id.btn_konfirmasi_pesanan)
    public void konfirmasiPesanan(){
        String nama = session.getNamaPref();
        String noHp = session.getHpPref();
        String email = session.getEmailPref();
        String gambar = "https://upload.wikimedia.org/wikipedia/commons/6/64/Foods_%28cropped%29.jpg";
        String status = "0";
        postPesan(nama, noHp, email, String.valueOf(latitude), String.valueOf(longitude), getIntent().getStringExtra("namamenu"), gambar, getIntent().getStringExtra("totalPesanan"), getIntent().getStringExtra("totalHarga"), status);

    }

    @OnClick(R.id.btn_deteksi_lokasi)
    public void getLocation(){
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        gpsTracker = new GpsTracker(getApplicationContext());
        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Toast.makeText(getApplicationContext(), String.valueOf(latitude), Toast.LENGTH_SHORT).show();
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    //service postPesanan
    public void postPesan(String nama, String noHp, String email, String latit, String longit, String namaMenu, String gambar, String jumlah, String totalHarga, String status){
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        Pesan pesan = new Pesan(nama, noHp, email, latit, longit, namaMenu, gambar, jumlah, totalHarga, status);
        Call<ResponseBody> call = apiService.postPesan( pesan,"123");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
