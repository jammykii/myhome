function deleteBoard(id) {
    //DELETE /api/boards/{id}
    $.ajax({
        url: '/api/boards/' + id,
        type: 'DELETE',
        success: function(result) {
            console.log('result', result);
            alert('삭제됐습니다.');     
            window.location.href = '/board/list';
        }
    });
}

let t = 0;
let heart = document.querySelector('.heart')
heart.addEventListener('click', ()=>{
    if(t == 0){
        heart.src = "../img/heart_full.png"
        t = 1;
    }else{
        heart.src = "../img/heart_empty.png"
        t = 0;
    }
})