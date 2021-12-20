//
// Created by Think on 2021/4/23.
//
#include <iostream>

using namespace std;

long long times = 0L;
void merge(int arr[], int left, int mid, int right) {//merge
    int SortedArr[right - left + 1];
    int l = left, r = mid + 1, a = 0, b = 0;
    while (l <= mid && r <= right) {
        if (arr[l] > arr[r]) {
            times += mid - l + 1;
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

void Sort(int arr[], int left, int right) {//iteration
    if (left < right) {
        int mid = (left + right) / 2;
        Sort(arr, left, mid);
        Sort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }
}

int main() {
    int n;
    cin >> n;
    int arr[n];
    for (int i = 0; i < n; i++) cin >> arr[i];
    Sort(arr, 0, n - 1);
    cout << times;
}
