package assignment2;

import java.util.ArrayList;
import java.util.List;

public class CoinInputProcessor {
    
    public static List<Integer> processInput(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("숫자만 입력해주세요.");
        }
        
        String[] coinStrings = input.split(",");
        List<Integer> coinList = new ArrayList<>();
        
        for (String coin : coinStrings) {
            int value = parsePositiveInteger(coin.trim());
            coinList.add(value);
        }
        
        if (coinList.isEmpty()) {
            throw new IllegalArgumentException("유효한 동전이 없습니다.");
        }
        
        return coinList;
    }
    
    private static int parsePositiveInteger(String str) {
        try {
            int value = Integer.parseInt(str);
            if (value <= 0) {
                throw new IllegalArgumentException("양수만 입력 가능합니다.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력해주세요.");
        }
    }
} 