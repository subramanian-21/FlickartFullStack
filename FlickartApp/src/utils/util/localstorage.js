export function getAccessToken() {
    return localStorage.getItem('accessToken');
  }
  export function getRefreshToken() {
    return localStorage.getItem('refreshToken');
  }
  
  export function setAccessToken(data) {
    localStorage.setItem('accessToken', data);
  }
  
  export function setRefreshToken(data) {
    localStorage.setItem('refreshToken', data);
  }
  
  export function cleanToken() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }
  export function setUserId(data) {
    localStorage.setItem('userId', data);
  }

  export function getUserId() {
    return localStorage.getItem('userId');
  }

  export function setCartId(data) {
    localStorage.setItem('cartId', data);
  }
  
  export function getCartId() {
    return localStorage.getItem('cartId');
  }