<template>
  <el-card class="conference-card">
      <img src="https://picsum.photos/200" class="image">
      <div>
        <div class="card-bottom">
          <div>{{ num }} Room Name</div>
          <el-tag class="category-tag" @click="clickCategory">Category</el-tag>
          <el-tag class="keyword-tag" type="warning">Keyword1</el-tag>
          <el-tag class="keyword-tag" type="warning">Keyword2</el-tag>
          <div><i class="el-icon-user"></i> 389 Watching</div>
        </div>
      </div>
    </el-card>
</template>

<script>
export default {
  props: {
    num: {
      type: Number
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

  .card-bottom .category-tag {
    display: block;
  }

  .card-bottom .keyword-tag {
    display: inline;
  }
</style>