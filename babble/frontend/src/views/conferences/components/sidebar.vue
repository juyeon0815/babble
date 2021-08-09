<template>
  <el-tabs stretch="true" type="card" @tab-click="handleClick">
    <el-tab-pane label="참여자 정보">
      
      <div v-if="state.publisher" class="people-list">
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
        <!-- 다른 참가자 -->
        <ul class="list-item">
          <li v-for="sub in state.subs" :key="sub.id">
            <div class="about">
              <div class="name">{{ JSON.parse(sub.stream.connection.data).clientData }}</div>
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
  setup() {
    const store = useStore();

    const state = reactive({
      publisher: computed(() => store.getters["root/getPublisher"]),
      subs: computed(() => store.getters["root/getSubscribers"])
    });

    return { state };
  }
};
</script>

<style>
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
</style>