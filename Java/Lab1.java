import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Lab1 {//stable match, time complexity O(N^2)

    public static void main(String[] args) {
        InputStream inputStream = System.in;// new FileInputStream("C:\\Users\\wavator\\Downloads\\test.in");
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    static class Task {

        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            String name;
            // get the hashmaps and arrays of names
            HashMap<String, Integer> boyMap = new HashMap<>(n);
            HashMap<String, Integer> girlMap = new HashMap<>(n);
            String[] boys = new String[n], girls = new String[n];
            for (int i=0;i<n;i++){
                name = in.next();
                boys[i] = name;
                boyMap.put(name, i);
            }
            for (int i=0;i<n;i++){
                name = in.next();
                girls[i] = name;
                girlMap.put(name, i);
            }
            //get preferences
            int[][] boyPrefer = new int[n][n];
            int[][] girlPrefer = new int[n][n];
            for (int i=0;i<n;i++){
                for (int j=0;j<n;j++){
                    boyPrefer[i][j] = girlMap.get(in.next());//store girls' ids(preference declines)
                }
            }
            for (int i=0;i<n;i++){
                for (int j=0;j<n;j++){
                    girlPrefer[i][boyMap.get(in.next())] = j;//store the preference of boys(smaller means prefer)
                }
            }
            //start matching
            int boyid, girlid, currentboy;
            Queue<Integer> singleBoy = new LinkedList<>();
            int[] matchstateBoy = new int[n], matchstateGirl = new int[n];
            for (int i=0;i<n;i++){
                matchstateBoy[i] = 0;//boyPrefer[i][0];//start from boys' most like one
                matchstateGirl[i] = -1;//-1 represent the girl is single
            }
            for(int i=0;i<n;i++) singleBoy.add(i);//all boys are single at first
            while (!singleBoy.isEmpty()){
                boyid = singleBoy.poll();
                while(true){
                    girlid = boyPrefer[boyid][matchstateBoy[boyid]];
                    currentboy = matchstateGirl[girlid];
                    if(currentboy == -1) {
                        matchstateGirl[girlid] = boyid;
                        break;
                    }else if(girlPrefer[girlid][boyid] < girlPrefer[girlid][currentboy]) {//green
                        matchstateGirl[girlid] = boyid;
                        singleBoy.add(currentboy);
                        break;
                    }else matchstateBoy[boyid]++;//rejected, next one
                }
            }
            for (int i=0;i<n;i++) out.println(boys[i]+" "+girls[boyPrefer[i][matchstateBoy[i]]]);

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

        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }


    }

}
