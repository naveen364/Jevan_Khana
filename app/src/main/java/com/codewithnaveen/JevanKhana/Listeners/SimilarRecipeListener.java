package com.codewithnaveen.JevanKhana.Listeners;

import com.codewithnaveen.JevanKhana.Models.SimilarRecipe;

import java.util.List;

public interface SimilarRecipeListener {
    void didFetch(List<SimilarRecipe> response, String message);
    void didError(String message);
}
