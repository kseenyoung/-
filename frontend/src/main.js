import { createApp } from 'vue';
import { createPinia } from 'pinia';

import App from './App.vue';
import router from './router';

import axios from 'axios';

axios.defaults.baseURL = 'https://localhost:8080';
axios.defaults.withCredentials = true;
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';

// npm install bootstrap-vue-3
import BootstrapVue3 from 'bootstrap-vue-3';
import 'bootstrap/dist/css/bootstrap.css';

// npm i bootstrap-icons
import 'bootstrap-icons/font/bootstrap-icons.css';

// npm install @vuepic/vue-datepicker
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

// npm install vue3-cookies --save

// npm i pinia-plugin-persistedstate
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

// npm i -S vuedraggable@next

import './assets/main.css';
import VueWriter from "vue-writer";

const app = createApp(App);

//pinia-plugin-persistedstate
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

app.use(pinia);
app.use(router);

//부트스트랩
app.use(BootstrapVue3);
app.use(VueWriter);
//데이트피커
app.component('VueDatePicker', VueDatePicker);

app.mount('#app');
