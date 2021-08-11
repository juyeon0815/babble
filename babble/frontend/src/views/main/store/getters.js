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

export function getLoadingStatus (state) {
	return state.loadingStatus;
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

export function getRoomID(state) { // 입장하는 방 이름
	return state.roomId
}

export function getPublisher(state) {
	return state.publisher
}
export function getSubscribers(state) {
	return state.subscribers
}
export function getUserName(state) {
	return state.userName
}
export function getUserVideoStatus(state) {
	return state.userVideoStatus
}
export function getUserAudioStatus(state) {
	return state.userAudioStatus
}
export function getIsHost(state) {
	return state.isHost
}