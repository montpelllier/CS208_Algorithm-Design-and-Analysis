//
// Created by Think on 2021/3/28.
//
#include <iostream>
#include<algorithm>

using namespace std;

int inRange(int center, int radius, int point){
    if (point>center+radius) return 1;
    else if (point<center-radius) return -1;
    else return 0;
}

int main(){
    int n, m, k, t, ans=0;
    cin >> n >> m >> k >> t;
    int criminal[n], house[m], num[m];
    for (int i = 0; i < n; ++i) cin >> criminal[i];
    for (int i = 0; i < m; ++i) {
        cin >> house[i];
        num[i] = 0;
    }
    sort(criminal,criminal+n);
    sort(house,house+m);

    for (int i = 0, j = 0; i < n && j < m;) {
        switch (inRange(house[j],t,criminal[i])) {
            case -1: i++; break;
            case 1: j++; break;
            case 0:
                ans++; i++;
                if (++num[j]==k) j++;
        }
    }

    cout << ans;
}
