package com.example.springboot.mathDto;

import com.example.springboot.model.mathPrase;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("mathDto")
public class mathService  implements mathdto{
    private static List<mathPrase> DB=new ArrayList<>();

    @Override
    public void insertMath(ArrayList<Integer> arguments, String operator) {
        DB.add(new mathPrase(arguments, operator));
    }

    @Override
    public List<mathPrase> selectAllPrase() {
        return DB;
    }


}
