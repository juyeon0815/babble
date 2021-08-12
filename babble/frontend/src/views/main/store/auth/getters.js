
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

export function getProvider (state){
  return state.provider
}
