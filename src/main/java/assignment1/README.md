# 카테고리 기반 게시판 시스템

계층형 카테고리 구조를 가진 게시판 시스템을 구현했습니다.<br>
카테고리들은 트리 구조로 구성되어 있으며, 각 말단 카테고리에는 게시판이 연결될 수 있습니다.

## 핵심 자료구조
### 1. TreeStructure 클래스

시스템의 핵심 자료구조로, 아래와 같은 필드로 구성돼 있습니다.
```java
private final List<Edge> edges = new ArrayList<>();
private final Map<String, Category> categoryNameMap = new HashMap<>();
private final Map<Integer, Category> categoryPkMap = new HashMap<>();
private final List<Board> boards = new ArrayList<>();
private final Map<Integer, Integer> categoryToBoardMap = new HashMap<>();
```
- `edges`: 카테고리 간의 관계를 저장 (parent_id, child_id)
- `categoryNameMap`: 카테고리 이름으로 검색을 위한 맵
- `categoryPkMap`: 카테고리 ID로 검색을 위한 맵
- `boards`: 게시판 목록
- `categoryToBoardMap`: 카테고리와 게시판의 연결 관계

### 2. Edge 클래스
카테고리 간의 관계를 표현하기 위한 클래스
```java
public class Edge {
Integer pid; // parent_id
Integer cid; // child_id
}
```

## 주요 구현 사항
### 1. 카테고리 검색
- ID 검색과 이름 검색 가능
- 검색된 카테고리의 하위 카테고리를 포함하여 결과 반환
- 재귀를 통해 하위 카테고리 탐색

### 2. 카테고리 추가
- 최상위 카테고리 생성 가능
- 기존 카테고리의 하위 카테고리로 추가 가능
- 카테고리 이름 중복 허용

### 3. 카테고리 삭제
- ID로 카테고리 삭제 가능
- 카테고리에 게시판이 있으면 삭제 불가능

### 4. 게시판 추가
- 말단 카테고리에만 게시판 추가 가능
- 하나의 카테고리에는 하나의 게시판만 할당 가능
- 게시판 이름 중복 허용

### 5. 게시판 삭제
- 카테고리와 게시판의 연결 제거

### 6. 게시판 공유
- 동일한 게시판을 여러 카테고리에서 공유 가능
- 예: 익명게시판을 여러 카테고리에서 공유

### 7. JSON 변환
- 카테고리 구조를 JSON으로 변환하여 출력
