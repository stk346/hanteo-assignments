package assignment1;

public class TreeInitializer {
    
    public static void init(TreeStructure tree) {
        initializeCategories(tree);
        initializeBoards(tree);
        assignBoardsToCategories(tree);
    }
    
    private static void initializeCategories(TreeStructure tree) {
        // 최상위 카테고리 생성
        Category 남자 = tree.addNewCategory("남자");
        Category 여자 = tree.addNewCategory("여자");
        
        // 남자 카테고리 하위 구조
        Category 엑소 = tree.addNewCategory(남자.getPk(), "엑소");
        Category 방탄 = tree.addNewCategory(남자.getPk(), "방탄소년단");
        
        // 여자 카테고리 하위 구조
        Category 블랙핑크 = tree.addNewCategory(여자.getPk(), "블랙핑크");
        
        // 엑소의 하위 카테고리
        Category 엑소공지 = tree.addNewCategory(엑소.getPk(), "공지사항");
        Category 첸 = tree.addNewCategory(엑소.getPk(), "첸");
        Category 백현 = tree.addNewCategory(엑소.getPk(), "백현");
        Category 시우민 = tree.addNewCategory(엑소.getPk(), "시우민");
        
        // 방탄의 하위 카테고리
        Category 방탄공지 = tree.addNewCategory(방탄.getPk(), "공지사항");
        Category 방탄익명 = tree.addNewCategory(방탄.getPk(), "익명게시판");
        Category 뷔 = tree.addNewCategory(방탄.getPk(), "뷔");
        
        // 블랙핑크의 하위 카테고리
        Category 블핑공지 = tree.addNewCategory(블랙핑크.getPk(), "공지사항");
        Category 블핑익명 = tree.addNewCategory(블랙핑크.getPk(), "익명게시판");
        Category 로제 = tree.addNewCategory(블랙핑크.getPk(), "로제");
    }
    
    private static void initializeBoards(TreeStructure tree) {
        tree.addNewBoard("엑소공지사항");     // pk: 1
        tree.addNewBoard("첸게시판");         // pk: 2
        tree.addNewBoard("백현게시판");        // pk: 3
        tree.addNewBoard("시우민게시판");      // pk: 4
        tree.addNewBoard("방탄공지사항");      // pk: 5
        tree.addNewBoard("익명게시판");        // pk: 6 (공유됨)
        tree.addNewBoard("뷔게시판");         // pk: 7
        tree.addNewBoard("블핑공지사항");      // pk: 8
        tree.addNewBoard("로제게시판");        // pk: 9
    }
    
    private static void assignBoardsToCategories(TreeStructure tree) {
        // 각 카테고리에 게시판 할당
        tree.assignBoardToCategory(3, 1);  // 엑소 공지사항 -> 보드1
        tree.assignBoardToCategory(4, 2);  // 첸 -> 보드2
        tree.assignBoardToCategory(5, 3);  // 백현 -> 보드3
        tree.assignBoardToCategory(6, 4);  // 시우민 -> 보드4
        tree.assignBoardToCategory(8, 5);  // 방탄 공지사항 -> 보드5
        tree.assignBoardToCategory(9, 6);  // 방탄 익명 -> 보드6
        tree.assignBoardToCategory(13, 6); // 블핑 익명 -> 보드6 (공유)
        tree.assignBoardToCategory(10, 7); // 뷔 -> 보드7
        tree.assignBoardToCategory(12, 8); // 블핑 공지사항 -> 보드8
        tree.assignBoardToCategory(14, 9); // 로제 -> 보드9
    }
} 