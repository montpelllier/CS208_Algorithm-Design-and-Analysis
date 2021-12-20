//
// Created by Think on 2021/5/7.
//
#include <iostream>
#include <algorithm>
#include <cmath>
#include <complex>
#define cd complex<double>

using namespace std;

double PI = acos(-1);

int min2power(int num) {//get the smallest power of 2 larger than num
    int max = 1 << 30;
    int i = num - 1;
    i |= (i >> 1);
    i |= (i >> 2);
    i |= (i >> 4);
    i |= (i >> 8);
    i |= (i >> 16);
    return i<0 ? 1 : (i>=max ? max : i+1);
}

void butterfly_trans(cd com[], int length) {//change recursion to iteration
    int right = length/2, high_bit;//length must be the power of 2
    for (int left = 1; left < length-1; ++left) {
        if (left < right) swap(com[left],com[right]);
        high_bit = length/2;//start from highest bit
        while (right >= high_bit) {//takes log(n)
            right -= high_bit;//carry bit reverse
            high_bit >>= 1;//check bit by bit from right
        }
        right += high_bit;//add the carry bit
    }
}

void FFT(cd com[], int length, int on) {//1 fft, -1 ifft by iteration
    butterfly_trans(com, length);
    for (int n = 2; n <= length; n <<= 1) {
        cd Wn(cos(-on*2*PI/n), sin(-on*2*PI/n));
        for (int j = 0; j < length; j+=n){
            cd W(1,0);
            for (int k = j; k < j+n/2; ++k) {
                cd u = com[k];
                cd t = W*com[k+n/2];
                com[k] = u+t;
                com[k+n/2] = u-t;
                W = W*Wn;
            }
        }
    }
    //ifft
    if (on == -1) for (int i = 0; i < length; ++i) {
        com[i] = cd (com[i].real()/length, com[i].imag());
    }

}

const int max_size = 262145;
cd com[max_size];//complex arr, must initialize here
int exponent[max_size];
long long cnt[max_size];

int main(){
    int n;
    cin >> n;
    int coe[n];
    for (int i = 0; i < n; ++i) cin >> coe[i];

    sort(coe,coe+n);
    int origin_len = coe[n-1]+1;
    int extend_len = min2power(2*origin_len);
    for (int i = 0; i < extend_len; ++i) {
        exponent[i] = 0;
        cnt[i] = 0;
    }

    for (int i = 0; i < n; ++i) exponent[coe[i]]++;
    for (int i = 0; i < origin_len; ++i) com[i] = cd (exponent[i],0);
    for (int i = origin_len; i < extend_len; ++i) com[i] = cd (0,0);
    FFT(com, extend_len,1);//fft turn to Dot-valued notation
    for (int i = 0; i < extend_len; ++i) com[i] = com[i]*com[i];//square
    FFT(com, extend_len, -1);//ifft turn back to coe notation
    for (int i=0;i<extend_len;i++) cnt[i] = (long long) round(com[i].real());
    //4 she 5 ru, rounding off
    long long ans = 0;
    extend_len = coe[n-1]*2;
    for (int i = 0; i < n; ++i) cnt[coe[i]*2]--;//self+self
    for (int i = 0; i <= extend_len; ++i) cnt[i] /= 2;//front+behind, behind+front
    for (int i = 1; i <= extend_len; ++i) cnt[i] += cnt[i-1];
    for (long long i = 0; i < n; i++) {
        ans += cnt[extend_len] - cnt[coe[i]];//choose coe[i] as longest one
        ans -=  (i*(n-i-1) + (n-i-1)*(n-i-2)/2 + n-1);//long i!!!!
    }
    cout << ans << endl;

    return 0;
}