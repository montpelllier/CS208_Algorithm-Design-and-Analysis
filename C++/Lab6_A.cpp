//
// Created by Think on 2021/4/2.
//
#include <iostream>
using namespace std;

int main() {
    int n, max = -1000000001, min = 1000000001, max_i = -1, min_i = -1;
    long long ans = 0;

    cin >> n;
    int power[n];
    for (int i = 0; i < n; ++i) {
        cin >> power[i];
        if (power[i] > max) {
            max = power[i];
            max_i = i;
        }//get max and its index
        if (power[i] < min) {
            min = power[i];
            min_i = i;
        }//get min and its index
    }
    if (n == 1) ans = power[0];
    else {
        if (max < 0) {
            ans = max;
            power[max_i] = 0;
        }//all negative, add max
        if (min > 0) {
            ans = -min;
            power[min_i] = 0;
        }//all positive, sub min

        for (int i = 0; i < n; ++i) {
            if (power[i] < 0) ans -= power[i];
            else ans += power[i];
        }
    }

    cout << ans << endl;
}