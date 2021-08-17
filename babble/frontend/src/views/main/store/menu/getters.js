// 메뉴 객체 가져오기
export function getMenus (state) {
	return state.menus
}
// Active된 메뉴 인덱스 가져오기
export function getActiveMenuIndex (state) {
	const keys = Object.keys(state.menus)
	return keys.findIndex(item => item === state.activeMenu)
}

export function getActiveCategory (state) {
	return state.activeCategory
}

export function getSearchWord (state) {
	return state.searchWord
}

export function getMainStreamManager (state) {
	return state.mainStreamManager
}
