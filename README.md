## 실행 방법
### 프로그램 실행
1. 프로젝트 폴더 압축을 풀고 에디터로 프로젝트 폴더를 연다.
2. src/main/java 폴더에 들어가서 Main.java 파일을 실행시킨다.

### 로그인 페이지
1. 아이디와 비밀번호를 입력합니다.
2. 등록이 되어 있는 user.txt 파일에 회원이 등록되어 있다면 로그인을 합니다.
3. 로그인이 되면 메인 페이지로 이동합니다.
> 현재 아이디 : User, 비밀번호 : User를 초기 등록해 두었습니다.

### 회원가입 페이지
1. 회원으로 등록되어 있지 않다면 회원가입을 합니다.
2. 이름, 아이디, 비밀번호를 입력합니다.
3. 비밀번호와 비밀번호 확인이 일치하지 않거나 비밀번호가 6자리가 넘지 않으면 안내 메세지가 호출됩니다.
4. 회원 정보는 users.txt에 저장되어 프로젝트를 다시 실행해도 정보가 삭제되지 않습니다.

### 메인 페이지
1. 메인 페이지에서는 위에서 부터 차례로 디데이 페이지, 캘린더 페이지, 과목 페이지로 이동할 수 있는 버튼이 있습니다. 이 버튼을 눌러 원하는 페이지로 이동합니다.

### 디데이 페이지
1. 디데이 페이지에서는 디데이 추가 버튼을 눌러 디데이를 등록할 수 있습니다.
2. 디데이 추가 버튼을 누른 후 디데이 이름과 날짜를 입력합니다.
3. 생성된 디데이는 더블 클릭하여 삭제할 수 있습니다.
4. 디데이 정보는 ddays.txt에 저장되어 프로젝트를 다시 실행해도 정보가 삭제되지 않습니다.

### 캘린더 페이지
1. 캘린더 페이지에서는 각 날짜에 원하는 이벤트를 추가하여 볼 수 있습니다.
2. 원하는 날짜를 클릭하고 일정 이름을 입력합니다.
3. 입력된 일정들은 아래에 오름차순으로 정렬되어 보여집니다.
4. 생성된 일정은 더블 클릭하여 삭제할 수 있습니다.
5. 위에 있는 <<, >> 버튼을 클릭하여 이전 월과 다음 월로 이동할 수 있습니다.
6. 일정 정보는 events.txt에 저장되어 프로젝트를 다시 실행해도 정보가 삭제되지 않습니다.

### 과목 페이지
1. 추가할 과목의 이름을 입력하고 오른쪽 위 Add Subject 버튼을 눌러 원하는 과목을 추가합니다.
2. 각 과목에 대해 Add to-do 버튼을 누르고 추가할 to-do를 입력하여 원하는 to-do를 추가합니다.
3. 생성된 to-do의 오른쪽의 Delete 버튼을 눌러 원하는 to-do를 삭제할 수 있습니다.
4. 과목과 to-do 정보는 checkboxes.txt에 저장되어 프로젝트를 다시 실행해도 정보가 삭제되지 않습니다.