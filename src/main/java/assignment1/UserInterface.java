package assignment1;

import java.util.*;

public class UserInterface {
    private final Scanner scanner;
    private final TreeStructure tree;
    
    public UserInterface(Scanner scanner, TreeStructure tree) {
        this.scanner = scanner;
        this.tree = tree;
    }
    
    public void start() {
        while (true) {
            try {
                if (!processOneIteration()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("오류가 발생했습니다: " + e.getMessage());
                scanner.nextLine(); // 버퍼 비우기
            }
        }
        
        scanner.close();
        System.out.println("프로그램을 종료합니다.");
    }
    
    private boolean processOneIteration() {
        displayMainMenu();
        int command = getCommand();
        
        switch (command) {
            case 1:
                searchCategory();
                return true;
            case 2:
                return false;
            default:
                System.out.println("잘못된 명령입니다.");
                return true;
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n=== 카테고리 관리 시스템 ===");
        System.out.println("1. 카테고리 검색");
        System.out.println("2. 종료");
        System.out.print("명령을 입력하세요: ");
    }
    
    private int getCommand() {
        int command = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기
        return command;
    }
    
    private void searchCategory() {
        List<Integer> searchResults = getSearchResults();
        if (searchResults.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }
        
        displayResults(searchResults);
    }
    
    private List<Integer> getSearchResults() {
        System.out.println("\n=== 카테고리 검색 ===");
        System.out.println("검색어를 입력하세요 (카테고리 이름 또는 ID, 전체검색은 엔터):");
        String searchParam = scanner.nextLine().trim();
        
        List<Integer> searchResults = new ArrayList<>();
        if (searchParam.isEmpty()) {
            tree.searchAll(searchResults);
        } else {
            tree.search(searchParam, searchResults);
        }
        return searchResults;
    }
    
    private void displayResults(List<Integer> searchResults) {
        System.out.println("출력 형식을 선택하세요:");
        System.out.println("1. 트리 형태");
        System.out.println("2. JSON 형태");
        
        int format = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기
        
        ResultDisplayer.displayResult(format, searchResults, tree);
    }
} 