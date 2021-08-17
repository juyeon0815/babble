<template>
  <div class="chart">
    <vue3-chart-js
      :id="doughnutChart.id"
      ref="chartRef"
      :type="doughnutChart.type"
      :data="doughnutChart.data"
      :options="doughnutChart.options"
    ></vue3-chart-js>
  </div>
</template>

<script>
import Vue3ChartJs from "@j-t-mcc/vue3-chartjs";
import { ref } from "vue";
import { onMounted, reactive, onUpdated } from "vue";
export default {
  components: {
    Vue3ChartJs
  },
  props: {
    chartData: Object
  },
  setup(props) {
    const chartRef = ref(null);

    const state = reactive({
      pieChart: {
        id: "doughnut",
        type: "doughnut",
        data: {
          labels: [],
          datasets: {
            backgroundColor: [],
            data: []
          }
        },
        options: {
          plugins: {}
        }
      }
    });

    const doughnutChart = {
      id: "doughnut",
      type: "doughnut",
      data: {
        labels: ["VueJs", "EmberJs", "ReactJs", "AngularJs"],
        datasets: [
          {
            backgroundColor: ["#41B883", "#E46651", "#00D8FF", "#DD1B16"],
            data: [40, 20, 80, 10]
          }
        ]
      },
      options: {
        plugins: {}
      }
    };

    const updateChart = function() {
      const map = new Map();
      for (var i = 0; i < props.chartData.length; i++) {
        if (map.get(props.chartData[i].category)) {
          map.set(
            props.chartData[i].category,
            map.get(props.chartData[i].category) + props.chartData[i].maxView
          );
        } else {
          map.set(props.chartData[i].category, props.chartData[i].maxView);
        }
      }

      for (let [key, value] of map) {
        state.pieChart.data.labels.push(key);
        state.pieChart.data.datasets.backgroundColor.push(
          "#" + parseInt(Math.random() * 0xffffff).toString(16)
        );
        state.pieChart.data.datasets.data.push(value);
      }

      doughnutChart.options.plugins.title = {
        text: "카테고리별 시청자 수 통계",
        display: true
      };
      doughnutChart.data.labels = state.pieChart.data.labels;
      doughnutChart.data.datasets = [
        {
          backgroundColor: state.pieChart.data.datasets.backgroundColor,
          data: state.pieChart.data.datasets.data
        }
      ];
      chartRef.value.update(250);
    };

    onUpdated(() => {
      updateChart();
    });

    return {
      state,
      doughnutChart,
      updateChart,
      chartRef
    };
  }
};
</script>
<style scoped>
.chart {
  width: 30%;
}
@media screen and (max-width: 480px) {
  .chart {
    width: 100%;
  }
}
</style>
