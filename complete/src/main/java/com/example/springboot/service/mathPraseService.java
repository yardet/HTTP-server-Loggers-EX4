package com.example.springboot.service;

import com.example.springboot.mathDto.mathdto;
import com.example.springboot.model.mathPrase;
import com.example.springboot.model.responsePrase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;


@Service
public class mathPraseService {

    private final mathdto mathdto1;
    public responsePrase responsePrase1;
    public Stack<Integer> stack = new Stack<>();

    @Autowired
    public mathPraseService(@Qualifier("mathDto") mathdto mathdto) {
        this.mathdto1 = mathdto;
    }


    public void addMath(mathPrase mathPrase1){
        mathdto1.insertMath(mathPrase1);
    }

    public List<mathPrase> getAllMath(){
        return mathdto1.selectAllPrase();
    }


    public int Plus(int a, int b) {
        return a+b;
    }
    public int Minus(int a, int b) {
        return a-b;
    }
    public int Times(int a, int b) {
        return a*b;
    }
    public Integer Divide(int a, int b) {
        if(b==0){
            return null;
        }
        return a/b;
    }
    public int power(int a, int b) {
        int result=1;
        for(int i=0;i<b;i++){
            result*=a;
        }
        return result;
    }
    public int Abs(int a) {
        if(a<0){
            return -a;
        }
        return a;
    }
    public int Factorial(int a) {
        if(a<0){
            return -1;
        }
        int result=1;
        for(int i=1;i<=a;i++){
            result*=i;
        }
        return result;
    }

    public responsePrase getResponsePrase1() {
        return responsePrase1;
    }

    public int getStackSize(){
        return stack.size();
    }

    public Integer getStackPop(){
        if(stack.size()==0){
            return null;
        }
        else
            return stack.pop();
    }

    public void addArgumants(ArrayList<Integer> arguments){
        for(int i=0;i<arguments.size();i++){
            stack.push(arguments.get(i));
        }
    }

    public List<Integer> getArgumants(){
        ArrayList<Integer> list = new ArrayList<>();
        while(stack.size()!=0){
            list.add(stack.pop());
        }
        for(int i=list.size()-1;i>=0;i--){
            stack.push(list.get(i));
        }
        return list;
    }

    public void removeArgumants(int num) {
        for(int i=0;i<num;i++){
            stack.pop();
        }
    }
    public responsePrase calculation(int a,int b,String operator){
        switch (operator.toLowerCase(Locale.ROOT)){
            case "plus":
                responsePrase1=new responsePrase(Plus(a,b));
                break;
            case "minus":
                responsePrase1=new responsePrase(Minus(a,b));
                break;
            case "times":
                responsePrase1=new responsePrase(Times(a,b));
                break;
            case "divide":
                responsePrase1=new responsePrase(Divide(a,b));
                if(responsePrase1.getResult()==null){
                    responsePrase1.setError_message("Error while performing operation Divide: division by 0");
                    responsePrase1.setResult(409);
                }
                break;
            case "pow":
                responsePrase1=new responsePrase(power(a,b));
                break;
            case "abs":
                responsePrase1=new responsePrase(Abs(a));
                break;
            case "fact":
                responsePrase1=new responsePrase(Factorial(a));
                if(responsePrase1.getResult()==-1){
                    responsePrase1.setError_message("Error while performing operation Factorial: not supported for the negative number");
                    responsePrase1.setResult(409);
                }
                break;
            default:
                responsePrase1=new responsePrase(409);
                responsePrase1.setError_message("“Error: unknown operation:"+operator+"\n");
                break;
        }
        return responsePrase1;
    }


}
