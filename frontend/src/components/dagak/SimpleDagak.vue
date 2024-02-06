 
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
  
