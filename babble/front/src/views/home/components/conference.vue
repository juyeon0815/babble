<template>
  <el-card class="conference-card">
      <img src="https://picsum.photos/200" class="image">
      <div>
        <span>{{ num }} conference</span>
        <div class="card-bottom">
          <div>Room Name</div>
          <div>Room infomation</div>
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
  }
}
</script>

<style>
  .conference-card {
    margin: 0 10px 0;
  }

  .card-bottom {
    padding: 14px;
    margin-top: 13px;
  }

  .image {
    width: 100%;
    display: block;
  }
</style>