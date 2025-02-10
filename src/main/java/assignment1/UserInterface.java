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
                addCategory();
                return true;
            case 3:
                return false;
            default:
                System.out.println("잘못된 명령입니다.");
                return true;
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n=== 카테고리 관리 시스템 ===");
        System.out.println("1. 카테고리 검색");
        System.out.println("2. 카테고리 추가");
        System.out.println("3. 종료");
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

    private void addCategory() {
        System.out.println("\n=== 카테고리 추가 ===");
        
        // 카테고리 이름 입력
        System.out.println("추가할 카테고리 이름을 입력하세요:");
        String categoryName = scanner.nextLine().trim();
        if (categoryName.isEmpty()) {
            System.out.println("카테고리 이름은 비워둘 수 없습니다.");
            return;
        }

        // 상위 카테고리 ID 입력
        System.out.println("상위 카테고리 ID를 입력하세요 (최상위 카테고리는 엔터):");
        String parentIdInput = scanner.nextLine().trim();
        
        try {
            Category newCategory;
            if (parentIdInput.isEmpty()) {
                // 최상위 카테고리 추가
                newCategory = tree.addNewCategory(categoryName);
            } else {
                // 하위 카테고리 추가
                int parentId = Integer.parseInt(parentIdInput);
                
                // 부모 카테고리 존재 여부 확인
                if (!tree.categoryPkMapContainsKey(parentId)) {
                    System.out.println("존재하지 않는 상위 카테고리입니다.");
                    return;
                }
                
                newCategory = tree.addNewCategory(parentId, categoryName);
            }
            
            // 추가된 카테고리 정보 출력
            System.out.println("카테고리 이름: " + newCategory.getName());
            Integer parentId = tree.getParentId(newCategory.getPk());
            if (parentId != null) {
                System.out.println("상위 카테고리: " + tree.getCategoryName(parentId) + " (ID: " + parentId + ")");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("올바른 카테고리 ID를 입력해주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
} 