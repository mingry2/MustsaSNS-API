package com.mutsasns.finalproject_kimmingyeong.service;

import org.springframework.stereotype.Service;

@Service
public class AlgorithmService {

    public Integer sumOfDigit(Integer num){
        Integer sum = 0;
        while(num!=0){
            sum += num%10;
            num /= 10;
        }
        return sum;
    }
}
