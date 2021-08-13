<template>
  <!--받아오는 룸인포가 없다면 아예 카드가 안 보이게?-->
  <div v-if="roomInfo">
    <el-card class="conference-card">
      <div class="hide-show">
        <div
          v-if="
            roomInfo.thumbnailUrl == 'room_thumbnailUrl' ||
              roomInfo.thumbnailUrl == null ||
              roomInfo.thumbnailUrl == 'default'
          "
          class="image-cover"
        >
          <img src="https://picsum.photos/200" class="image" />
        </div>
        <div v-else class="image-cover">
          <img :src="roomInfo.thumbnailUrl" class="image" />
        </div>
        <p class="text">Let's Ba:bble</p>
      </div>

      <div class="card-bottom">
        <div class="stringcut">{{ roomInfo.title }}</div>
        <div>
          <el-tag class="tag" @click.stop="clickCategory(roomInfo.category)">{{
            roomInfo.category
          }}</el-tag>
        </div>
        <div v-if="roomInfo.hashtag && roomInfo.hashtag[0] !== ''">
          <div class="tag" v-for="i in roomInfo.hashtag.length" :key="i">
            <el-tag
              type="warning"
              @click.stop="clickHashtag(roomInfo.hashtag[i - 1])"
              >{{ roomInfo.hashtag[i - 1] }}</el-tag
            >
          </div>
        </div>
        <div><i class="el-icon-user"></i> {{ state.connNum }} Watching</div>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
import { computed, reactive, onMounted, onUnmounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + "i5a308.p.ssafy.io:8443";
const OPENVIDU_SERVER_SECRET = "BABBLE";

export default {
  props: {
    roomInfo: {
      type: Object
    }
  },

  setup(props) {
    const store = useStore();
    const router = useRouter();

    const state = reactive({
      connNum: 0
    });

    const getConnectionNum = function(roomInfo) {
      axios
        .get(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${roomInfo.id}`, {
          auth: {
            username: "OPENVIDUAPP",
            password: OPENVIDU_SERVER_SECRET
          }
        })
        .then(response => {
          console.log(roomInfo.id);
          state.connNum = response.data.connections.numberOfElements;
        })
        .catch(error => console.log(error));
    };

    getConnectionNum(props.roomInfo);

    const clickCategory = function(tag) {
      // console.log("click category");
      store.commit("menu/setActiveCategory", tag);
      router.push({
        path: `/category/${tag}`
      });
    };

    const clickHashtag = function(tag) {
      // console.log(tag)
      store.commit("menu/setMenuActive", 3);
      store.commit("menu/setSearchWord", tag);
      router.push({
        path: `/search/${tag}`
      });
    };

    // if (props.roomInfo.id %= 1) {
    //   console.log(propss.roomInfo.id)
    //   let card = document.querySelector(".conference-card")
    //   card.classList.add('change-color')
    // } else {
    //   card.classList.remove('change-color')
    // }

    return { state, clickCategory, getConnectionNum, clickHashtag };
  }
};
</script>

<style>
.conference-card {
  margin: 10px;
  transition: all 0.2s linear;
  position: relative;
}

.conference-card:hover {
  margin: 0 10px 0;
  transform: scale(1.05);
  border-width: 3px;
  border-style: solid;
  border-color: #a0a0ff;
}

.conference-card:hover.change-color {
  margin: 0 10px 0;
  transform: scale(1.05);
  border-width: 3px;
  border-style: solid;
  border-color: #9f05ff69;
}

/* .hide-show {
  transition: opacity 0.2s;
  position: relative;
} */

.conference-card .text {
  color: #fff;
  bottom: 220px;
  right: 1px;
  opacity: 0;
  position: absolute;
  text-align: center;
  width: 100%;
  transition: all 0.3s ease;
}

.conference-card .image-cover {
  display: block;
  transition: all 0.3s ease 0s;
}

.conference-card img {
  width: 200px;
  height: 200px;
  object-fit: cover;
}

.conference-card:hover .text {
  opacity: 1;
  transition: all 0.3s ease;
  cursor: pointer;
}

.conference-card:hover .image-cover {
  background-color: #a0a0ff;
}

.conference-card:hover img {
  transition: all 0.3s ease;
  opacity: 0.15;
}

.card-bottom {
  margin-top: 13px;
}

.card-bottom .tag {
  display: inline;
  cursor: pointer;
}

.card-bottom .stringcut {
  width: 200px;
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
