<template>
  <div class="tab">
    <h3>{{ state.email }}님의 방 생성이력</h3>
    <!-- <div>{{state.historyData}}</div> -->
    <!--[{ "title": "test1", "viewDate": "2021-07-26T12:40:12.409+00:00",}]-->
    <!-- <el-button @click="clearFilter">reset all filters</el-button> -->
    <div class="chart">
      <el-table
        :data="state.historyData"
        row-key="category"
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
        <el-table-column prop="createDate" label="생성 날짜" sortable>
        </el-table-column>
        <el-table-column prop="createTime" label="생성 시간"> </el-table-column>
        <el-table-column prop="maxView" label="최대시청자수"> </el-table-column>
      </el-table>
      <Chart :chartData="state.historyData"></Chart>
    </div>
  </div>
</template>

<script>
import { reactive, computed, ref } from "vue";
import { useStore } from "vuex";
import Chart from "./chart.vue";

export default {
  components: {
    Chart
  },
  name: "History2",
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
      .dispatch("auth/requestViewHistory2", { email: state.email })
      .then(function(result) {
        let data = result.data;
        // console.log(data, '처음 받아온 데이터')
        // console.log(result.data[0])
        // console.log(result.data[0].viewDate)
        for (let i = 0; i < data.length; i++) {
          let dateHuman = result.data[i].viewDate.slice(0, 10);
          let timeHuman = result.data[i].viewDate.slice(11, 19);
          data[i]["createDate"] = dateHuman;
          data[i]["createTime"] = timeHuman;
        }
        // console.log(data, '추가 후 데이터')
        state.historyData = result.data;
      })
      .catch(function(err) {
        alert(err);
      });

    // const formatterDate = function (row, column, cellValue) {
    //   console.log(cellValue, '들어오니')
    //   return cellValue.substr(0, 9)
    // }

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

<style scoped>
.tab {
  margin-left: 60px;
}
.chart {
  display: flex;
  flex-wrap: wrap;
}

.table {
  width: 60%;
}
@media screen and (max-width: 480px) {
  .tab {
  }
  .chart {
    display: flex;
    flex-wrap: wrap;
  }

  .table {
    width: 100%;
  }
}
</style>
