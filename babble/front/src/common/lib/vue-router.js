import { createRouter, createWebHistory } from 'vue-router'

//아래 내용 수정 필요
import Home from '@/views/home/home'
import ConferencesDetail from '@/views/conferences/conference-detail'


function makeRoutesFromMenu () {
  let routes = null
  // 로그아웃 파싱한 부분 제거
  routes = routes.filter(item => item)
  // menu 자체에는 나오지 않는 페이지 라우터에 추가(방 상세보기)
  routes.push({
    path: '/conferences/:conferenceId',
    name: 'conference-detail',
    component: ConferencesDetail
  })
  return routes
}

const routes = makeRoutesFromMenu()

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.afterEach((to) => {
  console.log(to)
})

export default router
