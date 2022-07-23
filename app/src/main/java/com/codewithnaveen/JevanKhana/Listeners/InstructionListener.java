package com.codewithnaveen.JevanKhana.Listeners;

import com.codewithnaveen.JevanKhana.Models.InstructionResponse;

import java.util.List;

public interface InstructionListener {
    void didFetch(List<InstructionResponse> response, String message);
    void didError(String message);
}
