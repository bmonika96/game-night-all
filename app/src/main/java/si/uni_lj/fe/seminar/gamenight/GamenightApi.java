package si.uni_lj.fe.seminar.gamenight;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GamenightApi {
    @GET("igre/{uporabnisko_ime}")
    Call<String> getIgre(@Path("uporabnisko_ime") String uporabnisko_ime, @Header("Authorization") String basicToken);

    @GET("dogodki/{uporabnisko_ime}")
    Call<String> getDogodki(@Path("uporabnisko_ime") String uporabnisko_ime, @Header("Authorization") String basicToken);

    @GET("login")
    Call<ResponseBody> getToken(@Query("username") String username, @Query("password") String password);

    @GET("registracija")
    Call<String> registracija(@Body ResponseBody uporabnik);

    @POST("dogodki/dodaj")
    Call<ResponseBody> dodajDogodek(String admin_monika, JSONObject dogodek);

    @POST("igre/dodaj")
    Call<ResponseBody> dodajIgro(String admin_monika, JSONObject igra);
}
