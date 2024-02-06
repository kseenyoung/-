<template>
  <div>
    <svg width="200" height="200" xmlns="http://www.w3.org/2000/svg">
      <!-- Circle -->
      <!-- <circle cx="100" cy="100" r="90" fill="lightgray" stroke="black" stroke-width="2" /> -->

      <!-- Dynamic Pizza Slices with Labels -->
      <g v-for="(slice, index) in pizzaSlices" :key="index">
        <polygon :points="slice.points" :fill="slice.color" stroke="black" stroke-width="2" />
        <text
          :x="slice.labelX"
          :y="slice.labelY"
          text-anchor="middle"
          dominant-baseline="middle"
          fill="black"
        >
          {{ slice.label }}
        </text>
      </g>
    </svg>
  </div>
</template>

<script>
import { ref, computed } from 'vue'

export default {
  name: 'Dagak',
  setup() {
    const numSlices = ref(4)

    const pizzaSlices = computed(() => {
      const radius = 90
      const cx = 100
      const cy = 100
      return calculatePizzaSlices(numSlices.value, radius, cx, cy)
    })

    const calculatePizzaSlices = (slices, radius, cx, cy) => {
      const angleIncrement = (2 * Math.PI) / slices
      const startAngle = -Math.PI / 2 // Start from the 12 o'clock direction
      const labelOffset = 15 // Adjust the label offset from the center

      const slicePolygons = []
      const colors = ['white', 'black', 'lightgray', 'gray'] // Add more colors as needed

      for (let i = 0; i < slices; i++) {
        const angle1 = startAngle + i * angleIncrement
        const angle2 = startAngle + (i + 1) * angleIncrement

        const x1 = cx + radius * Math.cos(angle1)
        const y1 = cy + radius * Math.sin(angle1)

        const x2 = cx + radius * Math.cos(angle2)
        const y2 = cy + radius * Math.sin(angle2)

        // Include the center of the circle as a vertex to create a triangle
        const slicePoints = `${cx},${cy} ${x1},${y1} ${x2},${y2}`

        // Calculate label position
        const labelAngle = angle1 + angleIncrement / 2
        const labelRadius = radius + labelOffset // Adjust label distance from the center
        const labelX = cx + labelRadius * Math.cos(labelAngle)
        const labelY = cy + labelRadius * Math.sin(labelAngle)

        slicePolygons.push({
          points: slicePoints,
          label: `과목 ${i + 1}`,
          labelX,
          labelY,
          color: colors[i % colors.length] // Assign colors in a circular manner
        })
      }

      return slicePolygons
    }

    return {
      numSlices,
      pizzaSlices
    }
  }
}
</script>
