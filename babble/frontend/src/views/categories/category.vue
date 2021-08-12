<template>
  <div class="container">
    <div class="tab-menu">
      <router-link to="all"><button class="tab-menu-link" id="all">전체보기</button></router-link>
      <router-link to="sports"><button class="tab-menu-link" id="sports">스포츠</button></router-link>
      <router-link to="cooking"><button class="tab-menu-link" id="cooking">요리</button></router-link>
      <router-link to="handcraft"><button class="tab-menu-link" id="handcraft">수공예</button></router-link>
      <router-link to="music"><button class="tab-menu-link" id="music">음악</button></router-link>
      <router-link to="finance"><button class="tab-menu-link" id="finance">금융</button></router-link>
      <router-link to="game"><button class="tab-menu-link" id="game">게임</button></router-link>
      <router-link to="movie"><button class="tab-menu-link" id="movie">영화</button></router-link>
      <router-link to="drawing"><button class="tab-menu-link" id="drawing">그림</button></router-link>
      <router-link to="book"><button class="tab-menu-link" id="book">독서</button></router-link>
      <router-link to="pet"><button class="tab-menu-link" id="pet">반려동물</button></router-link>
    </div>
  </div>

  <router-view></router-view>
</template>

<style scoped>
  .container {
    margin: 20px 0 20px 0;
  }
  .tab-menu {
    display: flex;
    justify-content: space-between;
    align-items: center;
    max-width: 85%;
    height: auto;
    margin: 0 0 0 40px;
    transition: all 0.3s ease;
    border-bottom: 1.3px solid lightgrey;
  }
  .tab-menu-link {
    font-size: 1rem;
    font-weight: bold;
    cursor: pointer;
    width: 70px;
    padding: 1rem 0;
    border-bottom: 2.5px solid transparent;
    color: grey;
    background: white;
    transition: all 0.3s ease;
    border: none;
  }
  .active {
    bottom: 0px;
    z-index: 0;
    overflow: hidden;
    border-bottom: 3px solid #8860D8;
  }
</style>

<script>
import { reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { onBeforeRouteUpdate } from 'vue-router'
import Conference from '../home/components/conference'

export default {
  name: 'Category',
  components: {
    Conference
  },
  setup () {
    const store = useStore()
    const state = reactive({
      current: 1,
      categories_list: ['all', 'sports', 'cooking', 'handcraft', 'music', 'finance', 'game', 'movie', 'drawing', 'book', 'pet']
    })

    onBeforeRouteUpdate ((to, from) => {
      store.commit('menu/setActiveCategory', to.name)
      const newTab = document.getElementById(to.name)
      newTab.classList.add("active")
      const oldTab = document.getElementById(from.name)
      oldTab.classList.remove("active")
    })

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('menu/setMenuActiveMenuName', 'category')
      const allTab = document.getElementById('all')
      allTab.classList.add("active")
    })

    return { state }
  },
}
</script>
