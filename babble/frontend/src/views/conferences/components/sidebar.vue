<template>
  <el-tabs stretch="true" type="card" @tab-click="handleClick" class="tabs">
    <el-tab-pane label="참여자 정보" class="participants">
      <div v-if="state.publisher" class="people-list">
        <h3>{{ roomTitle }}</h3>
        <!-- 나 -->
        <div class="list-item">
          <div class="about">
            <div class="name">{{ JSON.parse(state.publisher.stream.connection.data).clientData }} (나)</div>
            <div class="status">
              <i
                v-if="state.publisher.stream.connection.stream.videoActive == true"
                style="color:red"
                class="el-icon-video-camera"
              />
              <i v-else type="danger" class="el-icon-video-camera" />
              <i
                v-if="state.publisher.stream.connection.stream.audioActive == true"
                style="color :red"
                class="el-icon-microphone"
              />
              <i v-else class="el-icon-turn-off-microphone" />
            </div>
          </div>
        </div>
      </div>
      <div v-else class="nologin-user">
        <p>비회원으로 방에 참여중입니다.</p>
      </div>
     
      <div v-if="state.subs.length > 0" class="people-list">
        <!-- 다른 참가자 -->
        <ul class="list-item">
          <li v-for="sub in state.subs" :key="sub.id">
            <div class="about">
              <div class="name" :id="sub.id">{{ JSON.parse(sub.stream.connection.data).clientData }}</div>
              <div class="status">
                <i
                  v-if="sub.stream.connection.stream.videoActive == true"
                  style="color:red"
                  class="el-icon-video-camera"
                />
                <i v-else type="danger" class="el-icon-video-camera" />
                <i
                  v-if="sub.stream.connection.stream.audioActive == true"
                  style="color :red"
                  class="el-icon-microphone"
                />
                <i v-else class="el-icon-turn-off-microphone" />
              </div>
            </div>
          </li>
        </ul>
      </div>
    </el-tab-pane>
    <el-tab-pane label="채팅">
      <Chats />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import { computed, reactive } from "vue"
import Chats from "./chats"
import { useStore } from "vuex"

export default {
  name: "sidebar",
  components: {
    Chats
  },
  props: {
    roomTitle: {
      type: String
    },
  },
  setup(props) {
    const store = useStore();

    const state = reactive({
      publisher: computed(() => store.getters["root/getPublisher"]),
      subs: computed(() => store.getters["root/getSubscribers"]),
    })

    return { state };
  }
};
</script>

<style>
  .people-list > h3 {
    margin: 0 0 10px 10px;
  }
  .people-list ul {
    list-style-type: none;
    padding: 10px;
  }
  .people-list li {
    margin-bottom: 10px;
  }
  .people-list .list-item {
    background-color: white;
    margin: 0 0 10px 5px;
    padding: 10px;
    border-radius: 3px;
    box-shadow: 0 0 2px rgba(0,0,0,.12),0 2px 4px rgba(0,0,0,.24);
    width: 90%
  }
  .people-list .about {
    display: flex;
    justify-content: space-between;
  }
  .people-list .about .name {
    display: block;
  }
  .people-list i {
    font-size: 20px;
    margin-right: 10px;
  }

  /* .el-tabs__item.is-active{
    color: white;
  }

  .el-tabs__item:hover {
    color: white;
  } */

  .el-tabs--card>.el-tabs__header .el-tabs__item.is-active {
    border-bottom-color: #666fe6c2;
  }

  .nologin-user > p {
    margin: 5px 0 5px 20px;
    color: lightgray;
  }

</style>
