<template>
  <svg
    width="100%"
    height="100%"
    xmlns="http://www.w3.org/2000/svg"
    :viewBox="`0 0 200 200`"
    style="max-width: 100%; max-height: 100%"
  >
    <!-- Circle for gakLength 1 -->
    <circle
      v-if="props.gakLength === 1"
      cx="100"
      cy="100"
      r="90"
      fill="lightgray"
      stroke="black"
      stroke-width="2"
    />

    <!-- Two equal halves for gakLength 2 -->
    <g v-else-if="props.gakLength === 2">
      <circle
        cx="100"
        cy="100"
        r="90"
        fill="lightgray"
        stroke="black"
        stroke-width="2"
      />
      <path
        d="M100 10 A90 90, 0, 1, 0, 100 190 Z"
        fill="gray"
        stroke="black"
        stroke-width="2"
      />
    </g>

    <!-- Dynamic Pizza Slices with Labels -->
    <g v-else v-for="(slice, index) in pizzaSlices" :key="index">
      <polygon
        :points="slice.points"
        :fill="slice.color"
        stroke="black"
        stroke-width="2"
      />
    </g>
  </svg>
  {{ props.gakLength }}
</template>

<script setup>
import { ref, computed, defineProps } from 'vue';

const props = defineProps({
  gakLength: Number,
});

const numSlices = ref(props.gakLength);

const pizzaSlices = computed(() => {
  const radius = 90;
  const cx = 100;
  const cy = 100;
  return calculatePizzaSlices(numSlices.value, radius, cx, cy);
});

const calculatePizzaSlices = (slices, radius, cx, cy) => {
  const angleIncrement = (2 * Math.PI) / slices;
  const startAngle = -Math.PI / 2; // Start from the 12 o'clock direction
  const labelOffset = 15; // Adjust the label offset from the center

  const slicePolygons = [];
  const colors = ['white', 'black', 'lightgray', 'gray']; // Add more colors as needed

  for (let i = 0; i < slices; i++) {
    const angle1 = startAngle + i * angleIncrement;
    const angle2 = startAngle + (i + 1) * angleIncrement;

    const x1 = cx + radius * Math.cos(angle1);
    const y1 = cy + radius * Math.sin(angle1);

    const x2 = cx + radius * Math.cos(angle2);
    const y2 = cy + radius * Math.sin(angle2);

    // Include the center of the circle as a vertex to create a triangle
    const slicePoints = `${cx},${cy} ${x1},${y1} ${x2},${y2}`;

    // Calculate label position
    const labelAngle = angle1 + angleIncrement / 2;
    const labelRadius = radius + labelOffset; // Adjust label distance from the center
    const labelX = cx + labelRadius * Math.cos(labelAngle);
    const labelY = cy + labelRadius * Math.sin(labelAngle);

    slicePolygons.push({
      points: slicePoints,
      label: `과목 ${i + 1}`,
      labelX,
      labelY,
      color: colors[i % colors.length], // Assign colors in a circular manner
    });
  }

  return slicePolygons;
};
</script>
