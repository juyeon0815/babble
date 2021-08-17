<template>
  <div v-if="state.activeMenuIndex != -1" class="footer-container">
    <footer class="site-footer">
      <!-- <a href="#"><h6>About Us</h6></a> -->
      <h6 @click="aboutUs">About Us</h6>
      <p class="copyright-text">Copyright &copy; 2021 All Rights Reserved by Team Ba:bble</p>
    </footer>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from "vue-router";

export default {
  setup() {
    const store = useStore()
    const state = reactive ({
      activeMenuIndex: computed(() => {
        return store.getters['menu/getActiveMenuIndex']
      })
    })
    const router = useRouter()
    const route = useRoute()

    const aboutUs = function() {
      store.commit('menu/setMenuActive', 4)
      router.push({
        name: 'about-us'
      })
      .then((() =>window.scrollTo(0,0) ))
    }
    return { state, aboutUs }
  }
}
</script>

<style>
.site-footer
{
  background-color: lightgray;
  padding:20px 0 20px;
  font-size:15px;
  line-height:24px;
  color:#737373;
  margin: 80px 0 0;
}
.site-footer p {
  margin: 3px;
}
.site-footer h6
{
  color:#fff;
  font-size:16px;
  text-transform:uppercase;
  margin:5px 0 20px 0;
  letter-spacing:2px;
  text-align: center;
  cursor: pointer;
}
.site-footer a {
  text-decoration: none;
}
.copyright-text
{
  margin: 0;
  text-align: center;
}
</style>
