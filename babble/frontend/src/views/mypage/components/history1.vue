<template>
  <div class="tab">
    <h3>{{ state.email }}님의 시청이력</h3>
    <!-- <div>{{state.historyData}}</div> -->
    <el-table
      :data="state.historyData"
      class="table"
      ref="filterTable"
      :default-sort="{ prop: 'createDate', order: 'descending' }"
      empty-text="No Data"
    >
      <el-table-column prop="title" label="방 제목"> </el-table-column>
      <el-table-column
        prop="category"
        label="카테고리"
        :filters="[
          { text: 'sports', value: 'sports' },
          { text: 'cooking', value: 'cooking' },
          { text: 'handcraft', value: 'handcraft' },
          { text: 'music', value: 'music' },
          { text: 'finance', value: 'finance' },
          { text: 'game', value: 'game' },
          { text: 'movie', value: 'movie' },
          { text: 'drawing', value: 'drawing' },
          { text: 'book', value: 'book' },
          { text: 'pet', value: 'pet' }
        ]"
        :filter-method="filterCategory"
        filter-placement="right-start"
      >
      </el-table-column>
      <el-table-column prop="viewDateFix" label="날짜" sortable>
      </el-table-column>
      <el-table-column prop="viewStartFix" label="시청 시작"> </el-table-column>
      <el-table-column prop="viewEndFix" label="시청 종료"> </el-table-column>
      <el-table-column prop="maxView" label="최대시청자수"> </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { reactive, computed, onMounted, ref } from "vue";
import { useStore } from "vuex";

export default {
  name: "History1",
  setup(props, { emit }) {
    const store = useStore();
    const filterTable = ref(null);
    const state = reactive({
      email: computed(() => {
        return store.getters["auth/getEmail"];
      }),
      historyData: []
    });
    store
      .dispatch("auth/requestViewHistory1", { email: state.email })
      .then(function(result) {
        console.log('********************')
        console.log(result.data)
        let data = result.data;
        // console.log(data, '처음 받아온 데이터')
        // console.log(result.data[0].viewDate)
        for (let i = 0; i < data.length; i++) {
          let dateHuman = result.data[i].viewDate.slice(0, 10);
          let startTimeHuman = result.data[i].viewStart.slice(11, 19);
          let endTimeHuman = result.data[i].viewEnd.slice(11, 19);
          data[i]["viewDateFix"] = dateHuman;
          data[i]["viewStartFix"] = startTimeHuman;
          data[i]["viewEndFix"] = endTimeHuman;
        }
        // console.log(data, '추가 후 데이터')
        state.historyData = result.data;
      })
      .catch(function(err) {
        alert(err);
      });

    const clearFilter = function() {
      console.log(filterTable.clearFilter); //undefined...?
      return filterTable.clearFilter;
    };

    //카테고리 선택 후, confirm하면 실행되는 함수
    const filterCategory = function(value, row) {
      // console.log(row, value)
      return row.category === value;
    };

    return { state, filterTable, clearFilter, filterCategory };
  }
};
</script>

<style>
.tab {
  margin-left: 60px;
}
.table {
  width: 90%;
}
</style>
