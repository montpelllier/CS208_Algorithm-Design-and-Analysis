//
// Created by Think on 2021/4/30.
//
#include <iostream>

using namespace std;

bool isPowerOf2(int num) {//check if it's the power of two
    return (num & (num - 1)) == 0;
}

long long highestOneBit(long long i) {//get the largest power of 2 less than i
    i |= (i >> 1);
    i |= (i >> 2);
    i |= (i >> 4);
    i |= (i >> 8);
    i |= (i >> 16);
    i |= (i >> 32);
    return i - (i >> 1);
}

long long getR(long long j);

long long getL(long long j) {//get the number of L from 1 to j
    if (j == 0) return 0;
    else if (isPowerOf2(j + 1)) return j / 2 + 1;//can get the result immediately when j=2^n-1
    long long temp = highestOneBit(j + 1);
    return getL(temp - 1) + 1 + (getR(temp - 1) - getR(temp - j + temp - 1));//left part + L + right part
}

long long getR(long long j) {//get the number of R from 1 to j
    if (isPowerOf2(j + 1)) return j / 2;
    long long temp = highestOneBit(j + 1);
    return getR(temp - 1) + (getL(temp - 1) - getL(temp - j + temp - 1));
}

int main() {
    int n;
    long long i, j;
    cin >> n;
    while (n-- > 0) {
        cin >> i >> j;
        cout << getL(j) - getL(i - 1) << endl;
    }
}