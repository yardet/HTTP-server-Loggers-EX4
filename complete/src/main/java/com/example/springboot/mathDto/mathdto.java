package com.example.springboot.mathDto;

import com.example.springboot.model.mathPrase;

import java.util.ArrayList;
import java.util.List;

public interface mathdto {
    void insertMath(ArrayList<Integer> arguments, String operator);

    default void insertMath(mathPrase mathPrase1){
        insertMath(mathPrase1.getArguments(),mathPrase1.getOperator());
    }

    List<mathPrase> selectAllPrase();
}
