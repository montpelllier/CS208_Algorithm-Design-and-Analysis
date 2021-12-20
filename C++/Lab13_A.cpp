//
// Created by Think on 2021/5/21.
//
#include <iostream>
#include <cstring>

#define MAX 100001
using namespace std;

int dp[MAX], cost[MAX], coupon[MAX];

int findBest(int i) {
    if (i == 1) {
        if (dp[1] == 0) dp[1] = cost[1];
    } else if (i == 2) {
        if (dp[2] == 0) dp[2] = min(cost[1] + cost[2], coupon[1]);
    } else if (i>2){
        if (dp[i]==0) dp[i] = min(cost[i]+ findBest(i-1), coupon[i-1]+ findBest(i-2));
    }
    return dp[i];
}

int main(){
    int n;
    cin >> n;
    for (int i = 1; i <= n; ++i) cin >> cost[i];
    for (int i = 1; i <= n-1; ++i) cin >> coupon[i];
    memset(dp, 0, sizeof(int ) * (n+1));

    cout << findBest(n) << endl;
//    for (int i = 1; i <= n; ++i) {
//        cout << dp[i] << endl;
//    }

}
