package kr.co.tjoeun.pizzastore_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

import kr.co.tjoeun.pizzastore_20200527.databinding.ActivityPizzaStoreDetailBinding;
import kr.co.tjoeun.pizzastore_20200527.datas.PizzaStore;

public class PizzaStoreDetailActivity extends BaseActivity {

    ActivityPizzaStoreDetailBinding binding;

    PizzaStore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pizza_store_detail);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                        Uri myUri = Uri.parse(String.format("tel:%s", mStore.getPhoneNum()));
                        Intent myIntent = new Intent(Intent.ACTION_CALL, myUri);
                        startActivity(myIntent);


                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                        Toast.makeText(mContext, "통화 권한을 허락해야 사용 가능.", Toast.LENGTH_SHORT).show();

                    }
                };

                TedPermission.with(mContext)
                        .setPermissionListener(permissionListener)
                        .setDeniedMessage("설정 에서 권한 승인이 필요합니다.")
                        .setPermissions(Manifest.permission.CALL_PHONE)
                        .check();


            }
        });

    }

    @Override
    public void setValues() {

        mStore = (PizzaStore) getIntent().getSerializableExtra("store");

        binding.nameTxt.setText(mStore.getName());
        binding.phoneNumTxt.setText(mStore.getPhoneNum());

        Glide.with(mContext).load(mStore.getLogoImgUrl()).into(binding.logoImg);


    }
}
