function deleteUserRole(userId, roleId) {
    $.ajax({
        url: '/api/userRole?userid=' + userId +'&roleid=' +roleId,
        type: 'DELETE',
        success: function(result) {
            console.log('result', result);
            alert('삭제됐습니다.');     
            window.location.href = '/account/usrole';
        }
    });
}

function deleteUser(userid) {
    $.ajax({
        url: '/api/users/' + userid ,
        type: 'DELETE',
        success: function(result) {
            console.log('result', result);
            alert('삭제됐습니다.');     
            window.location.href = '/account/usrole';
        }
    });
}