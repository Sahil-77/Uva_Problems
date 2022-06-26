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
        int t = ni(); while(t-- > 0)
            solve();

        w.close();
    }

    void solve() {
        hasNegativeCycle = false;
        int n = ni(), m = ni();
        graph = new ArrayList<>();
        for(int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for(int i = 0; i < m; i++) {
            int a = ni(), b = ni(), c = ni();
            graph.get(a).add(new Edge(b, c));
        }

        checkNegativeCycle(0);

        if(hasNegativeCycle) p("possible");
        else p("not possible");
    }

    static List<List<Edge>> graph;
    static long[] dist;
    static long INF = (long)1e18;
    static boolean hasNegativeCycle = false;

    static void bf(int start) {
        int n = graph.size(); // check this for 1-based or 0-based;
        dist = new long[n+2];
        Arrays.fill(dist, INF);

        for(int i = 0; i < n-1; i++) {
            for(int node = 0; node < n; node++) {
                for(Edge ed: graph.get(node)) {
                    if(dist[ed.to] > dist[node]+ed.cost) {
                        dist[ed.to] = dist[node]+ed.cost;
                    }
                }
            }
        }
    }

    static void checkNegativeCycle(int start) {
        hasNegativeCycle = false;
        bf(start);
        int n = graph.size();

        // Run this v-1 times to get all the nodes present in negative cycle
        for(int node = 0; node < n; node++) {
            for(Edge ed: graph.get(node)) {
                if(dist[ed.to] > dist[node]+ed.cost) {
                    hasNegativeCycle = true;
                    break;
                }
            }
        }
    }

    static class Edge {
        int to;
        long cost;

        public Edge(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }
    }


}