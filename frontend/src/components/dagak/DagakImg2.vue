<template>
  <svg
    width="140%"
    height="140%"
    xmlns="http://www.w3.org/2000/svg"
    :viewBox="`80 0 250 250`"
    style="max-width: 140%; max-height: 140%"
  >
    <!-- Circle for gakLength 1 -->
    <g v-if="props.gakLength === 1">
      <circle cx="100" cy="100" r="70" :fill="colors[0]" stroke="black" stroke-width="2" />
      <text
        style="font-size: 100%; font-weight: bold"
        :x="pizzaSlices[0].labelX + 4"
        :y="pizzaSlices[0].labelY - 30"
        text-anchor="middle"
        dominant-baseline="left"
        fill="black"
      >
        {{ subjectMapping(pizzaSlices[0].label) }}
      </text>
    </g>

    <!-- Two equal halves for gakLength 2 -->
    <g v-else-if="props.gakLength === 2">
      <g v-for="(slice, index) in pizzaSlices" :key="index">
        <circle cx="100" cy="80" r="70" :fill="colors[0]" stroke="black" stroke-width="2" />
        <path
          d="M100 10 A70 70, 0, 1, 0, 100 150 Z"
          :fill="colors[1]"
          stroke="black"
          stroke-width="1"
        />
        <text
          style="font-size: 100%; font-weight: bold"
          :x="slice.labelX + 4"
          :y="slice.labelY - 30"
          text-anchor="middle"
          dominant-baseline="left"
          fill="black"
        >
          {{ subjectMapping(slice.label) }}
        </text>
      </g>
    </g>

    <!-- Dynamic Pizza Slices with Labels -->
    <g v-else v-for="(slice, index) in pizzaSlices" :key="index">
      <polygon :points="slice.points" :fill="slice.color" stroke="black" stroke-width="2" />
      <text
        style="font-size: 90%; font-weight: bold"
        :x="slice.labelX"
        :y="slice.labelY"
        text-anchor="middle"
        dominant-baseline="left"
        fill="black"
      >
        {{ subjectMapping(slice.label) }}
      </text>
    </g>
  </svg>
</template>

<script setup>
import { ref, computed, defineProps } from 'vue'
import { useCategoryStore } from '@/stores/category'
import { useDagakStore } from '@/stores/dagak'
import { subjectMapping } from '@/utils/subjectMapping'

const categoryStore = useCategoryStore()
const categoryNameList = ref(categoryStore.categoryList)
const dagakStore = useDagakStore()
const todayDagak = dagakStore.todayDagak
const gaksToStudy = ref(todayDagak.gaks)

const props = defineProps({
  gakLength: Number
})

const numSlices = ref(props.gakLength)

const pizzaSlices = computed(() => {
  const radius = 90
  const cx = 100
  const cy = 120
  return calculatePizzaSlices(numSlices.value, radius, cx, cy)
})

const colors = [
  'deepskyblue',
  'greenyellow',
  'gold',
  'coral',
  'darkgreen',
  'gray',
  'forestgreen',
  'aquamarine',
  'darkorange',
  'white',
  'black',
  'lightgray',
  'aqua',
  'cadetblue',
  'cornflowerblue'
]

const shuffledColors = colors.slice().sort(() => Math.random() - 0.5)

const calculatePizzaSlices = (slices, radius, cx, cy) => {
  const angleIncrement = (2 * Math.PI) / slices
  const startAngle = -Math.PI / 2
  const labelOffset = 15

  const slicePolygons = []

  for (let i = 0; i < slices; i++) {
    const angle1 = startAngle + i * angleIncrement
    const angle2 = startAngle + (i + 1) * angleIncrement

    const x1 = cx + radius * Math.cos(angle1)
    const y1 = cy + radius * Math.sin(angle1)

    const x2 = cx + radius * Math.cos(angle2)
    const y2 = cy + radius * Math.sin(angle2)

    const slicePoints = `${cx},${cy} ${x1},${y1} ${x2},${y2}`

    // Calculate label position
    const labelAngle = angle1 + angleIncrement / 2
    const labelRadius = radius + labelOffset
    const labelX = cx + labelRadius * Math.cos(labelAngle)
    const labelY = cy + labelRadius * Math.sin(labelAngle)

    slicePolygons.push({
      points: slicePoints,
      // label: `과목 ${i + 1}`,
      label: categoryNameList.value[gaksToStudy.value[i].categoryId - 1].categoryName,
      labelX,
      labelY,
      color: colors[i % colors.length]
      // color: shuffledColors[i % shuffledColors.length], // Random color
    })
  }

  return slicePolygons
}
</script>
