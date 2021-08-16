<template>
  <div class="error-body">
    <h1>
      <span>E</span>
      <span>R</span>
      <span>R</span>
      <span>O</span>
      <span>R</span>
      <span>ㅤ</span>
      <span>:</span>
      <span>(</span>
    </h1>

    <h2>존재하지 않는 페이지입니다.</h2>
    <el-button class="el-icon-s-home home-btn" @click="goHome"></el-button>
  </div>
</template>
<script>
import { onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  setup(props) {
    const store = useStore();
    const router = useRouter();

    onMounted(() => {
      store.commit("menu/setMenuActive", -1);
    });

    const goHome = function() {
      const MenuItems = store.getters["menu/getMenus"];
      store.commit("menu/setActiveCategory", null);
      store.commit("menu/setMenuActive", 0);
      let keys = Object.keys(MenuItems);
      router.push({
        name: keys[0]
      });
    };
    return { goHome };
  }
};
</script>
<style scoped>
@font-face {
  font-family: "this_is_font_name";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/SDSamliphopangche_Outline.woff")
    format("woff");

  font-weight: normal;
  font-style: normal;
}

.error-body {
  position: absolute;

  font-family: this_is_font_name;
  width: 100%;
  height: 100vh;
  background: linear-gradient(to right, rgb(254, 227, 255), rgb(173, 101, 235));
  -webkit-font-smoothing: antialiased;
  justify-content: center;
  text-align: center;
  align-items: center;
}

.home-btn {
  color: white;
  background-color: #11ffee00;
  border: none;
  font-size: 30pt;
  text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
    0 5px 0 #ccc, 0 6px 0 #ccc, 0 7px 0 #ccc, 0 8px 0 #ccc, 0 9px 0 #ccc,
    0 50px 25px rgba(0, 0, 0, 0.2);
}

h1 {
  margin-top: 41vh;
  font-family: this_is_font_name;
  color: #fff;
  text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
    0 5px 0 #ccc, 0 6px 0 transparent, 0 7px 0 transparent, 0 8px 0 transparent,
    0 9px 0 transparent, 0 10px 10px rgba(0, 0, 0, 0.4);
}

h2 {
  color: rgb(255, 255, 255);
  font-size: 30px;
  font-family: this_is_font_name;
  text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
    0 5px 0 #ccc, 0 6px 0 #ccc, 0 7px 0 #ccc, 0 8px 0 #ccc, 0 9px 0 #ccc,
    0 50px 25px rgba(0, 0, 0, 0.2);
}

h1 span {
  position: relative;
  top: 20px;
  display: inline-block;
  animation: bounce 0.4s ease infinite alternate;
  font-family: this_is_font_name;
  margin: 5px;
  font-size: 80px;
  color: #fff;
  text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
    0 5px 0 #ccc, 0 6px 0 transparent, 0 7px 0 transparent, 0 8px 0 transparent,
    0 9px 0 transparent, 0 10px 10px rgba(0, 0, 0, 0.4);
}

h1 span:nth-child(2) {
  animation-delay: 0.1s;
}
h1 span:nth-child(3) {
  animation-delay: 0.2s;
}
h1 span:nth-child(4) {
  animation-delay: 0.3s;
}
h1 span:nth-child(5) {
  animation-delay: 0.4s;
}
h1 span:nth-child(6) {
  animation-delay: 0.5s;
}
h1 span:nth-child(7) {
  animation-delay: 0.6s;
}
h1 span:nth-child(8) {
  animation-delay: 0.7s;
}

@keyframes bounce {
  100% {
    top: -20px;
    text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc, 0 3px 0 #ccc, 0 4px 0 #ccc,
      0 5px 0 #ccc, 0 6px 0 #ccc, 0 7px 0 #ccc, 0 8px 0 #ccc, 0 9px 0 #ccc,
      0 50px 25px rgba(0, 0, 0, 0.2);
  }
}
</style>
