import java.io.*;
import java.util.*;

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

        while(!f) {
            tc++;
            solve();
        }

        w.close();
    }

    boolean f = false;
    int tc = 0;

    void solve() {
        createGraph(100);
        boolean okay = false;
        HashSet<Integer> hs = new HashSet<>();
        while(true) {
            int from = ni()-1, to = ni()-1;
            if(from == -1 && !okay) {
                f = true;
                return;
            } else if(from == -1) break;

            okay = true;
            graph[from][to] = 1;
            hs.add(from);
            hs.add(to);
        }

        fw();
        List<Integer> nodes = new ArrayList<>(hs);
        int n = nodes.size();
        int total = n*(n-1);
        long sum = 0;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i == j) continue;

                sum += dist[nodes.get(i)][nodes.get(j)];
            }
        }

        p(String.format("Case %d: average length between pages = %.3f clicks", tc, (sum/(double)total)));
    }

    long[][] graph; // adjacency matrix required for floyd warshall
    long inf = (long)1e15;
    int[][] prev;
    long[][] dist;
    boolean hasNegativeCycle;

    void createGraph (int n) {
        graph = new long[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i != j) graph[i][j] = inf;
            }
        }
    }

    void fw () {
        int n = graph.length;

        dist = new long[n][n];
        for(int i = 0; i < n; i++) {
            dist[i] = graph[i].clone();
        }

        prev = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                prev[i][j] = i;
            }
        }

        for(int k = 0; k < n; k++) {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    long itoj = dist[i][j];
                    if(dist[i][k] == inf || dist[k][j] == inf) continue;
                    long intermediate = dist[i][k]+dist[k][j];
                    if(intermediate < itoj) {
                        dist[i][j] = intermediate;
                        prev[i][j] = prev[k][j];
                    }
                }
            }
        }
    }

    // run floyd warshall again and check if distance decreases
    void checkNegativeCycle () {
        fw();
        int n = dist.length;
        for(int i = 0; i < n; i++) {
            if(dist[i][i]<0) { hasNegativeCycle = true; break; }
        }
    }

    List<Integer> path(int i, int j) {
        if(dist[i][i] < 0 || dist[j][j] < 0) return null; // negative
        if(dist[i][j] >= inf) return null; // no path
        List<Integer> path = new ArrayList<>();
        while(j!=i) {
            path.add(j);
            j = prev[i][j];
        }
        path.add(i);
        Collections.reverse(path);
        return path;
    }
}