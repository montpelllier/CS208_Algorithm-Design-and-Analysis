//
// Created by Think on 2021/5/14.
//
#include <iostream>
#define MAX 101

using namespace std;

int dp[MAX][MAX], cost[MAX];

int opt(int left, int right){
    int ans = 0x7fffffff;
    for (int i = left; i < right; ++i) {
        if (ans > dp[left][i]+dp[i+1][right]) ans = dp[left][i]+dp[i+1][right];
    }
    for (int i = left; i <= right; ++i) ans += cost[i];
    return ans;
}

int main(){
    int n;
    cin >> n;
    for (int i = 1; i <= n; ++i) {
        cin >> cost[i];
        dp[i][i] = 0;
    }

    for (int i = 1; i < n; ++i) {
        for (int j = 1; j+i <= n; ++j) {
            dp[j][i+j] = opt(j,i+j);
        }
    }
    cout << dp[1][n];

}
