package assignment2;

public class CoinCombination {
    
    public static int countWays(int[] coins, int sum) {
        // dp[i]는 금액 i를 만들 수 있는 방법의 수를 저장
        int[] dp = new int[sum + 1];
        
        // 금액 0을 만드는 방법은 1가지 (아무 동전도 선택하지 않음)
        dp[0] = 1;
        
        // 각 동전에 대해
        for (int coin : coins) {
            // 현재 동전으로 만들 수 있는 모든 금액에 대해
            for (int amount = coin; amount <= sum; amount++) {
                // 현재 금액에서 현재 동전을 뺀 금액을 만드는 방법의 수를 더함
                dp[amount] += dp[amount - coin];
            }
        }
        
        return dp[sum];
    }
}