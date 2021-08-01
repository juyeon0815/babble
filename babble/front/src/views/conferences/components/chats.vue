<template>
  <el-tabs stretch="true" type="card" @tab-click="handleClick">
    <el-tab-pane class="in-tab" label="참여자 음성설정">
      <h3>Speaker</h3>
      <h3>Listener</h3>
    </el-tab-pane>
    <el-tab-pane class="in-tab" label="채팅">
      채팅내용
    </el-tab-pane>
  </el-tabs>
</template>

<script>
export default {

}
</script>

<style>
  .in-tab {
    margin: 20px;
  }
  .in-tab > h3 {
    margin-top: 0;
  }
</style>