// ROOT STATE 변수 정의 및 기본값 대입

const menuData = require('@/views/main/menu.json')

export default {
  activeMenu: 'home',
  menus: menuData,
  activeCategory: null,
  searchWord: '',
}
