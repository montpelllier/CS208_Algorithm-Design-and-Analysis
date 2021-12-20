//
// Created by Think on 2021/3/8.
//
#include <iostream>
#include<algorithm>
using namespace std;

int main() {
    int t,n;
    cin >> t;
    while (t-->0){
        int max,sum,num=1,other=0,second;
        cin >> n;
        int problem[n];
        for(int i=0;i<n;i++) cin >> problem[i];
        sort(problem,problem+n);
        max = problem[n-1];
        sum = max;
        for(int i=n-2;i>=0;i--){
            if(max%problem[i] != 0) {
                if(num == 1) {
                    second = problem[i];
                    sum += second;
                    num++;
                }else if(num==2 && second%problem[i]!=0) {
                    sum += problem[i];
                    num++;
                } else if(num == 3) break;
            }else if(num == 3) break;
            else if(max%problem[i] == 0 && (problem[i]==max/2 || problem[i]==max/3 || problem[i]==max/5)) {
               //cout << other;
                other++;
            }
        }
        if(other==3 && max/30*31 > sum) cout << max/30*31 << endl;
        else cout << sum << endl;
        //for(int i=0;i<n;i++) cout << problem[i] << " ";
    }

}
