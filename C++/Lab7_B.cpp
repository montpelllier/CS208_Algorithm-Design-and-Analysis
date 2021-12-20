//
// Created by Think on 2021/4/27.
//
#include <iostream>

using namespace std;

const int Fibo[25] = {0,1,2,3,5,8,13,21,34,55,89,144,233,377,610,987,1597,2584,4181,6765,10946,17711,28657,46368,75025};
const int arrSize = 25;

bool isFibo(int low, int high){
//    int left = 0, right = arrSize-1, mid = (left+right)/2;
    for (int i = 0; i < arrSize; ++i) {
        if (low <= Fibo[i] && Fibo[i] <= high ) return true;
    }
    return false;
}

int main(){
    int t, n, m;
    //cout << Fibo[25];
    cin >> t;
    while (t-->0){
        cin >> n >> m;
        isFibo(n, m);

    }

}