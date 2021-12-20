#include <cstdio>
#include <cstdlib>
#include <algorithm>
#define MAXN 200000
using namespace std;

int par[MAXN], Rank[MAXN], num[MAXN];
long long ans[MAXN];

typedef struct{
    int a, b, price;
}Node;
Node a[MAXN];

bool cmp(const void*a, const void *b){
    return ((Node*)a)->price < ((Node*)b)->price;//ascending
}
void Init(int n){//create n node
    for(int i = 0; i < n; i++){
        Rank[i] = 0;//num of parent?
        par[i] = i;//parent
        num[i] = 1;
    }
}

int find(int x){
    int root = x;
    while(root != par[root]) root = par[root];
    while(x != root){
        int t = par[x];
        par[x] = root;
        x = t;
    }
    return root;
}

void unite(int x, int y){
    x = find(x);
    y = find(y);
    if(Rank[x] < Rank[y]){
        par[x] = y;
        num[y] += num[x];
    }else{
        par[y] = x;
        num[x] += num[y];
        if(Rank[x] == Rank[y]) Rank[x]++;
    }
}
//n is the num of nodes，n-1 is the edges
int Kruskal(int n){

    //将边按照权值从小到大排序
    sort(a, a+n-1, cmp);
    ans[0] = 0;
    int cp = a[0].price , ci = 0;
    for(int i = 0; i < n-1; i++){
        if (cp < a[i].price){
            cp = a[i].price;
            while (ci < cp) ans[ci + 1] = ans[ci++];
        }
        //判断当前这条边的两个端点是否属于同一棵树
        int a_par = find(a[i].a);
        int b_par = find(a[i].b);
        if(a_par != b_par){
            ans[ci] += num[a_par] * num[b_par];
            unite(a[i].a, a[i].b);
            //res += a[i].price;//size 相乘
            //nEdge++;
        }
    }
    //如果加入边的数量小于m - 1,则表明该无向图不连通,等价于不存在最小生成树
//    if(nEdge < m-1) res = -1;
//    return res;
}
int main() {
    int n, m, ans;
    scanf("%d%d", &n, &m);
    Init(n);//create n nodes
    for (int i = 0; i < n-1; i++) {//n-1 edges
        scanf("%d%d%d", &a[i].a, &a[i].b, &a[i].price);
        //将村庄编号变为0~m-1（这个仅仅只是个人习惯，并非必要的）
        a[i].a--;
        a[i].b--;
    }
    Kruskal(n);
    //ans = Kruskal(n, m);
//    if (ans == -1) printf("?\n");
//    else printf("%d\n", ans);
    while (m--) printf("%d\n");
    //return 0;
}