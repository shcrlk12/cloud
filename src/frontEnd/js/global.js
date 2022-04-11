const localUrl = "http://localhost:8010";
const remoteUrl = "http://115.139.45.137:2123";

const serverUrl = remoteUrl;

const tokenHeader = "X-KJWON-AUTH";

function getToken() {
    return localStorage.getItem('accessToken');
  }