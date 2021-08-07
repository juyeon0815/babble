<template>
  <el-row class="navbar" v-if="state.activeMenuIndex != -1">
    <h2 @click="clickLogo">Babble</h2>
    <el-input
      prefix-icon="el-icon-search"
      class="search-bar"
      @keyup.enter="enterSearch"
      v-model="state.searchWord"
    ></el-input>
    <el-button type="info" plain @click="clickCategory">카테고리</el-button>
    <div v-show="!state.isLoggedin">
      <el-button type="primary" plain @click="clickJoin">회원가입</el-button>
      <el-button type="info" plain @click="clickLogin">로그인</el-button>
    </div>
    <div v-show="state.isLoggedin">
      <el-button class="circle btn" @click="clickMyPage">
        <img class="profile" :src="state.profile" />
      </el-button>
      <el-button type="primary btn" plain @click="clickRoomCreate"
        >방 생성</el-button
      >
      <el-button type="info btn" plain @click="clickLogout">로그아웃</el-button>
    </div>
  </el-row>
</template>

<style scoped>
.navbar {
  padding-top: 5px;
  justify-content: space-around;
  align-items: center;
  background-color: #d3c4ed;
}

.navbar .search-bar {
  width: 400px;
  display: inline;
  margin: 20px;
}

.btn-group {
  display: block;
}

.circle {
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

.btn {
  margin: 0px 5px 0px;
}
</style>

<script>
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { reactive, computed, onMounted } from "vue";

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
        return store.getters["root/getActiveMenuIndex"];
      })
    });

    store
      .dispatch("auth/requestUserInfo", localStorage.getItem("jwt"))
      .then(function(result) {
        // console.log(result.data.picture)
        store.commit("auth/setUserProfile", result.data.picture);
      });

    const clickLogo = () => {
      store.commit("root/setActiveCategory", null);
      store.commit("root/setMenuActive", 0);
      const MenuItems = store.getters["root/getMenus"];
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
      store.commit("root/setMenuActive", 1);
      store.commit("root/setActiveCategory", "all");
      router.push({
        path: "/category/all"
      });
    };

    const clickMyPage = function() {
      let nowIndex = store.getters["root/getActiveMenuIndex"];
      if (nowIndex != 2) {
        store.commit("root/setMenuActive", 2);
        router.push({
          path: "/mypage/keyword"
        });
      }
    };

    const clickLogout = function() {
      console.log("clickLogout");
      store
        .dispatch("auth/requestLogout")
        .then(() => store.commit("auth/setLogout"))
        .then(() => router.push("/"));
      console.log(state.isLoggedin);
    };

    const enterSearch = function() {
      store.commit("root/setSearchWord", state.searchWord);
      router.push({
        name: "search-result",
        params: {
          searchWord: state.searchWord
        }
      });
      state.searchWord = "";
    };

    return {
      state,
      clickLogo,
      clickJoin,
      clickLogin,
      clickRoomCreate,
      clickCategory,
      clickMyPage,
      clickLogout,
      enterSearch
    };
  }
};
</script>

<style></style>
