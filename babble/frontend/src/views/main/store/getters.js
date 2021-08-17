// 플랫폼 관련 정보 가져오기
export function getIsDesktopPlatform (state) {
  return state.isDesktopPlatform
}

export function getLoadingStatus (state) {
	return state.loadingStatus;
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
export function getMainStreamManager(state) {
	return state.mainStreamManager
}