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
  