<template>
  <div class="about-us-container">
  <h1 class="page-title">About Us</h1>
  <h1 class="">👋 Ba:bble</h1>
    <p><em>관심사에 대해 쉽게, 편하게, 다같이! 떠드는 익명의 플랫폼</em></p>
    <p>여럿의 친구들이 와글와글 떠는 장면을 상상해보세요! </p>
    <p>이러한 모습을 나타내는 영단어 babble에서 유추할 수 있듯이, <strong><code>Ba:bble</code></strong>은 <mark class="highlight-yellow_background">좋아하는 취미와 관심사에 대해 이야기하는 화상 플랫폼</mark> 입니다. </p>
    <p>좋아하는 영화에 대해 이야기할 친구를 원하거나, 뜨개질 손 동작을 서로 화면을 통해 보며 이야기 하고 싶어하는 등 개발 팀원들의 니즈에서 이 프로젝트는 시작되었습니다.</p>
    <p>또한, 관심사를 통해 개성을 표현하고 싶어 하면서도 부담스러운 자리는 피하고 싶은 MZ 세대의 특성 또한 기획 배경이 되었습니다.  </p> <br>
  <h1>🙋 Who&#x27;s Team Ba:bble?</h1>
    <img :class="resize" :src="require('@/assets/images/about_us/team_bubble_members.png')">
    <p><strong><code>Team Ba:bble</code></strong>은 익명의 관심사 공유 플랫폼, <code>Ba:bble</code>을 개발하기 위해 뭉친 5명의 젊은 개발자 모임입니다. <br>
      삼성 청년 SW 아카데미 5기의 서울반에서 모인 이들은 다양한 기술을 습득하고 회의를 거듭하며 개발에 열을 다하곤 합니다!</p>
    <ul class="bulleted-list">
      <li>Backend-Developers
        <ul class="bulleted-list">
          <li>Kim Juhyun - j828h@naver.com</li>
        </ul>
        <ul class="bulleted-list">
          <li>Kim Juyeon - wndusx1@gmail.com </li>
        </ul>
        <ul  class="bulleted-list">
          <li>Hwang Sunghyun - ppko1233@gmail.com</li>
        </ul>
      </li>
    </ul>
    <ul class="bulleted-list">
      <li>Frontend-Developers
        <ul class="bulleted-list">
          <li>Lee Jimin - jm9521@hanmail.net</li>
        </ul>
        <ul class="bulleted-list">
          <li>Cho Eunji - bagjo28841@gmail.com</li>
        </ul>
      </li>
  </ul>

  <h1 >🧐 How To Use This Service? </h1>
  <ol type="1" class="numbered-list" start="1">
    <li>E-mail / SNS로 쉽게 가입하세요.</li>
      <ul>
         <img :class="resize" :src="require('@/assets/images/about_us/join.png')">
         <img :class="resize" :src="require('@/assets/images/about_us/join2.png')">
      </ul>
      <ul class="bulleted-list">
        <li>비회원도 방 시청이 가능합니다.</li>
      </ul>
  </ol>

  <ol type="1" class="numbered-list" start="2">
    <li>원하는 주제의 이야기 방을 생성하세요.
      <ul class="bulleted-list">
        <li>로그인한 사용자는 원하는 이야기 방을 생성할 수 있습니다.
          <br>방 제목, 설명을 기입하고 썸네일로 지정할 사진을 업로드 후 분류 카테고리를 지정합니다.</li>
        <img :class="resize" :src="require('@/assets/images/about_us/create.png')">
      </ul>
      <ul class="bulleted-list">
        <li>원하는 해시테그 키워드를 5개까지 설정할 수 있습니다.
          <br>해당 키워드 구독을 원하는 사용자에게 이야기 방 알림 메일이 전송됩니다!</li>
        <img :class="resize" :src="require('@/assets/images/about_us/alarm.png')">
      </ul>
    </li>
  </ol>
  <ol type="1" class="numbered-list" start="3">
    <li>생성된 이야기 방에 참여하세요.
      <ul class="bulleted-list">
        <li>모든 유저는 익명의 닉네임을 부여받아 이야기 방에 참여합니다.</li>
      </ul>
      <ul class="bulleted-list">
        <li>로그인한 사용자는
          <ul class="bulleted-list">
            <li>대기실에서 방송 입장 전 카메라 / 음성 송출 여부를 지정.후 방에 입장합니다.
              <br>방 입장 후에도 송출 여부를 변경할 수 있습니다.</li>
            <img :class="resize" :src="require('@/assets/images/about_us/enter.png')">
          </ul>
          <ul class="bulleted-list">
            <li>방 입장 후 이모티콘과 채팅을 통해 리액션을 보여주세요.</li>
            <img :class="resize" :src="require('@/assets/images/about_us/chat.png')"> &nbsp;
            <img :class="resize" :src="require('@/assets/images/about_us/emoji.png')">

          </ul>
        </li>
      </ul>
      <ul class="bulleted-list">
        <li>로그인 하지 않은 사용자는 방송 청취와 이모티콘 리액션, 채팅 보기만가능합니다.</li>
      </ul>
      <ol type="1" class="numbered-list" start="3">
        <li>마이 페이지에서 회원 정보를 수정하세요
          <ul class="bulleted-list">
            <li>최대 5개까지 알림 설정을 원하는 해시테그 키워드를 설정할 수 있습니다. 알람 설정이 on이라면 해당 키워드의 이야기 방이 개설되면 이메일 알람을 보내드립니다.</li>
            <img :class="resize" :src="require('@/assets/images/about_us/keyword.png')">
          </ul>
          <ul class="bulleted-list">
            <li>이야기 방 시청 이력과 방 생성 이력을 확인할 수 있습니다.</li>
             <img :class="resize" :src="require('@/assets/images/about_us/mypage.png')">
          </ul>
        </li>
        </ol>
    </li>
  </ol>
  </div>
</template>

<style>
  .about-us-container {
    margin-left: 50px;
    margin-right : 50px;
  }

  .resize {
    width: 20;
    /* height: auto; */
  }

</style>
