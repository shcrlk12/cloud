    // 토큰 체크
    (function(){
    const token = getToken();
    if (token === null) {
        location.assign('/');
    }
    })()
