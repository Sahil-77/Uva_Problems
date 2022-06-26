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
    long mod = lma;

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
        //int t = ni(); while(t-- > 0)
        while(!f) {
            set++;
            solve();
        }

        w.close();
    }

    boolean f = false;
    int set = 0;

    void solve() {
        int n;
        try {
            n = ni();
        } catch (Exception e) {f = true; return;}
        p("Set #"+set);

        graph = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        int[] vals = na(n);

        int m = ni();

        for(int i = 0; i < m; i++) {
            int a = ni()-1, b = ni()-1;
            graph.get(a).add(new Edge(b, mp(vals[b]-vals[a], 3)));
        }

        checkNegativeCycle(0);
        int q = ni();
        while(q-- > 0) {
            int to = ni()-1;
            long curr = dist[to];
            if(negatives[to] || curr < 3 || curr >= INF) {
                p("?");
            } else p(curr);
        }
    }

    long mp (long b, long x) {
        if (x == 0) return 1;
        if (x == 1) return b;
        if (x % 2 == 0) return mp (b * b % mod, x / 2) % mod;

        return b * mp (b * b % mod, x / 2) % mod;
    }

    List<List<Edge>> graph;
    long[] dist;
    long INF = (long)1e17;
    boolean hasNegativeCycle = false;
    boolean[] negatives;

    void bf(int start) {
        int n = graph.size(); // check this for 1-based or 0-based;
        dist = new long[n+2];
        Arrays.fill(dist, INF);
        dist[start] = 0;

        for(int i = 0; i < n-1; i++) {
            for(int node = 0; node < n; node++) {
                for(Edge ed: graph.get(node)) {
                    if(dist[node] != INF && dist[ed.to] > dist[node]+ed.cost) {
                        dist[ed.to] = dist[node]+ed.cost;
                    }
                }
            }
        }
    }

    void checkNegativeCycle(int start) {
        hasNegativeCycle = false;
        bf(start);
        int n = graph.size();
        negatives = new boolean[n];

        // Run this v-1 times to get all the nodes present in negative cycle
        for(int i = 0; i < n-1; i++) {
            for (int node = 0; node < n; node++) {
                for (Edge ed : graph.get(node)) {
                    if ((dist[ed.to] > dist[node] + ed.cost) || negatives[node]) {
                        hasNegativeCycle = true;
                        negatives[ed.to] = true;
                    }
                }
            }
        }
    }

    class Edge {
        int to;
        long cost;

        public Edge(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }
    }
}