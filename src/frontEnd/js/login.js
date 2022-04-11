  async function login(event) {

    // 1. 이벤트 막음
    event.preventDefault();
    event.stopPropagation();
  
    //2. 로그인 상태인지 확인
    const emailElement = document.querySelector('#email');
    const passwordElement = document.querySelector('#password');
  
    const principal = emailElement.value;
    const credentials = passwordElement.value;
    
    $.ajax({
        type: "POST",
        url: `${serverUrl}/api/users/login`,
        data: { 
            principal,
            credentials
         }, 
     
        success: function(data){
            localStorage.setItem('accessToken', data.response.token);
            location = '/';
        },
        error: function( request, status, error ){
            let errorMsg = request.responseText;

            if(errorMsg.includes('Bad credential'))
                alert('비밀번호가 다릅니다.');
            else
                alert('로그인 실패')
        }
     });
  }
  
  function bindLoginButton() {
    const form = document.querySelector('#form-login');
    form.addEventListener('submit', login);
  }
  
  async function main() {
    // 버튼에 이벤트 연결
    bindLoginButton();
  
    // 토큰 체크
    const token = getToken();
    if (token !== null) {
      location.assign('/storage');
      return;
    }
  }
  
  document.addEventListener('DOMContentLoaded', main);
  