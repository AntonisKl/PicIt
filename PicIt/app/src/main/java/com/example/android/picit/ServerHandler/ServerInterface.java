package com.example.android.picit.ServerHandler;

import com.example.android.picit.SchemaClasses.HistoryElement;
import com.example.android.picit.SchemaClasses.Product;
import com.example.android.picit.SchemaClasses.ShopProductElement;
import com.example.android.picit.SchemaClasses.Store;
import com.example.android.picit.StoreResult;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by DimitrisLPC on 14/10/2017.
 */

public interface ServerInterface {

    @POST("/adduser")
    Call<Integer> addUser();

    @GET("/getHistory/{userid}")
    Call<List<HistoryElement>> getHistory(
            @Path("userid") int userid
    );

    @Multipart
    @POST("/identifyProduct")
    Call<Product> identifyProduct(
            @Part("userid") int userid,
            @Part MultipartBody.Part image
    );

    @GET("/findStores/{prodId}")
    Call<List<StoreResult>> findStores(
            @Path("prodId") int productId
    );

    @GET("/getStoreDetails/{storeId}/{productId}")
    Call<ShopProductElement> getStoreProductDetails(
            @Path("storeId") int storeId,
            @Path("productId") int productId
    );

    @GET("/findSimilarProducts/{productid}")
    Call<List<Product>> findSimilarProducts(
            @Path("productid") int productId
    );

    @GET("/store/{id}")
    Call<Store> getStoreDetails(
            @Path("id") int storeId
    );

}
