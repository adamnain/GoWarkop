package io.github.adamnain.gowarkop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetailMenuActivity extends AppCompatActivity {
    AppBarLayout Appbar;
    CollapsingToolbarLayout CoolToolbar;
    Toolbar toolbar;
    //lokasi
    private GpsTracker gpsTracker;

    int totalPesanan, totalHarga, hargaMenu;
    double latitude, longitude;

    boolean ExpandedActionBar = true;

    @BindView(R.id.img_menu_detail)
    ImageView imgMenu;
    @BindView(R.id.tv_deskripsi_detail)
    TextView tvDeskripsi;
    @BindView(R.id.tv_harga_detail)
    TextView tvHarga;
    @BindView(R.id.tv_nama_menu_detail)
    TextView tvNamaMenu;
    @BindView(R.id.tv_jumlah_pesanan_detail)
    TextView tvJumlahPesanan;
    @BindView(R.id.tv_total_harga_detail)
    TextView tvTotalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        Appbar = (AppBarLayout) findViewById(R.id.appbar);
        CoolToolbar = (CollapsingToolbarLayout) findViewById(R.id.ctolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getLocation();


        totalHarga = 0;
        totalPesanan = Integer.parseInt(tvJumlahPesanan.getText().toString());
        hargaMenu = Integer.parseInt(getIntent().getStringExtra("harga"));


        Glide.with(getApplicationContext())
                .load("https://upload.wikimedia.org/wikipedia/commons/6/64/Foods_%28cropped%29.jpg")
                .into(imgMenu);

        tvNamaMenu.setText(getIntent().getStringExtra("namamenu"));
        tvHarga.setText("Rp"+getIntent().getStringExtra("harga"));
        tvDeskripsi.setText("Lorem Ipsum");

        Appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 200) {
                    ExpandedActionBar = false;
                    CoolToolbar.setTitle(getIntent().getStringExtra("namamenu"));
                    invalidateOptionsMenu();
                }
                else {
                    ExpandedActionBar = true;
                    CoolToolbar.setTitle(getIntent().getStringExtra("namamenu"));
                    invalidateOptionsMenu();
                }
            }
        });


    }

    @OnClick(R.id.btn_plus_detail)
    public void incPesanan(){
        totalPesanan = totalPesanan+1;
        tvJumlahPesanan.setText(""+totalPesanan);
        totalHarga = totalPesanan*hargaMenu;
        tvTotalHarga.setText("Rp."+totalHarga);
    }

    @OnClick(R.id.btn_min_pesanan)
    public void decPesanan(){
        if (totalPesanan > 0){
            totalPesanan = totalPesanan-1;
            tvJumlahPesanan.setText(""+totalPesanan);
            totalHarga = totalPesanan*hargaMenu;
            tvTotalHarga.setText("Rp."+totalHarga);
        }
        else {
            Toast.makeText(getApplicationContext(), "Pesanan Harus Lebih Dari Satu", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.btn_pesan)
    public void pesan(){
        //post pesan
//        String nama = "nama";
//        String noHp = "085724748508";
//        String email = "email@canggih.org";
        String gambar = "https://upload.wikimedia.org/wikipedia/commons/6/64/Foods_%28cropped%29.jpg";
//        String status = "0"
//        postPesan(nama, noHp, email, latitude, longitude, getIntent().getStringExtra("namamenu"), gambar, totalPesanan, totalHarga, status);

        getLocation();
        Intent i = new Intent(getApplicationContext(), KonfirmasiPesananActivity.class);
        i.putExtra("latitude", String.valueOf(latitude));
        i.putExtra("longitude", String.valueOf(longitude));
        i.putExtra("namamenu", getIntent().getStringExtra("namamenu"));
        i.putExtra("gambar", gambar);
        i.putExtra("totalPesanan", String.valueOf(totalPesanan));
        i.putExtra("totalHarga", String.valueOf(totalHarga));
        startActivity(i);
    }


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


//    private void postPesan(nama, noHp, email, latit, longit, namaMenu, gambar, jumlah, totalHarga, status){
//        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
//        Pesan pesan = new Pesan(nama, noHp, email, latit, longit, namaMenu, gambar, jumlah, totalHarga, status);
//        Call<Pesan> call = apiService.pesan(pesan,"123");
//        call.enqueue(new Callback<Pesan>() {
//            @Override
//            public void onResponse(Call<Pesan> call, Response<Pesan> response) {
//                if (response.isSuccessful()){
//                    loading.dismiss();
//                    Toast.makeText(getApplicationContext(), "Sukses Kirim Pesanan Silahkan Lihat Pesanan!", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    loading.dismiss();
//                    Toast.makeText(getApplicationContext(), "Failed fletch data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Pesan> call, Throwable t) {
//                loading.dismiss();
//                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
