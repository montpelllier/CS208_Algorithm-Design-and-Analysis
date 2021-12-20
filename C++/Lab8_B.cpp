//
// Created by Think on 2021/4/16.
//
#include <iostream>
using namespace std;

int main() {
    int n, m, res=0, temp;
    cin >> n >> m;
    string opera[n];
    int arr[n];

    for (int i = 0; i < n; ++i) {
        cin >> opera[i] >> arr[i];
    }

    for (int i = 1; i < 10;) {
        temp = i;
        for (int j = 0; j < n; ++j) {
            if (opera[j] == "AND") i &= arr[j];
            else if (opera[j] == "OR") i |= arr[j];
            else if (opera[j] == "XOR") i ^= arr[j];
        }
        res |= i;
        i = temp*2;
    }
    cout << res << endl;
}
