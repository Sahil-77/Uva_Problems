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

        while(!f)
            solve();

        w.close();
    }

    boolean f = false;

    void solve() {
        int n = ni();
        if(n == 0) {f = true; return;}
        List<List<edge>> yGraph = new ArrayList<>(), aGraph = new ArrayList<>();
        for(int i = 0; i <= 26; i++) {
            yGraph.add(new ArrayList<>());
            aGraph.add(new ArrayList<>());
        }
        int[] hash = new int[26];
        int[] revHash = new int[26];
        Arrays.fill(hash, -1);
        Arrays.fill(revHash, -1);
        int at = 0;

        for(int i = 0; i < n; i++) {
            String young = ns(), dir = ns();
            char from = ns().charAt(0), to = ns().charAt(0);
            if(hash[from-'A']==-1) {
                hash[from-'A']=at;
                revHash[at] = from-'A';
                at++;
            }

            if(hash[to-'A']==-1) {
                hash[to-'A']=at;
                revHash[at] = to-'A';
                at++;
            }

            int f = hash[from-'A'], t = hash[to-'A'];

            int cost = ni();
            if(young.equals("Y")) {
                if(dir.equals("U")) {
                    yGraph.get(f).add(new edge(t, cost));
                } else {
                    yGraph.get(f).add(new edge(t, cost));
                    yGraph.get(t).add(new edge(f, cost));
                }
            } else {
                if(dir.equals("U")) {
                    aGraph.get(f).add(new edge(t, cost));
                } else {
                    aGraph.get(f).add(new edge(t, cost));
                    aGraph.get(t).add(new edge(f, cost));
                }
            }
        }

        createGraph(26);

        for(int i = 0; i < 26; i++) {
            for(edge ed: yGraph.get(i)) {
                graph[i][ed.to]=Math.min(graph[i][ed.to], ed.cost);
            }
        }

        fw();

        long[][] distY = dist.clone();

        createGraph(26);

        for(int i = 0; i < 26; i++) {
            for(edge ed: aGraph.get(i)) {
                graph[i][ed.to]=Math.min(graph[i][ed.to], ed.cost);
            }
        }

        fw();

        long[][] distA = dist.clone();

        char child = ns().charAt(0), adult = ns().charAt(0);
        int ch = hash[child-'A'], ad = hash[adult-'A'];
        if(ch == -1 || ad == -1) {
            p("You will never meet.");
            return;
        }

        long min = lma;
        List<Character> places = new ArrayList<>();

        for(int i = 0; i < 26; i++) {
            long costA = distA[ad][i];
            long costC = distY[ch][i];
            long tot = costA+costC;

            if(tot >= inf) continue;

            if(min > tot) {
                min = tot;
                places = new ArrayList<>();
                places.add((char)(revHash[i]+'A'));
            } else if(min == tot) {
                places.add((char)(revHash[i]+'A'));
            }
        }

        if(min == lma) {
            p("You will never meet.");
            return;
        }

        places.sort(Integer::compare);

        pr(min+" ");
        for(int i = 0; i < places.size()-1; i++) pr(places.get(i)+" ");
        p(places.get(places.size()-1)+"");
    }

    class edge {
        int to, cost;

        public edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
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