//
// Created by Think on 2021/4/16.
//
#include <iostream>
#include <map>
#include <queue>

using namespace std;

int main() {
    int n, sum, ans;
    cin >> n;
    string text;
    map<char, int> count;
    map<char, int>::iterator it;
    priority_queue<int, vector<int>, greater<int> > minheap;

    for (int i = 0; i < n; ++i) {
        cin >> text;
        ans = 0;
        //minheap
        count.clear();//initialize ans and map
        for (int j = 0; j < text.length(); ++j) {
            if (count.find(text[j]) != count.end()) count[text[j]]++;
            else count[text[j]] = 1; //get the num of each character
        }

        it = count.begin();
        while (it != count.end()) minheap.push(it++ ->second);
        for (int j = 1; j < count.size(); ++j) {//pop two and the sum
            sum = minheap.top();
            minheap.pop();
            sum += minheap.top();
            minheap.pop();
            ans += sum;
            minheap.push(sum);
        }
        while (!minheap.empty()) minheap.pop();//clear heap
        cout << ans << endl;
    }

}
