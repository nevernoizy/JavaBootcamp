package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int Number) throws Exception {
        if(Number>1){
            return Number % 2 == 0;
        } else{
            throw new Exception("IllegalNumberException");
        }
    }
    public int digitSum(int Number){
        int res = 0;
        if(Number<0){
            Number*=-1;
        }
        while(Number>0){
            res+=Number%10;
            Number/=10;
        }
        return res;
    }

}
