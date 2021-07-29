// import { search } from "core-js/fn/symbol"

export function setPlatform (state, isDesktop) {
  state.isDesktopPlatform = isDesktop
}

export function setMenuActive (state, index) {
	console.log('setMenuActive', state, index)
	const keys = Object.keys(state.menus)
	state.activeMenu = keys[index]
}

export function setMenuActiveMenuName (state, menuName) {
	state.activeMenu = menuName
}

export function setLogout (state) {
  console.log('토큰 없앤다!')
	state.token = null
}

export function setEmail (state, email) {
  console.log('이메일 저장한다!')
	state.email = email
}

export function startSpinner (state) {
	state.loadingStatus = true;
}

export function endSpinner (state) {
	state.loadingStatus = false;
}

export function setActiveCategory (state, categoryName) {
	state.activeCategory = categoryName
}

export function setSearchWord(state, searchWord) {
	state.searchWord = searchWord
}