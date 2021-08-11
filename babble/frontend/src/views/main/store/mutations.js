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

export function startSpinner(state) {
	state.loadingStatus = true;
}

export function endSpinner(state) {
	state.loadingStatus = false;
}

export function joinRoom(state, payload) {
  state.roomId = payload;
}

export function setActiveCategory (state, categoryName) {
	state.activeCategory = categoryName
}

export function setSearchWord(state, searchWord) {
	state.searchWord = searchWord
}

export function setOV(state,ov) {
  state.OV = ov
}
export function setSession(state, session) {
  state.session = session
}
export function setMainStreamManager(state, manager) {
  state.mainStreamManager = manager
}
export function setPublisher(state, publisher) {
  state.publisher = publisher
}
export function setSubscribers(state,subscriber) {
  // state.subscribers = subscribers
  state.subscribers.push(subscriber);
}

export function setClearSubscribers(state,subscribers) {
  state.subscribers = subscribers;
}

export function setUserName(state,name) {
  state.userName = name;
}
