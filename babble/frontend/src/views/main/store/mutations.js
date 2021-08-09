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

export function setUserHashtagPush (state, input) {
  console.log('사용자 해시태그를 저장한다!')
  console.log(state.userHashtag)
  if (input) {
    state.userHashtag.push(input)
  }
  console.log(state.userHashtag)
}

export function setUserHashtagDelete (state, input) {
  console.log('사용자 해시태그를 삭제한다!')
  console.log(state.userHashtag)
  state.userHashtag.splice(input, 1)
  console.log(state.userHashtag)
}

export function setAlarm (state) {
  console.log('사용자 알림설정 변경!')
  console.log(state.alarmValue)
  state.alarmValue = !state.alarmValue
  console.log(state.alarmValue)
}

export function setDefaultAlarm (state, payload) {
  console.log('사용자 알림설정 db에서 가져오기!')
  console.log(state.alarmValue)
  state.alarmValue = payload
  console.log(state.alarmValue)
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


// OV: undefined,
// session: undefined,
// mainStreamManager: undefined,
// publisher: undefined,
// subscribers: [],
// videoStatus: true,
// audioStatus: true,

export function setOV(state,ov) {
  state.OV = ov
}
export function setSession(state, session) {
  state.session = session
}
export function setMainStreamManager(state,manager) {
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
  // state.subscribers = subscribers
  state.subscribers = subscribers;
}
