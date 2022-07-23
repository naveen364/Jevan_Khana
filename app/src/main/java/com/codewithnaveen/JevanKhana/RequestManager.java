package com.codewithnaveen.JevanKhana;

import android.content.Context;

import com.codewithnaveen.JevanKhana.Listeners.InstructionListener;
import com.codewithnaveen.JevanKhana.Listeners.RandomRecipeResponseListener;
import com.codewithnaveen.JevanKhana.Listeners.RecipeDetailsListner;
import com.codewithnaveen.JevanKhana.Listeners.SimilarRecipeListener;
import com.codewithnaveen.JevanKhana.Models.InstructionResponse;
import com.codewithnaveen.JevanKhana.Models.RandomRecipeApiResponse;
import com.codewithnaveen.JevanKhana.Models.ReciepeDetailsResponse;
import com.codewithnaveen.JevanKhana.Models.SimilarRecipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key),"30",tags);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getRecipeDetails(RecipeDetailsListner listner,int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<ReciepeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id,context.getString(R.string.api_key));
        call.enqueue(new Callback<ReciepeDetailsResponse>() {
            @Override
            public void onResponse(Call<ReciepeDetailsResponse> call, Response<ReciepeDetailsResponse> response) {
                if(!response.isSuccessful()){
                    listner.didError(response.message());
                    return;
                }
                listner.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<ReciepeDetailsResponse> call, Throwable t) {
                listner.didError(t.getMessage());
            }
        });
    }

    public void getSimilarRecipe(SimilarRecipeListener listener, int id){
        CallSimilarRecipes callSimilarRecipes = retrofit.create(CallSimilarRecipes.class);
        Call<List<SimilarRecipe>> call = callSimilarRecipes.callSimilarRecipe(id,"8",context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipe>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipe>> call, Response<List<SimilarRecipe>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipe>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getInstruction(InstructionListener listener, int id){
        CallInstructionStep callInstructionStep = retrofit.create(CallInstructionStep.class);
        Call<List<InstructionResponse>> call = callInstructionStep.callInstructionResponse(id,context.getString(R.string.api_key));
        call.enqueue(new Callback<List<InstructionResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionResponse>> call, Response<List<InstructionResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }

    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<ReciepeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallSimilarRecipes{
        @GET("recipes/{id}/similar")
        Call<List<SimilarRecipe>> callSimilarRecipe(
                @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallInstructionStep{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<InstructionResponse>> callInstructionResponse(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}
