import java.io.*;
import java.util.*;

public class Lab4_B {

    static int initial_root = 1;
    static long total_weight = 0;

    static class Node {
        LinkedList<Integer> next = new LinkedList<>();
    }

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        int n = in.nextInt(), a, b, depth=0, current, next;
        Node node[] = new Node[n+1];
        boolean[] isVisited = new boolean[n+1];
        int[] weight = new int[n+1];
        int[] father = new int[n+1];
        long[] sum = new long[n+1];
        long[] beauty = new long[n+1];
        long ans = 0;
        for (int i=1;i<=n;i++) {
            node[i] = new Node();
            weight[i] = in.nextInt();
            total_weight += weight[i];
        }
        while (n-->1) {
            a = in.nextInt();
            b = in.nextInt();
            node[a].next.add(b);
            node[b].next.add(a);
        }

        current = initial_root;//=1
        do {//until root is visited
            next = -1;
            for (int i : node[current].next){
                if (!isVisited[i] && i!=father[current]) {
                    next = i;//unvisited, child
                    break;
                }
            }
            if (next == -1){//no node to down
                a = father[current];
                isVisited[current] = true;
                beauty[initial_root] += depth*weight[current];//calculate beauty of root
                sum[current] += weight[current];//sum of weight including itself and its children
                sum[a] += sum[current];//add to father
                current = a;//go up
                depth--;
            } else {
                father[next] = current;//record its father
                current = next;//go down
                depth++;
            }
        } while (!isVisited[initial_root]);

        isVisited = new boolean[isVisited.length];
        isVisited[initial_root] = true;//initialization
        Queue<Integer> queue = new LinkedList<>();
        queue.addAll(node[initial_root].next);//start from root
        while (!queue.isEmpty()){
            current = queue.poll();//set current as the root
            a = father[current];
            beauty[current] = beauty[a]+sum[a]-2*sum[current];
            sum[current] = total_weight;
            isVisited[current] = true;
            for (int i : node[current].next) if (!isVisited[i]) queue.add(i);
        }

        for (long i : beauty ) if (ans < i) ans = i;
        out.println(ans);//max beauty, O(N)

        out.close();
    }


    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }

}