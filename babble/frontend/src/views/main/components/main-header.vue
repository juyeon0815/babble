<template>
  <nav class="navbar" v-if="state.activeMenuIndex != -1">
    <div class="navbar-logo">
      <h2 @click="clickLogo">Ba<mark class="purple">:b</mark>ble</h2>
    </div>
    <div class="navbar-search">
      <el-input
        prefix-icon="el-icon-search"
        class="search-bar"
        @keyup.enter="enterSearch"
        v-model="state.searchWord"
      ></el-input>
    </div>
    <div class="navbar-menu">
      <ul v-show="!state.isLoggedin">
        <li><button @click="clickCategory">카테고리</button></li>
        <li><button @click="clickJoin">회원가입</button></li>
        <li><button @click="clickLogin">로그인</button></li>
      </ul>
      <ul v-show="state.isLoggedin">
        <li>
          <button @click="clickCategory" class="textBtn">카테고리</button>
        </li>
        <li class="textline">
          <button @click="clickMyPage">마이페이지</button>
        </li>
        <li class="circle-img">
          <button @click="clickMyPage" class="circle">
            <div v-if="state.profile == 'default'">
              <img class="profile" :src="require('@/assets/images/default_profile.png')" />
            </div>
            <div v-else>
              <img class="profile" :src="state.profile" />
            </div>
          </button>
        </li>
        <li>
          <button @click="clickRoomCreate" class="textBtn">방 생성</button>
        </li>
        <li><button @click="clickLogout" class="textBtn">로그아웃</button></li>
      </ul>
    </div>
    <button class="navbar-toggleBtn" @click="clickToggle">
      <i class="fas fa-bars"></i>
    </button>
    <div class="seen-media">
      <div class="navbar-logo">
        <h2 @click="clickLogo">Ba<mark class="purple">:b</mark>ble</h2>
      </div>
      <div class="navbar-search">
        <el-input
          prefix-icon="el-icon-search"
          class="search-bar"
          @keyup.enter="enterSearch"
          v-model="state.searchWord"
        ></el-input>
      </div>
    </div>
  </nav>
  <div class="header-space" v-if="state.activeMenuIndex == 0">
    <div class="overlay">
      <div class="header-text">
        <h2>당신을 위한 공간, Ba:bble</h2>
        <p class="desc">
          ba:bble : 와글와글, 왁자지껄 (여럿이 한꺼번에 떠드는 소리)
        </p>
        <p class="desc2">
          ba:bble : 와글와글, 왁자지껄<br>
          여럿이 한꺼번에 떠드는 소리
        </p>
      </div>
      <img
        :src="require('@/assets/images/Visionary technology-rafiki.png')"
        class="illust"
      />
    </div>
  </div>
</template>

<style scoped>
.header-space {
  height: 300px;
  padding-bottom: 50px;
}

.header-space .overlay {
  width: 100%;
  height: 100%;
  padding-top : 50px;
  color: #fff;
  border-radius: 0 0 90% 50% /30%;
  text-shadow: 1px 1px 1px #333;
  background-image: linear-gradient(135deg, #9f05ff69 10%, #4a63cfc2 100%);
  position: relative;
}

.header-text {
  /* display: inline-block; */
  float: left;
  padding-left: 70px;
}

.header-space .overlay .header-text .desc {
  color: #fce3ff;
}

.header-space .overlay .header-text .desc2 {
  display: none;
}

.illust {
  width: 425px;
  height: 425px;
  float: right;
  padding-bottom: 5px;
  padding-right: 125px;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: transparent;
  background-image: linear-gradient(40deg, #9f05ff69 40%, #4a63cfc2 100%);
  padding: 10px 42px;
}

.navbar-logo h2 {
  font-size: 36px;
  color: white;
}

.navbar-logo h2:hover {
  font-size: 36px;
  cursor: pointer;
}

.navbar-logo mark {
  color: white;
  background: none;
}

.navbar-logo h2:hover .purple {
  color: #341a63;
  background: none;
}

.navbar-search .search-bar {
  width: 400px;
}

.navbar-menu ul {
  display: flex;
  list-style: none;
  padding-left: 0px;
}

.navbar-menu li {
  padding: 8px 12px;
}

.navbar-menu button:hover {
  color: #341a63;
  font-weight: bold;
  cursor: pointer;
}

.navbar-menu button {
  border: none;
  background: transparent;
  font-size: 16px;
  color: white;
}

.navbar-menu .textBtn {
  margin-top: 3px;
}

.navbar-toggleBtn {
  display: none;
  position: absolute;
  border: none;
  background: transparent;
  right: 32px;
  font-size: 24px;
  color: #341a63;
}

.seen-media {
  display: none;
}

.circle {
  padding: 0px;
  width: 40px;
  height: 40px;
  border-radius: 70%;
  overflow: hidden;
}

.circle .profile {
  width: 40px;
  height: 40px;
  object-fit: cover;
}

.textline {
  display: none;
}

@media screen and (max-width: 480px) {
  .navbar {
    flex-direction: column;
  }

  .navbar-logo,
  .navbar-search {
    display: none;
  }

  .navbar-menu {
    display: none;
    width: 100%;
  }

  .navbar-toggleBtn,
  .seen-media {
    display: block;
  }

  .navbar-toggleBtn:hover {
    cursor: pointer;
  }

  .seen-media {
    width: 100%;
    text-align: center;
  }

  .seen-media .navbar-search {
    display: block;
    width: 100%;
    padding-bottom: 10px;
  }

  .seen-media .navbar-search .search-bar {
    width: 90%;
  }

  .seen-media .navbar-logo {
    display: inline-block;
  }

  .seen-media .navbar-logo h2:hover .purple {
    color: #341a63;
    background: none;
  }

  .navbar-menu.active {
    display: flex;
    width: 100%;
  }

  .navbar-menu.active ul {
    flex-direction: column;
    width: 100%;
    /* align-items: center; */
  }

  .navbar-menu.active li {
    width: 90%;
    /* text-align: center; */
  }

  .navbar-menu.active button:hover {
    width: 100%;
    background-color: rgb(245, 247, 215, 0.5);
    border-radius: 5%;
    text-align: start;
    padding: 5px 1px;
  }

  .circle-img {
    display: none;
  }

  .textline {
    display: block;
  }

  .header-space .overlay {
    padding-top: 0px;
    border-radius: 0 0 70% 70% /30%;
    width: 100%;
    display: flex;
    justify-content: center;
  }

  .header-space .overlay .header-text {
    margin-top: 7%;
    padding-left: 0px;
    margin-left: auto;
    margin-right: auto;
    text-align: center;
    width: 80%;
    opacity: 0;
  }

  .header-space .overlay .header-text .desc {
    display: none;
  }

  .header-space .overlay .header-text .desc2 {
    display: block;
    margin-top: 12%;
    color: #341a63;
    text-shadow: none;
    font-weight: bold;
  }

  .header-space .overlay .illust {
    display: none;
  }


}
</style>

<script>
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { reactive, computed, watch, onMounted } from "vue";

export default {
  name: "main-header",

  setup(props, { emit }) {
    const router = useRouter();
    const store = useStore();

    const state = reactive({
      isLoggedin: computed(() => {
        return store.getters["auth/getToken"];
      }),
      profile: computed(() => {
        return store.getters["auth/getProfile"];
      }),
      searchWord: "",
      activeMenuIndex: computed(() => {
        return store.getters["menu/getActiveMenuIndex"];
      }),
      provider : computed(()=>{
        return store.getters["auth/getProvider"]
      }),
      token: computed(() => {
        return store.getters["auth/getToken"];
      }),
      email: computed(() => {
        return store.getters["auth/getEmail"]
      })
    });

    // store
    //   .dispatch("auth/requestUserInfo", localStorage.getItem("jwt"))
    //   .then(function(result) {
    //     // console.log(result.data.picture)
    //     store.commit("auth/setUserProfile", result.data.picture);
    //   });


    const clickLogo = () => {
      store.commit("menu/setActiveCategory", null);
      store.commit("menu/setMenuActive", 0);
      const MenuItems = store.getters["menu/getMenus"];
      let keys = Object.keys(MenuItems);
      router.push({
        name: keys[0]
      });
    };

    const clickJoin = function() {
      // console.log("clickJoin");
      emit("openJoinDialog");
    };

    const clickLogin = function() {
      // console.log('clickLogin')
      emit("openLoginDialog");
    };

    const clickRoomCreate = function() {
      emit("openRoomCreateDialog");
    };

    const clickCategory = function() {
      store.commit("menu/setMenuActive", 1);
      store.commit("menu/setActiveCategory", "all");
      router.push({
        path: "/category/all"
      });
    };

    const clickMyPage = function() {
      let nowIndex = store.getters["menu/getActiveMenuIndex"];
        if (nowIndex != 2) {
          store.commit("menu/setMenuActive", 2);
          router.push({
            path: "/mypage/keyword"
          });
        }
    };

    //state.provider 타입에 따라서 로그인한 타입에 따라서 dispatch 다르게
    const clickLogout = function() {
      console.log("clickLogout");
      console.log(state.provider)
      if (state.provider === "kakao") {
        store.dispatch("auth/requestKakaoLogout", state.token)
        .then(()=> store.commit("auth/setLogout"))
        .then(()=>router.push("/"));
      }
      else {
        store
        .dispatch("auth/requestLogout")
        .then(()=> store.commit("auth/setLogout"))
        .then(()=>router.push("/"));
      }

    };

    const enterSearch = function() {
      store.commit("menu/setMenuActive", 3)
      store.commit("menu/setSearchWord", state.searchWord);
      router.push({
        path: `/search/${state.searchWord}`
      });
      state.searchWord = "";
    };

    const clickToggle = function() {
      let menu = document.querySelector(".navbar-menu");
      menu.classList.toggle("active");
    };

    watch(
      () => state.isLoggedin,
      (after, prev) => {
        // console.log(prev + '--->' + after)
        loadProfile();
      }
    );

    const loadProfile = function() {
      if (state.provider == 'google' || state.provider == 'kakao') {
         store
        .dispatch("auth/requestSocialUserInfo", {email: state.email})
        .then(function (result) {
          console.log(result, '소셜 로그인 유저 정보 받아오기')
          store.commit("auth/setUserProfile", result.data.picture);
        })
      } else {
        store
          .dispatch("auth/requestUserInfo", localStorage.getItem("jwt"))
          .then(function (result) {
            store.commit("auth/setUserProfile", result.data.picture);
          })
          .catch(function (err) {
            if (err) {
              console.log(err, '헤더에서 프로필로드하며 에러캐치')
              // clickLogout()
            }
          })
      }

    };

     window.addEventListener('scroll', function(e) {
        if (document.documentElement.scrollTop > 100) {
          let text = document.querySelector(".header-text")
          text.style.opacity = "1"
        } else {
          let text = document.querySelector(".header-text")
          text.style.opacity = "0"
        }
      })

    return {
      state,
      clickLogo,
      clickJoin,
      clickLogin,
      clickRoomCreate,
      clickCategory,
      clickMyPage,
      clickLogout,
      enterSearch,
      clickToggle,
      loadProfile,
    };
  }
};
</script>

<style></style>
