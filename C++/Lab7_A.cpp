//
// Created by Think on 2021/4/20.
//
#include <map>
#include <list>
#include <iostream>
#include <algorithm>

using namespace std;

struct Task{
    int start_time, end_time, weight;
};

list<int> timeslots;
map<int, Task> schuedule;

bool cmp_start(const Task &x, const Task &y){
    return x.start_time < y.start_time;//increase <
}

bool cmp_weight(const Task &x, const Task &y){
    return x.weight > y.weight;//decrease >
}

bool LinearMatch(Task t, list<int>::iterator iter) {
    int x = *iter;
    if (x > t.end_time) return false;
    if (schuedule.find(x) == schuedule.end()) {//x not match
        schuedule[x] = t;
        return true;
    }
    Task temp = schuedule[x];
    if (temp.end_time < t.end_time) {
        return LinearMatch(t, ++iter);
    } else if (LinearMatch(temp, ++iter)) {
        schuedule[x] = t;
        return true;
    }
    return false;
}

int main() {
    int n, x=0;
    long long ans = 0;
    cin >> n;
    Task task[n];
    list<int>::iterator iter;

    for (int i = 0; i < n; ++i) {
        cin >> task[i].start_time >> task[i].end_time >> task[i].weight;
    }
    sort(task, task+n, cmp_start);
    for (int i = 0; i < n; ++i) {
        x = max(x+1, task[i].start_time);
        timeslots.push_back(x);
    }

    sort(task, task+n, cmp_weight);
    for (int i = 0; i < n; ++i) {
        iter = timeslots.begin();
        if (LinearMatch(task[i], iter)) ans+=task[i].weight;
    }

    cout << ans << endl;
}
