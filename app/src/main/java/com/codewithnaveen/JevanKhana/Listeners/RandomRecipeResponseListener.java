package com.codewithnaveen.JevanKhana.Listeners;

import com.codewithnaveen.JevanKhana.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response,String message);
    void didError(String message);
}
