<template>
  <h3>Room title <i class="el-icon-user-solid"></i> 0명</h3>

  <div class="temp">
    <div class="dummy"></div>
    <div class="dummy"></div>
  </div>
  <div class="temp">
    <div class="dummy"></div>
    <div class="dummy"></div>
  </div>
  

  <!-- bootstrap -> element plus 예정(grid/style) -->
  <div id="main-video" class="col-md-6">
    <UserVideo :stream-manager="mainStreamManager"/>
  </div>
  <div id="video-container" class="col-md-6">
    <UserVideo :stream-manager="publisher" @click="updateMainVideoStreamManager(publisher)"/>
    <UserVideo v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click="updateMainVideoStreamManager(sub)"/>
  </div>

  <div class="nav-icons">
    <el-button-group>
      <el-button type="info" plain> <i class="el-icon-microphone"></i></el-button>
      <el-button type="info" plain> <i class="el-icon-video-camera"></i></el-button>
      <el-button type="info" plain> <i class="el-icon-thumb"></i></el-button>
      <el-button type="info" plain> <i class="el-icon-star-on"></i></el-button>
      <el-button type="info" plain> <i class="el-icon-error" @click="leaveSession"></i></el-button>
    </el-button-group>
  </div>
</template>

<script>
import { reactive, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'
import { OpenVidu } from 'openvidu-browser'
import UserVideo from './user-video'

export default {
  name: 'video-space',
  components: {
    UserVideo
  },
  setup () {
    const route = useRoute()
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      conferenceId: '',
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      
      mySessionId: 'SessionA',
      myUseName: '익명의' + '너구리' // DB 동물이름으로 교체
    })

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.conferenceId = route.params.conferenceId
      state.OV = new OpenVidu()
      state.session = state.OV.initSession()

      state.session.on('streamCreated', ({ stream }) => {
        const subscriber = state.session.subscribe(stream)
        state.subscribers.push(subscriber)
      })

      state.session.on('streamDestroyed', ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0)
        if (index >= 0) {
          state.subscribers.splice(index, 1)
        }
      })

      state.session.on('exception', ({ exception }) => {
        console.warn(exception)
      })

      // token
      const getToken = async function (mySessionId) {
        const sessionId = await store.dispatch('root/requestOVSession', mySessionId)
        return await store.dispatch('root/requestOVToken', sessionId)
      }
      getToken(state.mySessionId).then(token => {
				state.session.connect(token, { clientData: state.myUserName })
					.then(() => {
						let publisher = state.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						})

						state.mainStreamManager = publisher
						state.publisher = publisher

						state.session.publish(this.publisher)
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					})
      })
    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      // 반영되는지 확인, 안될경우 leaveSession으로 통합
      state.conferenceId = ''
      if (state.session) state.session.disconnect()
			state.session = undefined
			state.mainStreamManager = undefined
			state.publisher = undefined
			state.subscribers = []
			state.OV = undefined
    })

    const leaveSession = function () {
      console.log('leave')
      store.commit('root/setActiveCategory', null)
      store.commit('root/setMenuActive', 0)
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      router.push({
        name: keys[0]
      })
    }

    const updateMainVideoStreamManager = function (stream) {
			if (state.mainStreamManager === stream) return
			state.mainStreamManager = stream
		}

    return { state, leaveSession, updateMainVideoStreamManager}
  }
}
</script>

<style>
  .sideChat {
    background-color: lightgrey;
    min-height: 600px;
  }
  
  .nav-icons {
    margin-top: 10px;
    text-align: center;
  }
  .nav-icons i {
    font-size: 25px;
  }

  .temp {
    text-align: center;
  }
  .dummy {
    display: inline-block;
    margin: 5px;
    width: 300px;
    height: 250px;
    background-color: lightblue;
  }
</style>