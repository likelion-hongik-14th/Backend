## 👋 Welcome LikeLion_14th Backend Repository
**멋쟁이사자처럼 홍익대학교 14기 세션**을 위한 백엔드 Repository입니다.

### 📚 과제
각 주차 세션 전에
1. 과제로 나오는 강의를 수강하고, 실습을 완료합니다.
2. 자신의 이름으로 브랜치를(`shinae`) 생성 후
3. 최상위 폴더 안에 자신의 이름으로 폴더 생성('shinae') 후 주차별로 폴더 생성 (week{#n})
4. 세션 실습은 practice 디렉터리에, 과제(강의 실습 코드)는 homework에 각각 작성을 완성하신 뒤에 `main` 브랜치로 PR합니다.
   
### 📤 프로젝트 열기
* 프로젝트를 하나 생성하고 IntelliJ에서 원격 저장소를 연결해줍니다
* ```git init``` // 저장소 생성
* ```git remote add origin https://github.com/likelion-hongik-14th/Backend```  //저장소 내 컴퓨터 <-> 원격 연결
* ```git pull origin main```
* IntelliJ의 terminal에서 자신의 branch로 checkout을 해주세요
    * ```git checkout origin [본인 이름]```  //브랜치 개념 모른다면 구글링. 자신만의 공간을 만든다고 생각하시면 됩니다.
    * ex) git checkout origin shinae
* 작업은 해당 **본인 branch에서만** 진행해주세요 **(~~❌main branch❌~~)**
    * ```git branch``` : 현재 branch 확인

위의 방식이 어렵다면 깃 Clone 하는 방법을 검색해보기

### 🏡 작업공간 생성
* Backend (프로젝트 루트 디렉토리)
    * 본인 이름의 디렉토리 
        * 해당 주차 디렉토리 (ex week2)
            * 스프링 소스코드 or 배운 것 등등


| 본인 이름의 디렉토리부터 만들고 시작하시면 됩니다!
```
│
├─ Backend
│     │
│     ├─ 본인 영문이름 (dir)
│     │     │ 
|     |     |
│     │     ├─  week1 (dir)
│     │     │    ├─ practice (dir)
│     │     │    └─ homework (dir)
│     │     │    |   ├─ gradle/wrapper
│     │     │    |   ├─ src
│     │     │    |   ├─ .gitignore
│     │     │    |   ├─ build.gradle
│     │     │    |   ├─ gradlew
│     │     │    |   ├─ gradlew.bat
│     │     │    |   └─ settings.gradle
│     │     │
│     │     ├─ week2 (dir)
│     │     │    └─ .. 이하 동일
│     │     │
│     │     └─ week3 (dir)
│     │          └─ .. 이하 동일 
│     │ 
```

### 💫 .gitignore에 필요없는 각종 설정파일 추가하기
* 깃허브에 코드 처음 커밋하고 푸시해주실때 본인 브랜치 가장 상위에는 ```본인 이름으로 된 디렉토리```만 해주셔야 합니다
* ```.gitigore``` 에 필요없는 각종 설정 파일들 추가해주셔서 ```최상위 디렉토리```,```.gitignore```, ```README.md``` 제외 다른 파일들은 푸시 안되게 해주셔야 합니다
* 보통 ```.idea``` 디렉토리나 ```.DS_Store(맥북의 경우)``` 이런 파일들이 추가로 올라가있는데 이런거 전부 최상위 디렉토리와 같은 레벨에 .gitignore 파일 만드시고   
  .idea/   
  .DS_Store   
  이렇게 추가해주시면 됩니다!!

### 📝 커밋 컨벤션
commit message는 `[Type] 작성 내용` 으로 통일합니다
- [Feat] : 새로운 기능 추가 시
- [Fix] : 버그 수정 시
- [Docs] : 문서화 작업 시
- [Style] : 코드 포맷 변경, 세미콜론 누락, 코드 수정이 없는 경우
- [Refactor] : 코드 리팩터링
- [Chore] : 빌드 업무 수정, 패키지 매니저 수정 등
- [Rename] : 파일명 또는 폴더명 수정한 경우
- [Remove] : 코드 삭제가 있을 때
- [Environment] : 개발 환경 세팅 시
- [!HOTFIX] : 급하게 치명적인 버그를 고쳐야 하는 경우

### ✅ PR 규칙
1. `main`에서 `{이름}`(예: `shinae`)로 브랜치 분기 후
2. `{이름}/week{#n}`(예: `shinae/week2/{practice or homework}`) 디렉터리에서 작업합니다.
3. 과제를 완성한 뒤에는 `main` 브랜치로 PR 합니다.
4. PR 제목은 `[{keyword}] {제목}`(예: `[Feat] Week2 API 실습 구현 및 과제 실습 완료`) 형식으로 합니다. 커밋 컨벤션과 마찬가지입니다.
5. Reviewers shinae1023, whc9999, Mymyseoyoung, alissa159 4명을 추가해주세요.
6. Assignees로는 본인을 추가해주세요.
7. Label로 해당 주차 과제(예: `Week2`)를 필수로 선택해주세요. 추가적인 Label도 괜찮습니다.
   ![image](https://github.com/user-attachments/assets/7d3139a5-af1f-4e3b-b02e-6878dd610619)

‼️  `main` 브랜치로의 PR 승인을 과제 확인으로 인정합니다.

‼️  디렉터리, 브랜치 이름은 영문을 이용해주세요.

‼️  PR 규칙을 잘 확인해주세요! 부정확하게 작업된 PR은 승인하지 않습니다.

