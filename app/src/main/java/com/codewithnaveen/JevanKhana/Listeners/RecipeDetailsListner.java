package com.codewithnaveen.JevanKhana.Listeners;

import com.codewithnaveen.JevanKhana.Models.RandomRecipeApiResponse;
import com.codewithnaveen.JevanKhana.Models.ReciepeDetailsResponse;

public interface RecipeDetailsListner {
    void didFetch(ReciepeDetailsResponse response, String message);
    void didError(String message);
}
