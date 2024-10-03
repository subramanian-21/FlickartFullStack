import axios from 'axios';

import { SERVER_BASE_URL } from '../util/constants';
import { cleanToken, getAccessToken, getRefreshToken, setAccessToken, setRefreshToken } from '../util/localstorage';

const instance = axios.create({
  baseURL: SERVER_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

instance.interceptors.request.use(
  async (config) => {
    const token = getAccessToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

instance.interceptors.response.use(
   (res) => res,
  async (err) => {
    const originalConfig = err.config;
    
    if (originalConfig.url !== 'user/login' && err.response) {
      if (err.response.status === 401 && !originalConfig._retry) {
        originalConfig._retry = true;

        try {
          const refreshToken = getRefreshToken();
          if (refreshToken) {

            const response = await instance.post('admin/auth/refresh', { refreshToken });
            setRefreshToken(response.data.refreshToken);
            setAccessToken(response.data.accessToken);
            
            instance.defaults.headers.common.Authorization = `Bearer ${response.data.accessToken}`;
            originalConfig.headers.Authorization = `Bearer ${response.data.accessToken}`;
            
            return instance(originalConfig);
          }
        } catch (_error) {
          cleanToken();
          window.location.href = '/login';
          return Promise.reject(_error);
        }
      }
    }
    return Promise.reject(err);
  }
);

export default instance;
