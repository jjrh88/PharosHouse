package mabel_tech.com.scanovate_demo.network;

import mabel_tech.com.scanovate_demo.model.CloseRegistroRequest;
import mabel_tech.com.scanovate_demo.model.CloseRequest;
import mabel_tech.com.scanovate_demo.model.CloseResponse;
import mabel_tech.com.scanovate_demo.model.DocRequest;
import mabel_tech.com.scanovate_demo.model.DocRequestFront;
import mabel_tech.com.scanovate_demo.model.FaceRequest;
import mabel_tech.com.scanovate_demo.model.GetConfigResponse;
import mabel_tech.com.scanovate_demo.model.LogRequest;
import mabel_tech.com.scanovate_demo.model.LogResponse;
import mabel_tech.com.scanovate_demo.model.ScoreResponse;
import mabel_tech.com.scanovate_demo.model.UserAuthenticationRequest;
import mabel_tech.com.scanovate_demo.model.UserAuthenticationResponse;
import mabel_tech.com.scanovate_demo.model.VerificationIdentityRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApi {

    @POST("New")
    Call<CloseResponse> postFace(@Body FaceRequest request);

    @POST("Images/DocumentFrontSide")
    Call<ResponseBody> postDocFront(@Body DocRequestFront request);

    @POST("Images/DocumentBackSide")
    Call<CloseResponse> postDocBack(@Body DocRequest request);

    @POST("Close")
    Call<CloseResponse> close(@Body CloseRequest request);

    @GET("Validation/{txId}")
    Call<ScoreResponse> validateTransaction( @Path("txId") String var2, @Query("returnImages") Boolean var3);


    @POST("RegisterLog")
    Call<LogResponse> RegisterLog(@Body LogRequest request);

    @POST("CloseRegistro")
    Call<CloseResponse> CloseRegistro(@Body CloseRegistroRequest request);

    @POST("UserAuthentication")
    Call<UserAuthenticationResponse> UserAuthentication(@Body UserAuthenticationRequest request);

    @POST("GetConfig")
    Call<GetConfigResponse> GetConfig (@Query("Message") String request);

    @POST("CustomerVerification")
    Call<CloseResponse> customerVerification(@Body VerificationIdentityRequest request);


}