import java.io.*;
import java.util.*;

public class Main {
    static ADMIN admin = new ADMIN();

    public static void main(String[] args) {
        admin.start();
    }
}

class ADMIN {
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

    void start() {
        //int t = ni(); while(t-- > 0)
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
        for(int i = 0; i <= n+1; i++) {
            graph.add(new ArrayList<>());
        }

        int cid = 1;
        String start = ns(), end = ns();
        HashMap<String, List<Integer>> hm = new HashMap<>();
        HashMap<String, Integer> id = new HashMap<>();
        HashMap<Integer, String> revId = new HashMap<>();

        for(int i = 0; i < n; i++) {
            String a = ns(), b = ns(), c = ns();
            List<Integer> li = new ArrayList<>();
            int currId = cid++;
            id.put(c, currId);
            revId.put(currId, c);
            li = hm.getOrDefault(a, new ArrayList<>());
            li.add(currId);
            hm.put(a, li);
            li = hm.getOrDefault(b, new ArrayList<>());
            li.add(currId);
            hm.put(b, li);
        }

        if(!hm.containsKey(start) || !hm.containsKey(end)) {
            p("impossivel");
            return;
        }

        for(int i: hm.get(start)) {
            graph.get(0).add(new Edge(i, revId.get(i).length()));
        }

        for(int i: hm.get(end)) {
            graph.get(i).add(new Edge(n+1, 0));
        }

        for(String s: hm.keySet()) {
            List<Integer> li = hm.get(s);
            int len = li.size();
            for(int i = 0; i < len; i++) {
                for(int j = i+1; j < len; j++) {
                    int a = li.get(i), b = li.get(j);
                    String s1 = revId.get(a), s2 = revId.get(b);
                    if(s1.charAt(0)==s2.charAt(0)) continue;
                    graph.get(a).add(new Edge(b, s2.length()));
                    graph.get(b).add(new Edge(a, s1.length()));
                }
            }
        }

        dj(0);
        if(dist[n+1] >= INF) {
            p("impossivel");
            return;
        }
        p(dist[n+1]);
    }

    static long[] dist;
    static List<List<Edge>> graph;
    static long INF = (long)1e18;
    static int[] prev;

    static public void dj(int start) {
        int n = graph.size();
        dist = new long[n];
        prev = new int[n];
        Arrays.fill(dist, INF);

        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b)-> {
            if(a.cost != b.cost) return Long.compare(a.cost, b.cost);
            else return Integer.compare(a.to, b.to);
        });

        pq.add(new Edge(start, 0));

        while(!pq.isEmpty()) {
            Edge edge = pq.poll();
            int at = edge.to;
            long d = edge.cost;

            if(dist[at] < d) continue;

            for(Edge ed: graph.get(at)) {
                int to = ed.to;
                long cost = ed.cost;

                if(dist[to] <= cost+d) continue;

                dist[to] = cost+d;
                prev[to] = at;
                pq.add(new Edge(to, cost+d));
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