//
// Created by Think on 2021/4/23.
//
#include <iostream>
#include <algorithm>

using namespace std;

struct Lanran{
    int u, v, happiness;
};

void merge(Lanran arr[], int left, int mid, int right) {//merge
    Lanran SortedArr[right - left + 1];
    int l = left, r = mid + 1, a = 0, b = 0;
    while (l <= mid && r <= right) {
        if (arr[l].u>arr[r].u || (arr[l].u==arr[r].u && arr[l].v>arr[r].v)) {
            arr[r].happiness -= mid-l+1;
            SortedArr[a++] = arr[r++];
        } else SortedArr[a++] = arr[l++];
    }
    while (l <= mid) {
        SortedArr[a++] = arr[l++];
    }
    while (r <= right) {
        SortedArr[a++] = arr[r++];
    }
    for (int i = left; i <= right; i++) {
        arr[i] = SortedArr[b++];
    }
}

void Sort(Lanran arr[], int left, int right) {//iteration
    if (left < right) {
        int mid = (left + right) / 2;
        Sort(arr, left, mid);
        Sort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }
}

int main(){
    int n;
    cin >> n;
    int count[n];
    Lanran lanran[n];
    for (int i = 0; i < n; ++i) {
        count[i] = 0;
        cin >> lanran[i].u >> lanran[i].v;
        lanran[i].happiness = i;
    }//v increase, if equal u increase
    Sort(lanran,0, n-1);

    for (int i = 0; i < n; ++i) count[lanran[i].happiness]++;
    for (int i = 0; i < n; ++i) cout << count[i] << endl;

    return 0;
}
