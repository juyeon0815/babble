
export function setMenuActive (state, index) {
	console.log('setMenuActive', state, index)
	const keys = Object.keys(state.menus)
	state.activeMenu = keys[index]
}

export function setMenuActiveMenuName (state, menuName) {
	state.activeMenu = menuName
}

export function setActiveCategory (state, categoryName) {
	state.activeCategory = categoryName
}

export function setSearchWord(state, searchWord) {
	state.searchWord = searchWord
}
