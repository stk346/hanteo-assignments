package assignment2;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    
    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public void start() {
        while (true) {
            try {
                if (!processOneIteration()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("오류가 발생했습니다: " + e.getMessage());
            }
        }
        
        scanner.close();
        System.out.println("프로그램을 종료합니다.");
    }
    
    private boolean processOneIteration() {
        // 동전 입력 받기
        List<Integer> coins = inputCoins();
        if (coins == null) return true;  // 계속 진행
        
        // 합계 입력 받기
        Integer sum = inputSum();
        if (sum == null) return true;  // 계속 진행
        
        // 결과 계산 및 출력
        calculateAndPrintResult(coins, sum);
        
        // 계속할지 확인
        return askToContinue();
    }
    
    private List<Integer> inputCoins() {
        System.out.println("동전을 입력해주세요 (쉼표로 구분, 예: 1,2,3):");
        String input = scanner.nextLine().trim();
        
        try {
            return CoinInputProcessor.processInput(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    private Integer inputSum() {
        System.out.println("합계를 입력해주세요:");
        String input = scanner.nextLine().trim();
        
        try {
            int sum = Integer.parseInt(input);
            if (sum <= 0) {
                System.out.println("양수를 입력해주세요.");
                return null;
            }
            return sum;
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력해주세요.");
            return null;
        }
    }
    
    private void calculateAndPrintResult(List<Integer> coinList, int sum) {
        int[] coins = coinList.stream().mapToInt(Integer::intValue).toArray();
        int ways = CoinCombination.countWays(coins, sum);
        
        System.out.println("\n입력한 동전: " + coinList);
        System.out.println("입력한 합계: " + sum);
        System.out.println("가능한 조합 갯수: " + ways);
    }
    
    private boolean askToContinue() {
        System.out.println("\n계속 하려면 'y'를 입력해주세요.");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equals("y");
    }
} 