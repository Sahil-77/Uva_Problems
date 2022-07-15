import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static Main2 admin = new Main2();

    public static void main(String[] args) {
        admin.start();
    }
}

class Main2 {
    //---------------------------------INPUT READER-----------------------------------------//
    public BufferedReader br;
    StringTokenizer st = new StringTokenizer("");
    String next() {
        while (!st.hasMoreTokens()) {
            try { st = new StringTokenizer(br.readLine());} catch (IOException e) { e.printStackTrace(); }
        }
        return st.nextToken();
    }

    int ni() { return Integer.parseInt(next()); }
    long nl() { return Long.parseLong(next()); }
    double nd() { return Double.parseDouble(next()); }
    String ns() { return next(); }

    int[] na(long n) {int[]ret=new int[(int)n]; for(int i=0;i<n;i++) ret[i]=ni(); return ret;}
    long[] nal(long n) {long[]ret=new long[(int)n]; for(int i=0;i<n;i++) ret[i]=nl(); return ret;}
    Integer[] nA(long n) {Integer[]ret=new Integer[(int)n]; for(int i=0;i<n;i++) ret[i]=ni(); return ret;}
    Long[] nAl(long n) {Long[]ret=new Long[(int)n]; for(int i=0;i<n;i++) ret[i]=nl(); return ret;}

    //--------------------------------------PRINTER------------------------------------------//
    PrintWriter w;
    void p(int i) {w.println(i);} void p(long l) {w.println(l);}
    void p(double d) {w.println(d);} void p(String s) { w.println(s);}
    void pr(int i) {w.print(i);} void pr(long l) {w.print(l);}
    void pr(double d) {w.print(d);} void pr(String s) { w.print(s);}
    void pl() {w.println();}

    //--------------------------------------VARIABLES-----------------------------------------//
    long lma = Long.MAX_VALUE, lmi = Long.MIN_VALUE;
    int ima = Integer.MAX_VALUE, imi = Integer.MIN_VALUE;
    long mod = 1000000007;

    {
        w = new PrintWriter(System.out);
        br = new BufferedReader(new InputStreamReader(System.in));
        try {if(new File(System.getProperty("user.dir")).getName().equals("LOCAL")) {
            w = new PrintWriter(new OutputStreamWriter(new FileOutputStream("output.txt")));
            br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));}
        } catch (Exception ignore) { }
    }

    //----------------------START---------------------//
    void start() {
//        int t = ni();

        while(!f)
            solve();

        w.close();
    }

    boolean f = false;

    void solve() {
        int n = ni(), m = ni();
        if(n == 0 && m == 0) {
            f = true;
            return;
        }

        graph = new ArrayList<>();
        for(int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for(int i = 0; i < m; i++) {
            int a = ni()-1, b = ni()-1, p = ni();
            graph.get(a).add(b);
            if(p==2) graph.get(b).add(a);
        }

        scc();

        if(sccs.size() == 1 && sccs.get(0).size() == n) {
            p(1);
        } else p(0);
    }

    List<List<Integer>> graph;
    int[] dfsLow, dfsNum;
    boolean[] vis;
    List<List<Integer>> sccs;
    int dfsCurr;
    Stack<Integer> stk;

    void scc() {
        int n = graph.size();
        dfsLow = new int[n+1];
        dfsNum = new int[n+1];
        vis = new boolean[n+1];
        sccs = new ArrayList<>();
        dfsCurr = 0;
        stk = new Stack<>();

        dfs(0);
    }

    void dfs(int at) {
        dfsLow[at] = dfsNum[at] = dfsCurr++;
        vis[at] = true;
        stk.push(at);

        for(int i: graph.get(at)) {
            if(!vis[i]) dfs(i);

            dfsLow[at] = Math.min(dfsLow[i], dfsLow[at]);
        }

        if(dfsLow[at] == dfsNum[at]) {
            List<Integer> currScc = new ArrayList<>();
            while(true) {
                int pop = stk.pop();
                currScc.add(pop);
                if(pop == at) break;
            }
            sccs.add(currScc);
        }
    }
}