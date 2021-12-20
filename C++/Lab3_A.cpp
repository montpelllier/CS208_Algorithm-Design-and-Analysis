//
// Created by Think on 2021/3/12.
//
#include <iostream>
using namespace std;

int getGCD(int a, int b){
    int c = a % b;
    while (c != 0){
        a = b;
        b = c;
        c = a % b;
    }
    return b;
}

int main(){
    int t,s,n,m,gcd;
    cin >> t;
    while (t-->0){
        cin >> s >> n >> m;
        gcd = getGCD(getGCD(s,n),m);
        s /= gcd; n /= gcd; m /= gcd;
        if (s%2==0) cout << s-1 << endl;
        else cout << "impossible" << endl;
    }
}
