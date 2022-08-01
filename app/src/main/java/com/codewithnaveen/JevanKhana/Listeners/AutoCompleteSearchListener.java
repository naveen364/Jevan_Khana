package com.codewithnaveen.JevanKhana.Listeners;

import com.codewithnaveen.JevanKhana.Models.searchItem;

import java.util.List;

public interface AutoCompleteSearchListener {
    void didFetch(List<searchItem> response, String message);
    void didError(String message);
}
