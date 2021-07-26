import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/home/home'
import Category from '@/views/categories/category'
import CategoryOrder from '@/views/categories/components/category-order'
import CategoryResult from '@/views/categories/components/category-result'
import ConferencesDetail from '@/views/conferences/conference-detail'

const fullMenu = require('@/views/main/menu.json')
const categories_list = ['all', 'sports', 'cooking', 'handcraft', 'music', 'finance', 'game', 'movie', 'drawing', 'book', 'pet']
const categoryChildren = []
for (let index = 0; index < categories_list.length; index++) {
  categoryChildren.push({
    name: `${categories_list[index]}`,
    path: `${categories_list[index]}`,
    component: CategoryOrder,
    children: [
      {
        path: 'best',
        component: CategoryResult,
        name: `${index}best`
      },
      {
        path: 'recent',
        component: CategoryResult,
        name: `${index}recent`
      }
    ]
  })
}

function makeRoutesFromMenu () {
  let routes = Object.keys(fullMenu).map((key) => {
    if (key === 'home') {
      return { path: fullMenu[key].path, name: key, component: Home  }
    } else if (key === 'category') {
      return { path: fullMenu[key].path, name: key, component: Category, children: categoryChildren}
    } else { // menu.json 에 들어있는 로그아웃 메뉴
      return null
    }
  })
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
