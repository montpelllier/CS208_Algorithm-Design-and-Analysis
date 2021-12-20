//
// Created by Think on 2021/3/28.
//
#include <iostream>

using namespace std;
const int root = 1;

int main(){
    int n, cur, next, temp, cost, income;
    cin >> n;
    long long ans=0, hp;
    int HP[n+1], father[n+1];
    long long profit[n+1];
    int adj[n+1][n+1];
    bool isVisited[n+1], lock;

    HP[0] = 0;
    for (int i=0;i<=n;i++) {
        father[i] = 0;
        profit[i] = 0;
        isVisited[i] = false;
        for (int j=0; j<=n; ++j) adj[i][j] = -1;
    }

    for (int i=1;i<=n;i++) cin >> HP[i];
    for (int i=1;i<n;i++){
        cin >> cur >> next >> adj[cur][next];
        adj[next][cur] = adj[cur][next];
    }

    cur = root;//represent current node
    do {
        next = -1;
        for (int i=1;i<=n;i++){
            if(adj[cur][i]!=-1 && !isVisited[i] && i!=father[cur]){
                next = i;
                break;
            }
        }

        if (next == -1){//no node to down
            temp = father[cur];
            isVisited[cur] = true;
            profit[cur] += HP[cur];
            profit[cur] -= adj[temp][cur];
            profit[cur] -= adj[temp][cur];//profit=2*cost-hp
            profit[temp] += profit[cur];
            cur = temp;//go up
        } else {
            father[next] = cur;//record its father
            cur = next;//go down
        }
    } while (!isVisited[root]);

    for (int i=1;i<=n;i++) isVisited[i] = false;
    hp = HP[root];
    cur = root;//initialization
    do {
        lock = false;
        next = -1;
        income = -1;
        cost = INT32_MAX;
        for (int i=1;i<=n;i++){
            if(adj[cur][i]!=-1 && !isVisited[i] && i!=father[cur]){
                if (profit[i]>=0 && adj[cur][i]<cost){ //lower cost first
                    cost = adj[cur][i];
                    next = i;
                    lock = true;
                }else if(profit[i]<0 && !lock && HP[i]>income){//higher income first
                    income = HP[i];
                    next = i;
                }
            }
        }

        if (next == -1){//no node to down
            temp = father[cur];
            isVisited[cur] = true;
            hp -= adj[temp][cur];
            if (hp < 0) {
                ans -= hp;
                hp = 0;
            }
            cur = temp;//go up
        } else {
            hp -= adj[cur][next];
            if (hp < 0) {
                ans -= hp;
                hp = 0;
            }
            hp += HP[next];
            cur = next;//go down
        }
    } while (!isVisited[root]);

    cout << ans << endl;
}
