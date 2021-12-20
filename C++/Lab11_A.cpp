//
// Created by Think on 2021/5/7.
//
#include <iostream>

using namespace std;

long long highestOneBit(long long i) {//get the largest power of 2 less than i
    i |= (i >> 1);
    i |= (i >> 2);
    i |= (i >> 4);
    i |= (i >> 8);
    i |= (i >> 16);
    i |= (i >> 32);
    return i - (i >> 1);
}

long long getL(long long i);
long long getR(long long i);
long long getN(long long i);

long long getL(long long i) {//get the number of L from 1 to j
    if (i <= 1) return i; //base case
    else if (i <= 3) return 1;
    long long temp = highestOneBit(i);
    long long right = getN(i-temp);
    long long middle = getN(temp/4);
    return temp/4 + middle + right;//determined part + right part + middle part
}

long long getR(long long i) {//get the number of R from 1 to j
    if (i == 0) return 0;
    else if (i <= 3) return i-1;
    long long temp = highestOneBit(i);
    long long right = getL(i-temp);
    long long middle = getL(temp/4);
    return temp/4 + middle + right;
}

long long getN(long long int i) {
    if (i <= 3) return 0;
    long long temp = highestOneBit(i);
    long long right = getR(i-temp);
    long long middle = getR(temp/4);
    return temp/4 + middle + right;
}


int main() {
    int t;
    long long i;
    cin >> t;
    while (t-- > 0) {
        cin >> i;
        cout << getL(i) << " " << getR(i) << " " << getN(i) << endl;
    }
}