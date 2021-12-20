import java.io.*;
import java.util.*;

public class Lab5_B {

    static final int root = 1;
    static class Node{
        ArrayList<Edge> next = new ArrayList<>();
    }

    static class Edge{
        int nd1;
        int nd2;
        int cost;
    }

    static int son(Edge edge, int father){
        if (edge.nd1 == father) return edge.nd2;
        else if (edge.nd2 == father) return edge.nd1;
        else return -1;
    }

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        int n = in.nextInt(), cur, next, temp, cost=0, income;
        long  ans=0, hp;
        int[] HP=new int[n+1], father=new int[n+1];
        long[] profit = new long[n+1];
        boolean isVisited[] = new boolean[n+1], lock;
        Node node[] = new Node[n+1];
        Edge edge[] = new Edge[n];
        Edge e;
        for (int i=1;i<n;i++) edge[i] = new Edge();

        for (int i=1;i<=n;i++) {
            node[i] = new Node();
            HP[i] = in.nextInt();
        }
        for (int i=1;i<n;i++){
            cur = in.nextInt();
            next = in.nextInt();
            edge[i].nd1 = cur;
            edge[i].nd2 = next;
            edge[i].cost = in.nextInt();
            node[cur].next.add(edge[i]);
            node[next].next.add(edge[i]);
        }

        father[0] = -1;
        cur = root;//represent current node
        do {
            next = -1;
            for (Edge i:node[cur].next){
                temp = son(i,cur);
                if(!isVisited[temp] && temp!=father[cur]){
                    next = temp;
                    cost = i.cost;
                    break;
                }//connected, not visited, not father
            }
            if (next == -1){//no node to down
                temp = father[cur];
                isVisited[cur] = true;
                profit[cur] += HP[cur];
                profit[cur] -= cost;
                profit[cur] -= cost;//profit=hp-2*cost
                profit[temp] += profit[cur];
                cur = temp;//go up
            } else {
                father[next] = cur;//record its father
                cur = next;//go down
            }
        } while (!isVisited[root]);

        Stack<Integer> stack = new Stack<>();
        isVisited = new boolean[isVisited.length];
        hp = HP[root];
        cur = root;//initialization
        do {
            lock = false;
            e = edge[0];
            next = -1;
            income = -1;
            cost = 2_147_483_647;
            for (Edge i:node[cur].next){
                temp = son(i,cur);
                if(!isVisited[temp] && temp!=father[cur]){
                    if (profit[temp]>=0 && i.cost<cost){ //lower cost first
                        cost = i.cost;
                        next = temp;
                        e = i;
                        lock = true;
                    }else if(profit[temp]<0 && !lock && HP[temp]>income){//higher income first
                        income = HP[temp];
                        next = temp;
                        e = i;
                    }
                }
            }

            if (next == -1){//no node to down
                temp = father[cur];//0 when cur=1
                isVisited[cur] = true;
                if (!stack.isEmpty()) hp -= stack.pop();
                if (hp < 0) {
                    ans -= hp;
                    hp = 0;
                }
                cur = temp;//go up
            } else {
                hp -= e.cost;
                stack.push(e.cost);
                if (hp < 0) {
                    ans -= hp;
                    hp = 0;
                }
                hp += HP[next];
                cur = next;//go down
            }
        } while (!isVisited[root]);

        out.println(ans);
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