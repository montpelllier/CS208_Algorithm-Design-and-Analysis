//
// Created by Think on 2021/5/14.
//
#include <iostream>
#include <cstring>

#define MAX 5001

using namespace std;

long long dp[MAX][MAX];
int value[MAX], cost[MAX];

long long OPT(int item, int capacity) {
    if (item == 0) {
        if (dp[item][capacity] == -1) dp[item][capacity] = 0;
    } else if (cost[item - 1] > capacity) {
        if (dp[item][capacity] == -1) dp[item][capacity] = OPT(item - 1, capacity);
    } else {
        if (dp[item][capacity] == -1) {
            dp[item][capacity] = max(OPT(item - 1, capacity), value[item - 1] + OPT(item - 1, capacity - cost[item - 1]));
        }
    }
    return dp[item][capacity];
}

int main() {//Knapsack problem 0-1
    int n, m;
    cin >> n >> m;
    for (int i = 0; i < n; ++i) cin >> value[i];
    for (int i = 0; i < n; ++i) cin >> cost[i];
    for (int i = 0; i <= n; ++i) memset(dp[i], -1, sizeof(long long) * (m+1));

    cout << OPT(n, m) << endl;

    return 0;
}
