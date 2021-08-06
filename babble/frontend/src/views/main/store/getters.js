// 플랫폼 관련 정보 가져오기
export function getIsDesktopPlatform (state) {
  return state.isDesktopPlatform
}
// 메뉴 객체 가져오기
export function getMenus (state) {
	return state.menus
}
// Active된 메뉴 인덱스 가져오기
export function getActiveMenuIndex (state) {
	const keys = Object.keys(state.menus)
	return keys.findIndex(item => item === state.activeMenu)
}

export function getToken (state) {
	return state.token
}

export function getEmail (state) {
	return state.email
}

export function getProfile (state) {
  return state.profile
}

export function getUserHashtag (state) {
	return state.userHashtag
}

export function getAlarm (state) {
	return state.alarmValue
}

export function getUserHashtagLength (state) {
  // console.log(state.userHashtag.length)
	return state.userHashtag.length
}
export function getLoadingStatus (state) {
	return state.loadingStatus;
}

export function getActiveCategory (state) {
	return state.activeCategory
}

export function getSearchWord (state) {
	return state.searchWord
}

export function getRoomID(state) { // 입장하는 방 이름
	return state.roomId
}

export function getOV(state) {
	return state.OV
}
export function getSession(state) {
	return state.session
}
export function getMainStreamManager(state) {
	return state.mainStreamManager
}
export function getPublisher(state) {
	return state.publisher
}
export function getSubscribers(state) {
	return state.subscribers
}
export function getVideoStatus(state) {
	return state.videoStatus
}
export function getAudioStatus(state) {
	return state.audioStatus
}