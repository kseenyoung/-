import Cookies from 'js-cookie';

export const cookiesStorage = {
  setItem(key, state) {
    return Cookies.set(key, state);
  },
  getItem(key) {
    return Cookies.get(key);
  },
  deleteItem(key) {
    return Cookies.remove(key);
  },
  
};

export const userCookiesStorage = {
  setItem(key, state) {
    return Cookies.set(key, state);
  },
  getItem(key) {
    return Cookies.get(key);
  },
  deleteItem(key) {
    return Cookies.remove(key);
  },
  
};
