<!-- <template>
<div class="example_area">
  <strong class="title">정오각형 예제</strong>
<svg width="100" height="100">
  <circle cx="50" cy="50" r="50" fill="salmon" />
  <polyline stroke="black" fill="none"
   points="50,0 97.5528, 34.5492 79.3893, 90.4508 20.6107, 90.4508 2.4471 34.5492 50, 0"/>
</svg>
</div>
<div class="input_area">
  <label for="input_radius">반지름: </label><input id="input_radius" type="text" value="50"><button id="button_ok" class="button_ok" type="button">확인</button>
</div>
<div class="pentagon_area">
  <strong class="title">생성된 정오각형</strong>
  <svg id="pentagon_svg" width="100" height="100">
  <circle id="pentagon_circle" cx="50" cy="50" r="50" fill="#2d3436" />
  <polygon id="pentagon" stroke="#ffeaa7" fill="none"
   points="50,0 97.5528, 34.5492 79.3893, 90.4508 20.6107, 90.4508 2.4471 34.5492 50, 0"/>
</svg>
</div>
</template>

<script setup>
let pentagonSVG = document.getElementById('pentagon_svg');
let pentagonCircle = document.getElementById('pentagon_circle');
let pentagon = document.getElementById('pentagon');
// let inputRadius = document.getElementById('input_radius');
let inputRadius = 50;
let buttonOk = document.getElementById('button_ok');
let radius = inputRadius.value;
let cx, cy, degree, pentagonPoints, radian, pointStr;

buttonOk.addEventListener('click', () => { 
  pentagonPoints = [];
  degree = 90;
  radius = cx = cy = 50;
  
  pentagonSVG.setAttribute('width', radius * 2);
  pentagonSVG.setAttribute('height', radius * 2);
  pentagonCircle.setAttribute('cx', cx);
  pentagonCircle.setAttribute('cy', cy);
  pentagonCircle.setAttribute('r', radius);
  
  for (let i = 0; i < 5; i++) {
    radian = Math.PI / 180 * degree; // radian으로 변환
    pentagonPoints.push({
      x: radius * Math.cos(radian) + radius,
      y: radius * Math.sin(radian + Math.PI) + radius
    });
    degree += 72;
  }
  
  pointStr = pentagonPoints.reduce((acc, point) => {
    acc += `${point.x}, ${point.y} `
    return acc;
  }, '');
  pentagon.setAttribute('points', pointStr);
});
</script>

<style lang="scss" scoped>
body {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.example_area {
  text-align: center;
}

.title {
  display: block;
}

.input_area {
  margin-top: 20px;
}

.button_ok {
  margin-left: 5px;
}

.pentagon_area {
  margin-top: 20px;
  text-align: center;
}

</style> -->
<!-- 
<template>
    <svg>
        <line x1="0" y1="0" x2="300" y2="200"
        stroke="black" stroke-width="1"></line>
    </svg>

    <svg width="200" height="200" xmlns="http://www.w3.org/2000/svg">
        <polygon points="100,10 176.6,50 176.6,150 100,190 23.4,150 23.4,50" fill="none" stroke="black" stroke-width="1" />
    </svg>
</template>

<script setup>

</script>

<style lang="scss" scoped>

</style> -->
<!-- 
<template>
    <div>
      <svg width="500" height="500" xmlns="http://www.w3.org/2000/svg">

        <circle cx="250" cy="250" r="200" fill="lightgray" stroke="black" stroke-width="2" />
        <polygon :points="dynamicPolygonPoints" fill="blue" stroke="black" stroke-width="2" />

        <polygon v-for="(points, index) in polygonPoints" :points="points" :fill="colors[index]" stroke="black" stroke-width="2" />
      </svg>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        polygonPoints: this.generatePolygonPoints(3, 9, 200, 250, 250), // 3 to 9-gons
        colors: ['blue', 'green', 'red', 'purple', 'orange', 'pink', 'brown'], // Colors for each polygon
      };
    },
    methods: {
      generatePolygonPoints(sides, maxSides, radius, cx, cy) {
        const points = [];
        for (let i = sides; i <= maxSides; i++) {
          const polygonPoints = this.calculatePolygonPoints(i, radius, cx, cy);
          points.push(polygonPoints.join(' '));
        }
        return points;
      },
      calculatePolygonPoints(sides, radius, cx, cy) {
        const angleIncrement = (2 * Math.PI) / sides;
        const polygonPoints = [];
        for (let i = 0; i < sides; i++) {
          const x = cx + radius * Math.cos(i * angleIncrement);
          const y = cy + radius * Math.sin(i * angleIncrement);
          polygonPoints.push(`${x},${y}`);
        }
        return polygonPoints;
      },
    },
  };
  </script>
   -->
<!-- 
   <template>
    <div>
      <svg width="500" height="500" xmlns="http://www.w3.org/2000/svg">

        <circle cx="250" cy="250" r="200" fill="lightgray" stroke="black" stroke-width="2" />

        <polygon :points="dynamicPolygonPoints" fill="blue" stroke="black" stroke-width="2" />
      </svg>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        numSides: 10, // Variable to control the number of sides
      };
    },
    computed: {
      dynamicPolygonPoints() {
        const radius = 200;
        const cx = 250;
        const cy = 250;
  
        return this.calculatePolygonPoints(this.numSides, radius, cx, cy).join(' ');
      },
    },
    methods: {
      calculatePolygonPoints(sides, radius, cx, cy) {
        const angleIncrement = (2 * Math.PI) / sides;
        const polygonPoints = [];
        for (let i = 0; i < sides; i++) {
          const x = cx + radius * Math.cos(i * angleIncrement);
          const y = cy + radius * Math.sin(i * angleIncrement);
          polygonPoints.push(`${x},${y}`);
        }
        return polygonPoints;
      },
    },
  };
  </script> -->
  
<!-- 
   <template>
    <div>
      <svg width="300" height="300" xmlns="http://www.w3.org/2000/svg">
        <circle cx="250" cy="250" r="200" fill="lightgray" stroke="black" stroke-width="2" />
        <polygon :points="dynamicPolygonPoints" fill="none" stroke="black" stroke-width="20" />
      </svg>
    </div>
  </template>
  
  <script>
  import { ref, computed } from 'vue';
  
  export default {
    setup() {
      const numSides = ref(10);
  
      const dynamicPolygonPoints = computed(() => {
        const radius = 200;
        const cx = 250;
        const cy = 250;
        return calculatePolygonPoints(numSides.value, radius, cx, cy).join(' ');
      });
  
      const calculatePolygonPoints = (sides, radius, cx, cy) => {
        const angleIncrement = (2 * Math.PI) / sides;
        const startAngle = -Math.PI / 2; // Start from the 12 o'clock direction
        const polygonPoints = [];
  
        for (let i = 0; i < sides; i++) {
          const angle = startAngle + i * angleIncrement;
          const x = cx + radius * Math.cos(angle);
          const y = cy + radius * Math.sin(angle);
          polygonPoints.push(`${x},${y}`);
        }
  
        return polygonPoints;
      };
  
      return {
        numSides,
        dynamicPolygonPoints,
      };
    },
  };
  </script> -->
  
   <template>
    <div>
      <svg width="200" height="200" xmlns="http://www.w3.org/2000/svg">
        <polygon :points="dynamicPolygonPoints" fill="none" stroke="black" stroke-width="5" />
      </svg>
    </div>
  </template>
  
  <script>
  import { ref, computed } from 'vue';
  
  export default {
    setup() {
      const numSides = ref(3);
  
      const dynamicPolygonPoints = computed(() => {
        const radius = 90;
        const cx = 100;
        const cy = 100;
        return calculatePolygonPoints(numSides.value, radius, cx, cy).join(' ');
      });
  
      const calculatePolygonPoints = (sides, radius, cx, cy) => {
        const angleIncrement = (2 * Math.PI) / sides;
        const startAngle = -Math.PI / 2; // Start from the 12 o'clock direction
        const polygonPoints = [];
  
        for (let i = 0; i < sides; i++) {
          const angle = startAngle + i * angleIncrement;
          const x = cx + radius * Math.cos(angle);
          const y = cy + radius * Math.sin(angle);
          polygonPoints.push(`${x},${y}`);
        }
  
        return polygonPoints;
      };
  
      return {
        numSides,
        dynamicPolygonPoints,
      };
    },
  };
  </script>
  
   <!-- <template>
    <div>
      <svg width="200" height="200" xmlns="http://www.w3.org/2000/svg">
        <circle cx="100" cy="100" r="90" fill="lightgray" stroke="black" stroke-width="2" />
        <polygon v-for="(slicePoints, index) in pizzaSlices" :key="index" :points="slicePoints" fill="none" stroke="black" stroke-width="1" />
      </svg>
    </div>
  </template>
  
  <script>
  import { ref, computed } from 'vue';
  
  export default {
    setup() {
      const numSlices = ref(12);
  
      const pizzaSlices = computed(() => {
        const radius = 90;
        const cx = 100;
        const cy = 100;
        return calculatePizzaSlices(numSlices.value, radius, cx, cy);
      });
  
      const calculatePizzaSlices = (slices, radius, cx, cy) => {
        const angleIncrement = (2 * Math.PI) / slices;
        const startAngle = -Math.PI / 2; // Start from the 12 o'clock direction
        const slicePolygons = [];
  
        for (let i = 0; i < slices; i++) {
          const angle1 = startAngle + i * angleIncrement;
          const angle2 = startAngle + (i + 1) * angleIncrement;
  
          const x1 = cx + radius * Math.cos(angle1);
          const y1 = cy + radius * Math.sin(angle1);
  
          const x2 = cx + radius * Math.cos(angle2);
          const y2 = cy + radius * Math.sin(angle2);
  
          // Include the center of the circle as a vertex to create a triangle
          const slicePoints = `${cx},${cy} ${x1},${y1} ${x2},${y2}`;
          slicePolygons.push(slicePoints);
        }
  
        return slicePolygons;
      };
  
      return {
        numSlices,
        pizzaSlices,
      };
    },
  };
  </script>
   -->
<!-- 
   <template>
    <div>
      <svg width="200" height="200" xmlns="http://www.w3.org/2000/svg">
        <circle cx="100" cy="100" r="90" fill="lightgray" stroke="black" stroke-width="2" />
        <g v-for="(slice, index) in pizzaSlices" :key="index">
          <polygon :points="slice.points" fill="none" stroke="black" stroke-width="1" />
          <text :x="slice.labelX" :y="slice.labelY" text-anchor="middle" dominant-baseline="middle" fill="orange" class="sub">{{ slice.label }}</text>
        </g>
      </svg>
    </div>
  </template>
  
  <script>
  import { ref, computed } from 'vue';
  
  export default {
    setup() {
      const numSlices = ref(9);
  
      const pizzaSlices = computed(() => {
        const radius = 90;
        const cx = 100;
        const cy = 100;
        return calculatePizzaSlices(numSlices.value, radius, cx, cy);
      });
  
      const calculatePizzaSlices = (slices, radius, cx, cy) => {
        const angleIncrement = (2 * Math.PI) / slices;
        const startAngle = -Math.PI / 2; // Start from the 12 o'clock direction
        const slicePolygons = [];
  
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
          const labelRadius = radius / 2; // Adjust label distance from center
          const labelX = cx + labelRadius * Math.cos(labelAngle);
          const labelY = cy + labelRadius * Math.sin(labelAngle);
  
          slicePolygons.push({
            points: slicePoints,
            // label: `과목 ${i + 1}`,
            label: "java",
            labelX,
            labelY,
          });
        }
  
        return slicePolygons;
      };
  
      return {
        numSlices,
        pizzaSlices,
      };
    },
  };
  </script>
  <style>
.sub{
    font-size:15px;
}

</style> -->
<!-- 
<template>
    <div>
      <svg width="200" height="200" xmlns="http://www.w3.org/2000/svg">
        <circle cx="100" cy="100" r="90" fill="lightgray" stroke="black" stroke-width="2" />
        <g v-for="(slice, index) in pizzaSlices" :key="index">
          <polygon :points="slice.points" fill="blue" stroke="black" stroke-width="2" />
          <text :x="slice.labelX" :y="slice.labelY" text-anchor="middle" dominant-baseline="middle" fill="black">{{ slice.label }}</text>
        </g>
      </svg>
    </div>
  </template>
  
  <script>
  import { ref, computed } from 'vue';
  
  export default {
    setup() {
      const numSlices = ref(6);
  
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
            label: `Slice ${i + 1}`,
            labelX,
            labelY,
          });
        }
  
        return slicePolygons;
      };
  
      return {
        numSlices,
        pizzaSlices,
      };
    },
  };
  </script>
   -->