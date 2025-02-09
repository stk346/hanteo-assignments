package assignment1;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        TreeStructure tree = new TreeStructure();
        init(tree);

        // 대화형 인터페이스 시작
        start(tree);
    }

    private static void start(TreeStructure tree) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n=== 카테고리 관리 시스템 ===");
                System.out.println("1. 카테고리 검색");
                System.out.println("2. 종료");
                System.out.print("명령을 입력하세요: ");

                int command = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기

                switch (command) {
                    case 1:
                        search(scanner, tree);
                        break;
                    case 2:
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    default:
                        System.out.println("잘못된 명령입니다.");
                }
            } catch (Exception e) {
                System.out.println("오류가 발생했습니다: " + e.getMessage());
                scanner.nextLine(); // 버퍼 비우기
            }
        }
    }

    private static void search(Scanner scanner, TreeStructure tree) {
        System.out.println("\n=== 카테고리 검색 ===");
        System.out.println("검색어를 입력하세요 (카테고리 이름 또는 ID, 전체검색은 엔터):");
        String searchParam = scanner.nextLine().trim();

        List<Integer> searchResults = new ArrayList<>();
        if (searchParam.isEmpty()) {
            tree.searchAll(searchResults);
        } else {
            tree.search(searchParam, searchResults);
        }

        if (searchResults.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        System.out.println("출력 형식을 선택하세요:");
        System.out.println("1. 트리 형태");
        System.out.println("2. JSON 형태");
        int format = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        switch (format) {
            case 1:
                System.out.println("\n[카테고리 트리 검색 결과]");
                for (Integer categoryId : searchResults) {
                    printCategoryTree(categoryId, 0, tree);
                }
                break;
            case 2:
                System.out.println("\n[카테고리 JSON 검색 결과]");
                Map<String, Object> result = new HashMap<>();
                List<Map<String, Object>> categories = new ArrayList<>();
                for (Integer categoryId : searchResults) {
                    categories.add(buildJsonTree(categoryId, tree));
                }
                result.put("categories", categories);
                System.out.println(toPrettyJson(result));
                break;
            default:
                System.out.println("잘못된 형식입니다.");
        }
    }

    public static Map<String, Object> buildJsonTree(Integer nodeId, TreeStructure tree) {
        if (!tree.categoryPkMapContainsKey(nodeId)) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();
        String categoryName = tree.getCategoryName(nodeId);
        result.put("pk", nodeId);
        result.put("parent_id", tree.getParentId(nodeId));
        result.put("name", categoryName);

        if (tree.categoryToBoardMapContainsKey(nodeId)) {
            Map<String, Object> boardInfo = new HashMap<>();
            Integer boardPk = tree.getBoardPk(nodeId);
            Board board = tree.getBoard(boardPk - 1); // pk는 1부터 시작하므로 -1
            boardInfo.put("pk", boardPk);
            boardInfo.put("name", board.getName());
            result.put("board", boardInfo);
        }

        List<Map<String, Object>> children = new ArrayList<>();
        List<Integer> childNodes = tree.getChildren(nodeId);
        for (Integer childId : childNodes) {
            children.add(buildJsonTree(childId, tree));
        }

        if (!children.isEmpty()) {
            result.put("children", children);
        }

        return result;
    }

    private static void printCategoryTree(Integer nodeId, int depth, TreeStructure tree) {
        Category category = tree.getCategoryPkMap(nodeId);
        String indent = "  ".repeat(depth);

        // 카테고리 정보 출력
        StringBuilder line = new StringBuilder(indent + "ㄴ " + category.getName());
        line.append(" (cid ").append(nodeId).append(")");

        // 게시판 정보가 있으면 추가

        if (tree.categoryToBoardMapContainsKey(nodeId)) {
            Integer boardPk = tree.getBoardPk(nodeId);
            Board board = tree.getBoard(boardPk - 1);
            line.append(" [게시판: ").append(board.getName())
                    .append(" (bid ").append(boardPk).append(")]");
        }

        System.out.println(line);

        // 자식 노드 출력
        List<Integer> childNodes = tree.getChildren(nodeId);
        for (Integer childId : childNodes) {
            printCategoryTree(childId, depth + 1, tree);
        }
    }

    public static String toPrettyJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{\n");
        toPrettyJsonRecursive(map, json, 1);
        json.append("}\n");
        return json.toString();
    }

    private static void toPrettyJsonRecursive(Map<String, Object> map, StringBuilder json, int indent) {
        String indentStr = "  ".repeat(indent);
        int i = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.append(indentStr).append("\"").append(entry.getKey()).append("\": ");

            Object value = entry.getValue();
            if (value == null) {
                json.append("null");
            } else if (value instanceof String) {
                json.append("\"").append(value).append("\"");
            } else if (value instanceof Number) {
                json.append(value);
            } else if (value instanceof List) {
                json.append("[\n");
                List<?> list = (List<?>) value;
                for (int j = 0; j < list.size(); j++) {
                    Object item = list.get(j);
                    if (item instanceof Map) {
                        json.append(indentStr).append("  {\n");
                        toPrettyJsonRecursive((Map<String, Object>) item, json, indent + 2);
                        json.append(indentStr).append("  }");
                    }
                    if (j < list.size() - 1) {
                        json.append(",");
                    }
                    json.append("\n");
                }
                json.append(indentStr).append("]");
            } else if (value instanceof Map) {
                json.append("{\n");
                toPrettyJsonRecursive((Map<String, Object>) value, json, indent + 1);
                json.append(indentStr).append("}");
            }

            if (i < map.size() - 1) {
                json.append(",");
            }
            json.append("\n");
            i++;
        }
    }

    private static void init(TreeStructure tree) {
        // 남자 카테고리 생성
        Category 남자 = tree.addNewCategory("남자");
        Category 엑소 = tree.addNewCategory(남자.getPk(), "엑소");
        Category 방탄 = tree.addNewCategory(남자.getPk(), "방탄소년단");

        // 여자 카테고리 생성
        Category 여자 = tree.addNewCategory("여자");
        Category 블랙핑크 = tree.addNewCategory(여자.getPk(), "블랙핑크");

        // 엑소의 하위 카테고리들
        Category 엑소공지 = tree.addNewCategory(엑소.getPk(), "공지사항");
        Category 첸 = tree.addNewCategory(엑소.getPk(), "첸");
        Category 백현 = tree.addNewCategory(엑소.getPk(), "백현");
        Category 시우민 = tree.addNewCategory(엑소.getPk(), "시우민");

        // 방탄의 하위 카테고리들
        Category 방탄공지 = tree.addNewCategory(방탄.getPk(), "공지사항");
        Category 방탄익명 = tree.addNewCategory(방탄.getPk(), "익명게시판");
        Category 뷔 = tree.addNewCategory(방탄.getPk(), "뷔");

        // 블랙핑크의 하위 카테고리들
        Category 블핑공지 = tree.addNewCategory(블랙핑크.getPk(), "공지사항");
        Category 블핑익명 = tree.addNewCategory(블랙핑크.getPk(), "익명게시판");
        Category 로제 = tree.addNewCategory(블랙핑크.getPk(), "로제");

        // 보드 생성
        Board 엑소공지보드 = tree.addNewBoard("엑소공지사항");    // pk: 1
        Board 첸보드 = tree.addNewBoard("첸게시판");            // pk: 2
        Board 백현보드 = tree.addNewBoard("백현게시판");         // pk: 3
        Board 시우민보드 = tree.addNewBoard("시우민게시판");      // pk: 4
        Board 방탄공지보드 = tree.addNewBoard("방탄공지사항");    // pk: 5
        Board 익명보드 = tree.addNewBoard("익명게시판");         // pk: 6 (공유됨)
        Board 뷔보드 = tree.addNewBoard("뷔게시판");            // pk: 7
        Board 블핑공지보드 = tree.addNewBoard("블핑공지사항");    // pk: 8
        Board 로제보드 = tree.addNewBoard("로제게시판");         // pk: 9

        // 보드 할당
        tree.assignBoardToCategory(엑소공지.getPk(), 엑소공지보드.getPk());   // 엑소 공지사항 -> 보드1
        tree.assignBoardToCategory(첸.getPk(), 첸보드.getPk());             // 첸 -> 보드2
        tree.assignBoardToCategory(백현.getPk(), 백현보드.getPk());          // 백현 -> 보드3
        tree.assignBoardToCategory(시우민.getPk(), 시우민보드.getPk());       // 시우민 -> 보드4
        tree.assignBoardToCategory(방탄공지.getPk(), 방탄공지보드.getPk());   // 방탄 공지사항 -> 보드5
        tree.assignBoardToCategory(방탄익명.getPk(), 익명보드.getPk());      // 방탄 익명 -> 보드6
        tree.assignBoardToCategory(블핑익명.getPk(), 익명보드.getPk());      // 블핑 익명 -> 보드6 (공유)
        tree.assignBoardToCategory(뷔.getPk(), 뷔보드.getPk());             // 뷔 -> 보드7
        tree.assignBoardToCategory(블핑공지.getPk(), 블핑공지보드.getPk());   // 블핑 공지사항 -> 보드8
        tree.assignBoardToCategory(로제.getPk(), 로제보드.getPk());         // 로제 -> 보드9
    }
}
