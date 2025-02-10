package assignment1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeStructure {
    private final List<Edge> edges = new ArrayList<>();
    private final Map<String, Category> categoryNameMap = new HashMap<>();
    private final Map<Integer, Category> categoryPkMap = new HashMap<>();
    private final List<Board> boards = new ArrayList<>();
    private final Map<Integer, Integer> categoryToBoardMap = new HashMap<>();

    public Category addNewCategory(Integer parentId, String name) {
        Category category = new Category(name);
        categoryNameMap.put(String.format("%s_%d", name, category.getPk()), category);
        categoryPkMap.put(category.getPk(), category);

        if (parentId != null) {
            validateCategoryExists(parentId);
        }
        edges.add(new Edge(parentId, category.getPk()));
        return category;
    }

    public Board addNewBoard(String name) {
        Board board = new Board(name);
        boards.add(board);
        return board;
    }

    public void assignBoardToCategory(int categoryPk, int boardPk) {
        if (!categoryPkMap.containsKey(categoryPk)) {
            throw new IllegalArgumentException(String.format("카테고리가 존재하지 않습니다: %d", categoryPk));
        }
        if (boardPk > boards.size() || boardPk < 1) {
            throw new IllegalArgumentException(String.format("게시판이 존재하지 않습니다: %d", boardPk));
        }
        categoryToBoardMap.put(categoryPk, boardPk);
    }

    public Integer getParentId(Integer nodeId) {
        for (Edge edge : edges) {
            if (edge.cid.equals(nodeId)) {
                return edge.pid;
            }
        }
        return null;
    }

    public List<Integer> getChildren(Integer nodeId) {
        List<Integer> children = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.pid != null && edge.pid.equals(nodeId)) {
                children.add(edge.cid);
            }
        }
        return children;
    }

    public void searchAll(List<Integer> searchResults) {
        for (Edge edge : edges) {
            if (edge.pid == null) {
                searchResults.add(edge.cid);
            }
        }
    }

    public void search(String searchParam, List<Integer> searchResults) {
        try {
            int categoryId = Integer.parseInt(searchParam);
            if (categoryPkMap.containsKey(categoryId)) {
                searchResults.add(categoryId);
            }
        } catch (NumberFormatException e) {
            // 이름으로 검색
            for (Category category : categoryPkMap.values()) {
                if (category.getName().contains(searchParam)) {
                    searchResults.add(category.getPk());
                }
            }
        }
    }

    public boolean categoryToBoardMapContainsKey(Integer nodeId) {
        return categoryToBoardMap.containsKey(nodeId);
    }

    public Board getBoard(Integer boardPk) {
        return boards.get(boardPk);
    }

    public Integer getBoardPk(Integer nodeId) {
        return categoryToBoardMap.get(nodeId);
    }

    public boolean categoryPkMapContainsKey(Integer nodeId) {
        return categoryPkMap.containsKey(nodeId);
    }

    public String getCategoryName(Integer nodeId) {
        return categoryPkMap.get(nodeId).getName();
    }

    public boolean isLeafCategory(Integer categoryId) {
        return getChildren(categoryId).isEmpty();
    }

    public Board createAndAssignBoard(String boardName, Integer categoryId) {
        validateCategoryExists(categoryId);
        validateIsLeafCategory(categoryId);
        validateBoardNotExists(categoryId);
        validateBoardName(boardName);
        
        Board board = addNewBoard(boardName);
        assignBoardToCategory(categoryId, board.getPk());
        return board;
    }

    public void assignExistingBoard(Integer categoryId, Integer boardPk) {
        if (!isLeafCategory(categoryId)) {
            throw new IllegalArgumentException("말단 카테고리에만 추가할 수 있습니다.");
        }
        
        if (boardPk > boards.size() || boardPk < 1) {
            throw new IllegalArgumentException("게시판이 존재하지 않습니다.");
        }
        
        categoryToBoardMap.put(categoryId, boardPk);
    }

    public void deleteBoard(Integer categoryId) {
        validateCategoryExists(categoryId);
        validateBoardExists(categoryId);
        
        categoryToBoardMap.remove(categoryId);
    }

    public void deleteCategory(Integer categoryId) {
        validateDeletableCategory(categoryId);

        edges.removeIf(edge -> edge.cid.equals(categoryId));
        
        Category category = categoryPkMap.get(categoryId);
        categoryNameMap.remove(String.format("%s_%d", category.getName(), categoryId));
        categoryPkMap.remove(categoryId);
    }

    private void validateDeletableCategory(Integer categoryId) {
        validateCategoryExists(categoryId);
        
        if (categoryToBoardMapContainsKey(categoryId)) {
            throw new IllegalArgumentException("게시판이 존재하는 카테고리는 삭제할 수 없습니다.");
        }
        
        if (!isLeafCategory(categoryId)) {
            throw new IllegalArgumentException("하위 카테고리가 있는 카테고리는 삭제할 수 없습니다.");
        }
    }

    private void validateCategoryExists(Integer categoryId) {
        if (!categoryPkMapContainsKey(categoryId)) {
            throw new IllegalArgumentException(String.format("카테고리가 존재하지 않습니다: %d", categoryId));
        }
    }

    private void validateIsLeafCategory(Integer categoryId) {
        if (!isLeafCategory(categoryId)) {
            throw new IllegalArgumentException("말단 카테고리에만 추가할 수 있습니다.");
        }
    }

    private void validateBoardNotExists(Integer categoryId) {
        if (categoryToBoardMapContainsKey(categoryId)) {
            throw new IllegalArgumentException("게시판이 존재합니다.");
        }
    }

    private void validateBoardExists(Integer categoryId) {
        if (!categoryToBoardMapContainsKey(categoryId)) {
            throw new IllegalArgumentException("게시판이 존재하지 않습니다.");
        }
    }

    private void validateBoardName(String boardName) {
        if (boardName == null || boardName.trim().isEmpty()) {
            throw new IllegalArgumentException("게시판 이름을 입력해주세요.");
        }
    }
}