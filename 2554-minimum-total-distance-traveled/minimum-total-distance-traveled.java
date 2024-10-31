class Solution {
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        Collections.sort(robot);
        Arrays.sort(factory, (a, b) -> Integer.compare(a[0], b[0]));
        int m = robot.size();
        int n = factory.length; 
        long[][] dp = new long[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            dp[i][n] = Long.MAX_VALUE;
        }
        for (int j = n - 1; j >= 0; j--) {
            long prefix = 0;
            Deque<Pair<Integer, Long>> qq = new ArrayDeque<>();
            qq.offer(new Pair<>(m, 0L));
            for (int i = m - 1; i >= 0; i--) {
                prefix += Math.abs(robot.get(i) - factory[j][0]);
                while (!qq.isEmpty() && qq.peekFirst().getKey() > i + factory[j][1]) {
                    qq.pollFirst();
                }
                while (!qq.isEmpty() && qq.peekLast().getValue() >= dp[i][j + 1] - prefix) {
                    qq.pollLast();
                }
                qq.offerLast(new Pair<>(i, dp[i][j + 1] - prefix));
                dp[i][j] = qq.peekFirst().getValue() + prefix;
            }
        }
        
        return dp[0][0];
    }
    
    private static class Pair<K, V> {
        private K key;
        private V value;
        
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
    }
}