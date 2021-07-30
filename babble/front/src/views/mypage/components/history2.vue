<template>
<div class="tab">
  <h3>{{state.email}}님의 방 생성이력</h3>
  <!-- <div>{{state.historyData}}</div> -->
  <!--[{ "title": "test1", "viewDate": "2021-07-26T12:40:12.409+00:00",}]-->
  <el-table :data="state.historyData" class="table">
    <el-table-column prop="title" label="방 제목">
    </el-table-column>
    <el-table-column prop="category" label="카테고리">
    </el-table-column>
    <!-- <div :data="state.viewDate"> -->
    <el-table-column prop="viewDate" label="날짜" :formatter="formatterDate">
      <!-- <template slot-scope="props">
        {{ props.row.viewDate | moment("MMMM Do YYYY")}}
      </template> -->
    </el-table-column>
    <!-- </div> -->
    <el-table-column prop="viewDate" label="시각">
    </el-table-column>
    <el-table-column prop="viewStart" label="시작">
    </el-table-column>
    <el-table-column prop="viewEnd" label="끝">
    </el-table-column>
    <el-table-column prop="maxView" label="최대시청자수">
    </el-table-column>
  </el-table>
</div>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'History2',
  setup (props, {emit}) {
    const store = useStore()
    const state = reactive({
      email: computed(() => {
        return store.getters['root/getEmail']
      }),
      historyData: [],
      // viewDate: [],


    })
    store.dispatch('root/requestViewHistory2', {email: state.email})
    .then(function (result) {
      console.log(result.data)
      state.historyData = result.data
      // state.historyData.push()
      console.log(state.historyData)
      console.log(result.data[0].viewDate)
      // state.viewDate.push(result.data[0].viewDate)
      // console.log(state.viewDate)
    })
    .catch(function (err) {
      alert(err)
    })

    const formatterDate = function (row, column, cellValue) {
      console.log(cellValue, '들어오니')
      return cellValue.substr(0, 9)

    }




  return {state, formatterDate}
  }

}
</script>

<style>
  .tab {
    margin-left: 60px;
  }
  .table {
    width: 90%;
  }
</style>
