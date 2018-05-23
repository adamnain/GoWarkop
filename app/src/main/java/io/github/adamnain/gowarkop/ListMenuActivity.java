package io.github.adamnain.gowarkop;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import io.github.adamnain.gowarkop.adapter.MenuAdapter;
import io.github.adamnain.gowarkop.api.BaseApiService;
import io.github.adamnain.gowarkop.api.UtilsApi;
import io.github.adamnain.gowarkop.model.Menu;
import io.github.adamnain.gowarkop.model.ResponseMenu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMenuActivity extends AppCompatActivity {

    private List<Menu> listMenu;
    private RecyclerView mRecyclerView;
    private MenuAdapter mAdapter;
    BaseApiService apiService;
    ProgressDialog loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        listMenu = new ArrayList<>();
        mAdapter = new MenuAdapter(getApplicationContext(), listMenu);
        mRecyclerView = findViewById(R.id.rv_list_menu);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);

        apiService = UtilsApi.getAPIService();



        backButton();
        final String key = "123";

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        Call<ResponseMenu> call = apiService.getAllMenu(key);
        call.enqueue(new Callback<ResponseMenu>() {
            @Override
            public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    listMenu = response.body().getData();

                    mRecyclerView.setAdapter(new MenuAdapter(getApplicationContext(), listMenu));
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed fletch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMenu> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });


    }


    //untuk enampilkan back button
    public void backButton(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List Menu");
    }

    //fungsi back ketika tombol back diklik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
