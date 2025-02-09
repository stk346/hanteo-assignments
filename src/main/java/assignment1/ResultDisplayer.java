package assignment1;

import java.util.*;

public class ResultDisplayer {
    
    public static void displayResult(int format, List<Integer> searchResults, TreeStructure tree) {
        switch (format) {
            case 1:
                displayTreeFormat(searchResults, tree);
                break;
            case 2:
                displayJsonFormat(searchResults, tree);
                break;
            default:
                System.out.println("잘못된 형식입니다.");
        }
    }
    
    private static void displayTreeFormat(List<Integer> searchResults, TreeStructure tree) {
        System.out.println("\n[카테고리 트리 검색 결과]");
        for (Integer categoryId : searchResults) {
            printCategoryTree(categoryId, 0, tree);
        }
    }
    
    private static void displayJsonFormat(List<Integer> searchResults, TreeStructure tree) {
        System.out.println("\n[카테고리 JSON 검색 결과]");
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> categories = new ArrayList<>();
        
        for (Integer categoryId : searchResults) {
            categories.add(buildJsonTree(categoryId, tree));
        }
        
        result.put("categories", categories);
        System.out.println(JsonFormatter.toPrettyJson(result));
    }
    
    private static void printCategoryTree(Integer categoryId, int depth, TreeStructure tree) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "- " + tree.getCategoryName(categoryId) + " (ID: " + categoryId + ")");
        
        if (tree.categoryToBoardMapContainsKey(categoryId)) {
            Integer boardPk = tree.getBoardPk(categoryId);
            Board board = tree.getBoard(boardPk - 1);
            System.out.println(indent + "  └ " + board.getName() + " (게시판 ID: " + boardPk + ")");
        }
        
        for (Integer childId : tree.getChildren(categoryId)) {
            printCategoryTree(childId, depth + 1, tree);
        }
    }
    
    private static Map<String, Object> buildJsonTree(Integer nodeId, TreeStructure tree) {
        if (!tree.categoryPkMapContainsKey(nodeId)) {
            return null;
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("pk", nodeId);
        result.put("parent_id", tree.getParentId(nodeId));
        result.put("name", tree.getCategoryName(nodeId));
        
        addBoardInfo(nodeId, tree, result);
        addChildrenInfo(nodeId, tree, result);
        
        return result;
    }
    
    private static void addBoardInfo(Integer nodeId, TreeStructure tree, Map<String, Object> result) {
        if (tree.categoryToBoardMapContainsKey(nodeId)) {
            Map<String, Object> boardInfo = new HashMap<>();
            Integer boardPk = tree.getBoardPk(nodeId);
            Board board = tree.getBoard(boardPk - 1);
            boardInfo.put("pk", boardPk);
            boardInfo.put("name", board.getName());
            result.put("board", boardInfo);
        }
    }
    
    private static void addChildrenInfo(Integer nodeId, TreeStructure tree, Map<String, Object> result) {
        List<Map<String, Object>> children = new ArrayList<>();
        for (Integer childId : tree.getChildren(nodeId)) {
            children.add(buildJsonTree(childId, tree));
        }
        
        if (!children.isEmpty()) {
            result.put("children", children);
        }
    }
} 