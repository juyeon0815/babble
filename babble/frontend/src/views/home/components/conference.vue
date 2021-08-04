<template>
  <!--받아오는 룸인포가 없다면 아예 카드가 안 보이게?-->
  <el-card class="conference-card">
    <div v-if="roomInfo">
      <div v-if="roomInfo.thumbnailUrl == 'room_thumbnailUrl' || roomInfo.thumbnailUrl == null">
        <img src="https://picsum.photos/200" class="image">
      </div>
      <div v-else>
        <img :src="roomInfo.thumbnailUrl" class="image">
      </div>
      <div class="card-bottom">
        <div class="name-area">
          <div class="stringcut t1">{{ roomInfo.title }}</div>
          <el-tag class="tag" @click="clickCategory">{{ roomInfo.category }}</el-tag>
        </div>
        <div class="tag" v-for="i in roomInfo.hashtag.length" :key="i">
          <el-tag type="warning">{{ roomInfo.hashtag[i-1] }}</el-tag>
        </div>
        <div><i class="el-icon-user"></i> {{ roomInfo.viewers }} Watching</div>
      </div>
    </div>
  </el-card>
</template>

<script>
export default {
  props: {
    roomInfo : {
      type: Object
    }
  },

  setup () {
    const clickCategory = () => {
      console.log('click category')
      // 해당 카테고리 페이지로 이동
      // 카드 자체가 회의실로 이동? - preventDefault? 아니면 회의실로 가는게 맞나?
      // 그럼 버튼으로 하는게?
      // 키워드도 같은 고민
    }

    return { clickCategory }
  }
}
</script>

<style>
  .conference-card {
    margin: 0 10px 0;
  }

  .card-bottom {
    margin-top: 13px;
  }

  .image {
    width: 100%;
    display: block;
  }

  .card-bottom .name-area {
    /* justify-content: start; */
    display: block;
  }

  .card-bottom .tag {
    display: inline;
  }

  .card-bottom .name-area .stringcut {
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
  }

  .card-bottom .name-area .stringcut .t1 {
    width: 10px;
  }
</style>
