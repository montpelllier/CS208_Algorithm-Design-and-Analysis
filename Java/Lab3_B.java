import java.util.*;

public class Lab3_B {

    static class Node{
        int letter;
        int beauty;
        Node up;
        Node low;
        LinkedList<Integer> upLetter = new LinkedList<>();
    }

    public static boolean isConsonants(char ch){
        return (ch!='a' && ch!='e' && ch!='i' && ch!='o' && ch!='u');
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int t = in.nextInt(), ans, temp = 0;
        long startTime = System.currentTimeMillis();
        int[][] matrix;
        int[] sum;
        String str;
        char[] text;
        Node root, pop;
        Stack<Node> stack = new Stack<>();

        HashMap<Character, Integer> alphabet = new HashMap<>(21);
        for (int i=0;i<26;i++){
            char ch = 'a';
            if(isConsonants((char)(ch+i))) alphabet.put((char)(ch+i),temp++);
        }

        while (t-->0) {
            ans = 0;
            root = new Node();
            sum = new int[21];
            matrix = new int[21][21];
            str = in.next();
            text = str.toCharArray();

            for (int i = 0; i < text.length - 1; i++) {
                if (text[i] != text[i + 1] && isConsonants(text[i]) && isConsonants(text[i + 1])) {
                    matrix[alphabet.get(text[i])][alphabet.get(text[i + 1])]++;
                    matrix[alphabet.get(text[i + 1])][alphabet.get(text[i])]++;
                }
            }

            for (int i = 0; i < 21; i++) for (int j = 0; j < 21; j++) sum[i] += matrix[i][j];
            //bfs laji, dfs good!
            root.beauty = 0;
            root.letter = 0;
            stack.push(root);
            while (!stack.isEmpty()){
                pop = stack.pop();
                temp = pop.beauty + sum[pop.letter+1];
                for (Integer j : pop.upLetter) temp -= 2 * matrix[pop.letter+1][j];
                if (pop.letter < 19){
                    pop.low = new Node();
                    pop.low.beauty = pop.beauty;
                    pop.low.letter = pop.letter+1;
                    pop.low.upLetter = pop.upLetter;
                    stack.push(pop.low);
                    if (temp > pop.beauty) {
                        pop.up = new Node();
                        pop.up.beauty = temp;
                        pop.up.letter = pop.letter + 1;
                        pop.up.upLetter.addAll(pop.upLetter);
                        pop.up.upLetter.add(pop.letter + 1);
                        stack.push(pop.up);
                        if (ans < temp) ans = temp;
                    }
                }else if (pop.letter == 19 && ans < temp) ans = temp;
            }
            System.out.println(ans);
        }
        in.close();
        long overTime = System.currentTimeMillis();      //获取结束时间
        System.out.println("程序运行时间为："+(overTime-startTime)+"毫秒");
    }
}