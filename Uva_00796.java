import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
            try {
                st = new StringTokenizer(br.readLine());
            }
            catch (Exception e) {
                f = true;
                return "-1";
            }
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

        while(!f)
            solve();

        w.close();
    }

    boolean f = false;

    void solve() {
        int n = ni();
        if(f) return;
        graph = new ArrayList<>();
        for(int i = 0; i < n; i++) graph.add(new ArrayList<>());

        for(int i = 0; i < n; i++) {
            int at = ni();
            String str = ns();
            int nn = Integer.parseInt(str.substring(1, str.length()-1));
            for(int j = 0; j < nn; j++) {
                graph.get(at).add(ni());
            }
        }

        if(n == 0) {
            p("0 critical links");
            pl();
            return;
        }

        vis = new boolean[n+1];
        bridges = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(!vis[i]) articulationPointsAndBridges(i);
        }
        for(int[] ar: bridges) Arrays.sort(ar);
        bridges.sort((a, b)->{
            if(a[0]!=b[0]) return Integer.compare(a[0], b[0]);
            else return Integer.compare(a[1], b[1]);
        });
        p(bridges.size()+" critical links");
        for(int[] ar: bridges) {
            p(ar[0]+" - "+ar[1]);
        }
        pl();
    }

    List<List<Integer>> graph;
    boolean[] aPt, vis;
    List<int[]> bridges;
    int[] dfsPt, dfs_Low, dfs_Num;
    int dfsCt, dfsRoot, dfsRoot_Ch;

    void articulationPointsAndBridges(int at) {
        int n = graph.size();
        if(bridges == null) bridges = new ArrayList<>();
        aPt = new boolean[n+1];
        if(vis == null) vis = new boolean[n+1];
        dfs_Low = new int[n+1];
        dfs_Num = new int[n+1];
        dfsPt = new int[n+1];
        dfsCt = 0;
        dfsRoot = at;
        dfsRoot_Ch = 0;

        dfs(at); // 0 for zero based vertices

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