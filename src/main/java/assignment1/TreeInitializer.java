package assignment1;

public class TreeInitializer {
    
    public static void init(TreeStructure tree) {
        // 최상위 카테고리 생성
        Category menCategory = tree.addNewCategory(null, "남자");
        Category womenCategory = tree.addNewCategory(null, "여자");
        
        // 남자 카테고리 하위 구조
        Category exoCategory = tree.addNewCategory(menCategory.getPk(), "엑소");
        Category btsCategory = tree.addNewCategory(menCategory.getPk(), "방탄소년단");
        
        // 여자 카테고리 하위 구조
        Category blackpinkCategory = tree.addNewCategory(womenCategory.getPk(), "블랙핑크");
        
        // 엑소의 하위 카테고리와 게시판
        Category exoNoticeCategory = tree.addNewCategory(exoCategory.getPk(), "공지사항");
        Category chenCategory = tree.addNewCategory(exoCategory.getPk(), "첸");
        Category baekhyunCategory = tree.addNewCategory(exoCategory.getPk(), "백현");
        Category xiuminCategory = tree.addNewCategory(exoCategory.getPk(), "시우민");
        
        tree.createAndAssignBoard("엑소공지사항", exoNoticeCategory.getPk());
        tree.createAndAssignBoard("첸게시판", chenCategory.getPk());
        tree.createAndAssignBoard("백현게시판", baekhyunCategory.getPk());
        tree.createAndAssignBoard("시우민게시판", xiuminCategory.getPk());
        
        // 방탄의 하위 카테고리와 게시판
        Category btsNoticeCategory = tree.addNewCategory(btsCategory.getPk(), "공지사항");
        Category btsAnonymousCategory = tree.addNewCategory(btsCategory.getPk(), "익명게시판");
        Category vCategory = tree.addNewCategory(btsCategory.getPk(), "뷔");
        
        tree.createAndAssignBoard("방탄공지사항", btsNoticeCategory.getPk());
        tree.createAndAssignBoard("뷔게시판", vCategory.getPk());
        
        // 블랙핑크의 하위 카테고리와 게시판
        Category blackpinkNoticeCategory = tree.addNewCategory(blackpinkCategory.getPk(), "공지사항");
        Category blackpinkAnonymousCategory = tree.addNewCategory(blackpinkCategory.getPk(), "익명게시판");
        Category roseCategory = tree.addNewCategory(blackpinkCategory.getPk(), "로제");
        
        tree.createAndAssignBoard("블핑공지사항", blackpinkNoticeCategory.getPk());
        tree.createAndAssignBoard("로제게시판", roseCategory.getPk());

        // 익명게시판 공유 (방탄, 블랙핑크)
        Board anonymousBoard = tree.addNewBoard("익명게시판1");
        tree.assignExistingBoard(btsAnonymousCategory.getPk(), anonymousBoard.getPk());
        tree.assignExistingBoard(blackpinkAnonymousCategory.getPk(), anonymousBoard.getPk());
    }
} 