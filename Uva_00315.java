import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    String nline() {
        try {
            return br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

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
        int n = ni();
        if(n == 0) {
            f = true;
            return;
        }

        graph = new ArrayList<>();
        for(int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        while(true) {
            String[] next = nline().split(" ");
            if(next[0].equals("0")) break;

            for(int j = 1; j < next.length; j++) {
                int a = Integer.parseInt(next[0]), b = Integer.parseInt(next[j]);
                graph.get(a).add(b);
                graph.get(b).add(a);
            }
        }

        articulationPointsAndBridges(1);

        int ct = 0;
        for(boolean i: aPt) if(i) ct++;
        p(ct);
    }

    List<List<Integer>> graph;
    boolean[] aPt, vis;
    List<int[]> bridges;
    int[] dfsPt, dfs_Low, dfs_Num;
    int dfsCt, dfsRoot, dfsRoot_Ch;

    void articulationPointsAndBridges(int at) {
        int n = graph.size();
        bridges = new ArrayList<>();
        aPt = new boolean[n+1];
        vis = new boolean[n+1];
        dfs_Low = new int[n+1];
        dfs_Num = new int[n+1];
        dfsPt = new int[n+1];
        dfsCt = 0;
        dfsRoot = at;
        dfsRoot_Ch = 0;

        dfs(1); // 0 for zero based vertices

        // check for dfs_root
        if(dfsRoot_Ch > 1) {
            aPt[at] = true;
        } else aPt[at] = false;
    }

    void dfs(int at) {
        dfs_Low[at] = dfs_Num[at] = dfsCt++;
        vis[at] = true;

        for(int i: graph.get(at)) {
            if(!vis[i]) {
                dfsPt[i] = at;
                if(at == dfsRoot) dfsRoot_Ch++;

                dfs(i);

                if(dfs_Low[i] >= dfs_Num[at]) {
                    aPt[at] = true;
                }

                if(dfs_Low[i] > dfs_Num[at]) {
                    bridges.add(new int[] {i, at});
                }

                dfs_Low[at] = Math.min(dfs_Low[at], dfs_Low[i]);

            } else if(i != dfsPt[at]){
                dfs_Low[at] = Math.min(dfs_Low[at], dfs_Num[i]);
            }
        }
    }
}