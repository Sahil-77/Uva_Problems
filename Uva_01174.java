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
    int testCase;

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
        testCase = ni(); while(testCase-- > 0)
            solve();

        w.close();
    }

    void solve() {
        int n = ni();
        int m = ni();
        HashMap<String, Integer> hm = new HashMap<>();
        graph = new ArrayList<>();
        for(int i = 0; i < n; i++) graph.add(new ArrayList<>());
        int at = 0;
        for(int i = 0; i < m; i++) {
            String from = ns(), to = ns();
            long cost = nl();
            if(!hm.containsKey(from)) hm.put(from, at++);
            if(!hm.containsKey(to)) hm.put(to, at++);
            int id1 = hm.get(from), id2 = hm.get(to);
            graph.get(id1).add(new Edge(id2, cost));
            graph.get(id2).add(new Edge(id1, cost));
        }
        long cost = prims();
        p(cost);
        if(testCase > 0) pl();
    }

    List<List<Edge>> graph;
    List<Edge> mst;

    long prims () {
        int n = graph.size();
        boolean[] taken = new boolean[n];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.cost));
        for(Edge ed: graph.get(0)) pq.add(new Edge(ed.to, ed.cost));
        mst = new ArrayList<>();
        taken[0] = true;

        long cost = 0;

        while(!pq.isEmpty()) {
            Edge ed = pq.poll();
            if(taken[ed.to]) continue;
            cost += ed.cost;
            taken[ed.to] = true;
            for(Edge currEd: graph.get(ed.to)) pq.add(new Edge(currEd.to, currEd.cost));
            mst.add(ed);
        }

        return cost;
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