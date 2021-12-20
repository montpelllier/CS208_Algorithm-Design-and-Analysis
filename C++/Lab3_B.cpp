//
// Created by Think on 2021/3/13.
//
#include <iostream>
#include <map>
#include <list>
#include <stack>
using namespace std;

struct Node{
    int letter;
    int beauty;
    struct Node *up;
    struct Node *low;
    list<int> upLetter;
};

bool isConsonants(char ch){
    return (ch!='a' && ch!='e' && ch!='i' && ch!='o' && ch!='u');
}

int main() {
    int t, ans, temp = 0;
    string str;
    cin >> t;

    stack<Node> stack;

    map<char,int> alphabet;
    for (int i = 0; i < 26; ++i) {
        char ch = 'a';
        if (isConsonants((char) (ch+i))) alphabet[(char)(ch+i)] = temp++;
    }

    while (t-->0){
        ans = 0;
        Node root;
        Node pop;
        int sum[21];
        int matrix[21][21] = {};

        for (int i = 0; i < 21; ++i) {
            sum[i] = 0;
            for (int j = 0; j < 21; ++j) {
                matrix[i][j] = 0;
            }
        }
        cin >> str;

        for (int i = 0; i < str.length()-1; ++i) {
            if (str[i]!=str[i+1] && isConsonants(str[i]) && isConsonants(str[i+1])){
                matrix[alphabet[str[i]]][alphabet[str[i+1]]]++;
                matrix[alphabet[str[i+1]]][alphabet[str[i]]]++;
            }
        }

        for (int i = 0; i < 21; ++i) {
            for (int j = 0; j < 21; ++j) {
                sum[i] += matrix[i][j];
            }
        }

        root.beauty = 0;
        root.letter = 0;
        stack.push(root);
        while (!stack.empty()){
            pop = stack.top();
            stack.pop();
            temp = pop.beauty + sum[pop.letter+1];
            for (int j : pop.upLetter) temp -= 2 * matrix[pop.letter+1][j];
            if (pop.letter < 19){
                //struct Node pop.low = new struct Node();
                pop.low->beauty = pop.beauty;
                pop.low->letter = pop.letter+1;
                pop.low->upLetter = pop.upLetter;
                stack.push(*pop.low);

                if (temp > pop.beauty) {
                    //struct Node pop.up;
                    pop.up->beauty = temp;
                    pop.up->letter = pop.letter + 1;
                    pop.up->upLetter.assign(pop.upLetter.begin(),pop.upLetter.end());
                    pop.up->upLetter.push_back(pop.letter + 1);
                    stack.push(*pop.up);
                }
            }
            if (ans < temp) ans = temp;
        }
        cout << ans << endl;

    }
}
