import java.io.*;
import java.util.*;

//public class Main {
public class Lab10_B {

    static class fatherNode {
        int size = 2;
        int height;
        fatherNode father;
    }

    static class treeNode {
        fatherNode father;

        public fatherNode getRoot() {
            if (father == null) return null;
            fatherNode root = father;
            while (root.father != null) root = root.father;
            return root;
        }

    }

    static class Edge implements Comparable<Edge> {
        public int node1, node2, weight;

        public Edge(int n1, int n2, int w) {
            this.node1 = n1;
            this.node2 = n2;
            this.weight = w;
        }

        @Override
        public int compareTo(Edge edge) {//increase
            return this.weight - edge.weight;
        }

    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt(), m = in.nextInt(), a, b;
        treeNode[] node = new treeNode[n + 1];
        Edge edge;
        ArrayList<Edge> E = new ArrayList<>();

        for (int i = 1; i <= n; i++) node[i] = new treeNode();
        for (int i = 1; i < n; i++) {
            a = in.nextInt();
            b = in.nextInt();
            edge = new Edge(a, b, in.nextInt());
            E.add(edge);
        }

        Collections.sort(E);//sort in ascending
        long[] ans = new long[E.get(n-2).weight + 1];
        int temp_weight = E.get(0).weight, index = temp_weight;
        for (Edge e : E) {
            //System.out.println(e.weight);
            if (temp_weight < e.weight) {
                temp_weight = e.weight;
                while (index < temp_weight) ans[index + 1] = ans[index++];
            }
            fatherNode root1 = node[e.node1].getRoot();
            fatherNode root2 = node[e.node2].getRoot();
            if (root1 == null && root2 == null) {
                node[e.node1].father = new fatherNode();
                node[e.node2].father = node[e.node1].father;
                ans[index]++;
            } else if (root1 == null) {//node2 in path
                node[e.node1].father = root2;
                ans[index] += root2.size++;
            } else if (root2 == null) {//node1 in path
                node[e.node2].father = root1;
                ans[index] += root1.size++;
            } else {//both in path
                ans[index] += (long) root1.size * root2.size;
                if (root1.height > root2.height) {
                    root2.father = root1;
                    root1.size += root2.size;
                }else if (root1.height < root2.height){
                    root1.father = root2;
                    root2.size += root1.size;
                }else {
                    fatherNode f = new fatherNode();
                    f.size = root1.size + root2.size;
                    f.height = root1.height + 1;
                    root1.father = f;
                    root2.father = f;
                }
            }
        }

        while (m-- > 0) {
            a = in.nextInt();
            if (a >= ans.length) System.out.print(ans[ans.length - 1]+" ");
            else if (a < 0) System.out.print(0+" ");
            else System.out.print(ans[a]+" ");
        }

        out.close();
    }

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
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

        public double nextDouble() throws IOException {
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

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

}