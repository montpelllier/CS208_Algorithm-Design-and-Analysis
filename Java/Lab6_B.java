import java.io.*;
import java.util.*;

public class Lab6_B {

    static class Node {
        int index;
        long shortest = Long.MAX_VALUE;
        LinkedList<Edge> next = new LinkedList<>();
    }

    static class Edge {
        int front, next, weight;
    }

    static Comparator<Node> com = new Comparator<>() {
        @Override
        public int compare(Node nd1, Node nd2) {
            if (nd1.shortest-nd2.shortest<0) return -1;
            else if (nd1.shortest== nd2.shortest) return 0;
            else return 1;
        }
    };

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        int n=in.nextInt(), m=in.nextInt(), rest, cost;
        int[] red = new int[n+1], green = new int[n+1];
        //long time_in[] = new long[n+1];
        boolean[] isvisited = new boolean[n+1], inQueue = new boolean[n+1];
        Node node[] = new Node[n+1], poll;
        Edge edge;

        for (int i=1;i<=n;i++) node[i] = new Node();

        while (m-->0){
            edge = new Edge();
            edge.front = in.nextInt();
            edge.next = in.nextInt();
            edge.weight = in.nextInt();
            node[edge.front].next.add(edge);
        }

        for (int i=1;i<=n;i++){
            red[i] = in.nextInt();
            green[i] = in.nextInt();
        }

        PriorityQueue<Node> minheap = new PriorityQueue<Node>(com);
        node[1].shortest = 0;
        minheap.add(node[1]);
        while (!minheap.isEmpty()){
            poll = minheap.poll();
            isvisited[poll.index] = true;
            //System.out.println(poll.shortest);
            for (Edge e:poll.next) {
                if (!isvisited[e.next] && green[e.next]!=0){
                    cost = e.weight;
                    rest = (int) (poll.shortest+e.weight) % (red[e.next] + green[e.next]);
                    //calculate weight first!!!!!!!!
                    if (rest < red[e.next]) cost += red[e.next] - rest;
                    if (node[e.next].shortest > poll.shortest+cost){
                        node[e.next].shortest = poll.shortest+cost;
                        if (inQueue[e.next]) minheap.remove(node[e.next]);
                        minheap.add(node[e.next]);
                        inQueue[e.next] = true;
                    }
                }
            }
        }

        System.out.println(node[n].shortest);
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