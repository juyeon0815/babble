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
  state.email = ''
  state.activeMenu = 'home'
  state.userHashtag = []
  state.alarmValue = false
}

export function setEmail (state, email) {
  console.log('이메일 저장한다!')
  console.log(state.email)
	state.email = email
  console.log(state.email)
}

export function setToken (state, token) {
  console.log('토큰을 store에 저장한다!')
  console.log(state.token)
	state.token = token
  console.log(state.token)
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

export function startSpinner (state) {
	state.loadingStatus = true;
}

export function endSpinner (state) {
	state.loadingStatus = false;
}

export function joinRoom(state, payload) {
  console.log("join Room");
  console.log(payload);
  state.roomId = payload;
}

export function setActiveCategory (state, categoryName) {
	state.activeCategory = categoryName
}

export function setSearchWord(state, searchWord) {
	state.searchWord = searchWord
}

export function setUserProfile (state, profile) {
  console.log('사용자 프로필 저장!')
  console.log(state.profile)
  state.profile = profile
  console.log(state.profile)
}

export function setUserHashtag (state, hashtag) {
  console.log('사용자 해시태그 db에서 가져와 store에 저장!')
  console.log(state.userHashtag)
  state.userHashtag = hashtag
  console.log(state.userHashtag)
}
