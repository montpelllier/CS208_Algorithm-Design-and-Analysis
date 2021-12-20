import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Lab3_B2 {
    static int ans = 0;
    static int res = 0;
    static int pos = 0;
    static int[] stack = new int[22];
    static int[] count = new int[22];

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader scan = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        int t = scan.nextInt();
        for (int i = 1; i <= t; i++) {
            initial();
            int[][] arr = new int[22][22];
            String s = scan.next();
            int length = s.length();
            for (int j = 0; j <= length - 2; j++) {
                int a = getCode(s.charAt(j));
                int b = getCode(s.charAt(j + 1));
                if (a > 0 && b > 0) {
                    arr[a][b]++;
                    arr[b][a]++;
                }
            }
            for (int j = 1; j <= 21; j++) {
                for (int k = 1; k <= 21; k++) {
                    if (j != k) {
                        count[j] += arr[j][k];
                    }
                }
            }
            DFS(1, arr);
            System.out.println(ans);
        }
    }

    public static void initial() {
        for (int i = 1; i <= 21; i++) {
            stack[i] = 0;
            count[i] = 0;
        }
        ans = 0;
        res = 0;
        pos = 0;
    }

    public static void DFS(int depth, int[][] arr) {
        if (depth == 22) {
            if (res > ans) {
                ans = res;
            }
            return;
        }
        //the (depth) letter is lowercase
        DFS(depth + 1, arr);
        //the (depth) letter is uppercase
        int temp = res;
        res += count[depth];
        for (int i = 1; i <= pos; i++) {
            res -= 2 * arr[stack[i]][depth];
        }
        pos++;
        stack[pos] = depth;
        DFS(depth + 1, arr);
        pos--;
        res = temp;
    }

    public static int getCode(char c) {
        if (c == 97 || c == 101 || c == 105 || c == 111 || c == 117) {
            return -1;
        }
        if (c < 101) {
            return c - 97;
        } else if (c < 105) {
            return c - 98;
        } else if (c < 111) {
            return c - 99;
        } else if (c < 117) {
            return c - 100;
        } else {
            return c - 101;
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] nextCharArray() {
            return next().toCharArray();
        }
    }
}