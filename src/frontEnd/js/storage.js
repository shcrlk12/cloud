let downloadFile = function(path, fileName){
    if(path == null)
        window.location.href = `${serverUrl}/api/v1/fileDownload?fileName=${fileName}`;
    else
    window.location.href = `${serverUrl}/api/v1/fileDownload?fileName=${fileName}&path=${path}`;
}

let postdFile = function(path, fileName){
    $.ajax({
        url: `${serverUrl}/api/v1/postFile`,
        processData : false,
        contentType : false,
        type: 'POST',
        data: formData
    });
}

let init = function(){


    const URLSearch = new URLSearchParams(location.search);

    let pathParam = URLSearch.get('path');
    let path;

    if(pathParam == null){
        path = '';
    }else
        path = `?path=${pathParam}`;
    
    $.ajax({
        url: `${serverUrl}/api/v1/fileList${path}`,
        type: 'GET',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type","application/json");
            xhr.setRequestHeader("X-KJWON-AUTH",'Bearer ' + getToken());
        },

        success : function(data){
            let elem = $('#fileSet');
            let innerHTML = '';
            
            data.data.forEach(data => {
                if(data.fileType == 0){
                    let test = data.fileName.replace(/ /gi, "%20")
                    let test1 ='';

                    if(pathParam !== null){
                        test1 = pathParam + '/' + test
                    }
                    else
                        test1 = test
                    innerHTML += `<div><a href="?path=${test1}">${data.fileName}</a></div>`;
                }
                else
                    innerHTML += `<div class="item">${data.fileName}</div>`;
                    
            });

            elem.html(innerHTML);

            $('.item').dblclick(function(e){
                downloadFile(URLSearch.get('path'), e.target.outerText);
            })
        }
    });
}
window.onload = function() {

/**/
    init();
/**/
var sec9 = document.querySelector('#ex9');
var btnUpload = sec9.querySelector('.btn-upload');
var inputFile = sec9.querySelector('input[type="file"]');
var uploadBox = sec9.querySelector('.upload-box');

/* ?????? ?????? Drag ???????????? ??? */
uploadBox.addEventListener('dragenter', function(e) {
    console.log('dragenter');
});

/* ?????? ?????? Drag??? ?????? ?????? ??? */
uploadBox.addEventListener('dragover', function(e) {
    e.preventDefault();
    console.log('dragover');

    this.style.backgroundColor = 'green';
});

/* ?????? ????????? Drag??? ?????? ??? */
uploadBox.addEventListener('dragleave', function(e) {
    console.log('dragleave');

    this.style.backgroundColor = 'white';
});

/* ?????? ????????? Drag??? Drop?????? ??? */
uploadBox.addEventListener('drop', function(e) {

    console.log(e.dataTransfer.files);
    e.preventDefault();

    console.log('drop');
    this.style.backgroundColor = 'white';
    
    const URLSearch = new URLSearchParams(location.search);

    let pathParam = URLSearch.get('path');
    
    const formData = new FormData();
    formData.append('uploadFile', e.dataTransfer.files[0]);
    formData.append('path', pathParam);
    
    
    $.ajax({
        url: `${serverUrl}/api/v1/postFile`,
        processData : false,
        contentType : false,
        type: 'POST',
        data: formData,
        success : function(){
            location.reload();
        }
    });
});
}