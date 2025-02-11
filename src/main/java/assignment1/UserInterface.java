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
                e.printStackTrace();
                scanner.nextLine(); // 버퍼 비우기
            }
        }
        
        scanner.close();
        System.out.println("프로그램을 종료합니다.");
    }

    private boolean processOneIteration() {
        System.out.println("\n=== 메뉴 ===");
        System.out.println("1. 카테고리 검색");
        System.out.println("2. 카테고리 추가");
        System.out.println("3. 카테고리 삭제");
        System.out.println("4. 게시판 추가");
        System.out.println("5. 게시판 삭제");
        System.out.println("0. 종료");
        
        String input = scanner.nextLine().trim();
        
        switch (input) {
            case "1": searchCategory(); break;
            case "2": addCategory(); break;
            case "3": deleteCategory(); break;
            case "4": addBoard(); break;
            case "5": deleteBoard(); break;
            case "0": return false;
            default: System.out.println("잘못된 입력입니다.");
        }
        
        return true;
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
        
        try {
            System.out.println("카테고리 이름을 입력하세요:");
            String categoryName = scanner.nextLine().trim();
            
            System.out.println("상위 카테고리 ID를 입력하세요 (최상위 카테고리는 엔터):");
            String parentIdInput = scanner.nextLine().trim();
            
            if (parentIdInput.isEmpty()) {
                tree.addNewCategory(null, categoryName);
            } else {
                tree.addNewCategory(Integer.parseInt(parentIdInput), categoryName);
            }
            
        } catch (NumberFormatException e) {
            System.out.println("올바른 카테고리 ID를 입력해주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteCategory() {
        System.out.println("\n=== 카테고리 삭제 ===");

        try {
            System.out.println("삭제할 카테고리 ID를 입력하세요:");
            int categoryId = Integer.parseInt(scanner.nextLine().trim());

            tree.deleteCategory(categoryId);
            System.out.println("카테고리가 삭제되었습니다.");

        } catch (NumberFormatException e) {
            System.out.println("올바른 카테고리 ID를 입력해주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addBoard() {
        System.out.println("\n=== 게시판 추가 ===");
        
        try {
            System.out.println("카테고리 ID를 입력하세요:");
            int categoryId = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.println("게시판 이름을 입력하세요:");
            String boardName = scanner.nextLine().trim();
            
            tree.createAndAssignBoard(boardName, categoryId);
            
        } catch (NumberFormatException e) {
            System.out.println("올바른 카테고리 ID를 입력해주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteBoard() {
        System.out.println("\n=== 게시판 삭제 ===");
        
        try {
            System.out.println("카테고리 ID를 입력하세요:");
            int categoryId = Integer.parseInt(scanner.nextLine().trim());
            
            tree.deleteBoard(categoryId);
            
        } catch (NumberFormatException e) {
            System.out.println("올바른 카테고리 ID를 입력해주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
} 