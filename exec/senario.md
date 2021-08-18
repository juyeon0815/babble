# Project Senario

#### 메인 화면
<img src = "https://i.imgur.com/QTCaU2k.png" width = "1000px">

Component|Action|Login|Note
---------|---------|--|--|
로고|클릭 시 홈화면 이동|--|--|
검색창|라이브 방송 검색|--|--|--|
카테고리|카테고리 별 라이브 방송 분류 화면으로 이동|--|--|
프로필 사진|클릭 시마이페이지로 이동|--|마이페이지에서 사진 수정 가능|
방 생성|클릭 시 방 생성 dialog on|O|--|
로그인|login dialog on|X|--|
로그아웃|로그아웃 상태로 바뀜|O|--|
라이브 카드|화상회의 방 입장 전 대기실 dialog on|--|시청자 순, 생성 순으로 정렬|

#### 회원 가입
<img src = "https://i.imgur.com/WnMWTNQ.png" width = "1000px">

Component|Action|Login|Note
---------|---------|--|--|
입력 폼|입력 시 validation 검사|X|유효성 검사 실패 시 warning 등장|
중복체크|axios를 통해 이메일 중복검사|X|--|
인증하기|해당 이메일로 랜덤 코드 발송|X|--|
이메일 인증번호|발송된 코드와 동일한지 validation 검사|X|유효성 검사 실패 시 warning 등장|
회원가입|모든 validation 통과 시 axios를 통한 회원 정보 저장|X|--|


#### 회원가입 인증 코드 확인
<img src = "https://i.imgur.com/MQXBild.png" width = "1000px">


#### 로그인
<img src = "https://i.imgur.com/lom3vBh.png" width = "1000px" style ="border-radius:10px">

Component|Action|Login|Note
---------|---------|--|--|
입력 폼|입력 별 validation 검사|X|유효성 검사 실패 시 warning 등장|
로그인|axios를 통하여 auth 검사 후 로그인 처리|X||
비밀번호 찾기|비밀번호 찾기 폼으로 이동|X|이메일 입력 후 해당 이메일로 임시 패스워드 발송|
카카오 소셜 로그인|카카오 로그인 페이지로 리다이렉팅|X|Oauth
구글 소셜 로그인|구글 로그인 페이지로 리다이렉팅|X|Oauth

#### 방 입장 전 대기실
<img src = "https://i.imgur.com/NvltyvD.png" width = "1000px">

Component|Action|Login|Note
---------|---------|--|--|
마이크 버튼|마이크 음소거|O|--|
비디오 버튼|비디오 종료|O|--|
카메라 화면|웹캠 영상 출력|O|640*480|
방 입장하기|화상 회의실 화면으로 리다이렉팅|--|현재 마이크와 비디오 상태를 가지고 이동함.|


#### 화상회의 room
<img src = "https://i.imgur.com/EfOOe8i.png" width = "1000px">

Component|Action|Login|Note
---------|---------|--|--|
영상화면|크게 보기 상태에서 grid 모드로 변경|--|사용자가 음성 사용 시 화면 테두리에 border 표시|
영상화면 하단의 이름|강퇴 / 크게보기|--|--|
ㄴ|강퇴 클릭 시 해당 사용자 연결 종료|--|방장만 가능|
ㄴ|크게보기 클릭 시 화면 레이아웃 구성 변경|--|큰 화면 클릭 시 원래 화면으로 전환|
참여자 정보|--|O|현재 참여자의 이름과 카메라와 마이크 상태가 표시됨|
마이크 버튼|마이크 음소거|O|--|
비디오 버튼|비디오 종료|O|비디오 Off시 대체 이미지로 교체|
감정표현 버튼|감정표현 클릭 시 왼쪽 하단에 이름과 함께 화면에 표시|O|웹소켓을 이용한 통신|
채팅|채팅 메뉴 클릭 시 채팅창과 입력창 생성|O|웹소켓을 이용한 통신|


<img src = "https://i.imgur.com/JpOQ5S7.png" width = "1000px">

#### 카테고리
<img src = "https://i.imgur.com/NILHhqK.png" width = "1000px">

Component|Action|Login|Note
---------|---------|--|--|
카테고리 탭|해당 카테고리 영상만 표시|--|--|
정렬 버튼|클릭 시 해당 정렬 기준으로 변경|--|--|

#### 마이페이지
<img src = "https://i.imgur.com/zqRS3OB.png" width = "1000px">

Component|Action|Login|Note
---------|---------|--|--|
키워드|키워드 입력 후 + 버튼 클릭 시 키워드 등록|O|최대 5개까지 등록 가능|
알림 설정|On/Off toggle|--|알림 설정 시 등록한 키워드로 생성되는 방에 대한 이메일이 도착함.|


<img src = "https://i.imgur.com/4OLokCB.png" width = "1000px">

Component|Action|Login|Note
---------|---------|--|--|
방 생성 이력|--|O|--|
그래프|태그 클릭시 원하는 정보만 표시|--|ChartJS|

<img src = "https://i.imgur.com/k3yUWBa.png" width = "1000px">

Component|Action|Login|Note
---------|---------|--|--|
프로필 사진|+버튼 or 찾기 버튼 클릭 시 파일 첨부|O|프로필 업로드 버튼 후 변경|
비밀번호 변경|기존 비밀번호 체크 및 새로운 비밀번호로 변경|O|validation check|
회원 탈퇴|클릭 시 회원 탈퇴|O|--|

#### 에러 페이지 
- 존재하지 않는 페이지 이동 시 리다이렉팅
<img src = "https://i.imgur.com/tTbs15Z.gif" width = "1000px">