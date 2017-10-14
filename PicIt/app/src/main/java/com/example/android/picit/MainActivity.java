package com.example.android.picit;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.picit.SchemaClasses.Product;
import com.example.android.picit.SchemaClasses.User;
import com.example.android.picit.ServerHandler.ServerClient;
import com.example.android.picit.ServerHandler.ServerInterface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    User myUser;
    FragmentManager fragmentManager = getFragmentManager();

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            try {
                File imgFile = File.createTempFile(System.currentTimeMillis() + "", ".png", getApplicationContext().getCacheDir());
                OutputStream os = new BufferedOutputStream(new FileOutputStream(imgFile));
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", imgFile.getName(), reqFile);
                ServerInterface serverService = ServerClient.getClient(getApplicationContext()).create(ServerInterface.class);
                Call<Product> findProduct = serverService.identifyProduct(myUser.getUserId(getApplicationContext()), body);
                findProduct.enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.code() < 300) {
                            Product myProduct = response.body();
                            Toast.makeText(MainActivity.this, myProduct.getProductName(), Toast.LENGTH_SHORT).show();

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            ResultsFragment fragment = ResultsFragment.newInstance("bla", "bla");
                            fragmentTransaction.replace(R.id.content, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Log.e("SVRERROR", t.toString());
                        Toast.makeText(MainActivity.this, "Error on uploading picture.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {

            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.popBackStackImmediate();
                    dispatchTakePictureIntent();
                    return true;
                case R.id.navigation_dashboard:

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    HistoryFragment fragment = HistoryFragment.newInstance("bla", "bla");
                    fragmentTransaction.replace(R.id.content, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }
        myUser = new User();
        if (myUser.getUserId(getApplicationContext()) == -1) {
            myUser.setUserId(getApplicationContext());
        }
        setContentView(R.layout.activity_main);
        dispatchTakePictureIntent();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }

}
